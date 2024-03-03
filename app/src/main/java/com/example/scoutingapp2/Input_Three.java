package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Input_Three extends AppCompatActivity implements frag_Input_TeleOp_Three.TeleOpOnDataPass,
        frag_Input_Auton_Three.AutonOnDataPass, frag_Input_PreMatch_Three.PreMatchOnDataPass, frag_Input_Endgame_Three.EndGameOpOnDataPass {

    Button auton_btn;
    Button teleop_btn, get_btn, prematch_btn, endgame_btn;

    frag_Input_Auton_Three autonFragment;
    frag_Input_TeleOp_Three teleopFragment;
    frag_Input_PreMatch_Three prematchFragment; //making fragments, these are the screens in the main activity
    frag_Input_Endgame_Three endgameFragment;

    MyDataBaseHelper myDB = new MyDataBaseHelper(Input_Three.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_three);

        auton_btn = findViewById(R.id.auton_btn);
        teleop_btn = findViewById(R.id.teleop_btn);
        get_btn = findViewById(R.id.get_btn);
        prematch_btn = findViewById(R.id.prematch_btn);
        endgame_btn = findViewById(R.id.endgame_btn);

        autonFragment = new frag_Input_Auton_Three();
        teleopFragment = new frag_Input_TeleOp_Three();
        prematchFragment = new frag_Input_PreMatch_Three();
        endgameFragment = new frag_Input_Endgame_Three();

        FragmentManager fragmentManager = getSupportFragmentManager(); //getting a manager to manage the fragments

        fragmentManager.beginTransaction() //adding the fragments to the container view
                .replace(R.id.fragmentContainerView, autonFragment, null)
                .add(R.id.fragmentContainerView, teleopFragment, null)
                .add(R.id.fragmentContainerView, prematchFragment, null)
                .add(R.id.fragmentContainerView, endgameFragment, null)
                .commit();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //creating a transaction to hide a fragments on launch
        fragmentTransaction.hide(autonFragment).hide(teleopFragment).hide(endgameFragment).commit();

        //region Show Hide Fragments
        auton_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();
                autonFragment.updateTeamNums();
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2
                        .show(autonFragment)
                        .hide(teleopFragment)
                        .hide(prematchFragment)
                        .hide(endgameFragment)
                        .commit();
            }
        });

        teleop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();
                teleopFragment.updateTeamNums();
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3
                        .hide(autonFragment)
                        .show(teleopFragment)
                        .hide(prematchFragment)
                        .hide(endgameFragment)
                        .commit();

            }
        });

        prematch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();

                FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                fragmentTransaction4
                        .hide(autonFragment)
                        .hide(teleopFragment)
                        .show(prematchFragment)
                        .hide(endgameFragment)
                        .commit();
                prematchFragment.sendData();
            }
        });

        endgame_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();
                endgameFragment.updateTeamNums();
                FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                fragmentTransaction4
                        .hide(autonFragment)
                        .hide(teleopFragment)
                        .hide(prematchFragment)
                        .show(endgameFragment)
                        .commit();
                prematchFragment.sendData();
            }
        });
        //endregion

        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();
                autonFragment.sendData();
                teleopFragment.sendData();

                for (int i = 0; i  <= 2; i++) {
                    ContentValues cv = new ContentValues();
                    cv.put(myDB.COLUMN_TEAMNUM, teamNum_int[i]);
                    cv.put(myDB.COLUMN_LEAVESTART, boolLeaveStart[i]);
                    cv.put(myDB.COLUMN_AUTOSPEAKER, autoSpeaker[i]);
                    cv.put(myDB.COLUMN_AUTOAMP, autoAmp[i]);
                    cv.put(myDB.COLUMN_AUTOGRAB, autoGrab[i]);
                    cv.put(myDB.COLUMN_TELESPEAKER, teleSpeaker[i]);
                    cv.put(myDB.COLUMN_TELEAMP, teleAmp[i]);
                    cv.put(myDB.COLUMN_TELEAMPSPEAKER, teleAmpSpeaker[i]);
                    cv.put(myDB.COLUMN_CLIMB, climbAmount[i]);
                    cv.put(myDB.COLUMN_TRAPDOOR, trapDoor[i]);
                    Log.d("Hello world", cv.getAsString(myDB.COLUMN_AUTOAMP));
                    myDB.AddUpdateMatch(-1, cv, true);
                }
                //after adding the match, we go back to the view screen
                Intent intent = new Intent(Input_Three.this, Input_View.class);
                startActivity(intent);
            }
        });

    }


    int[] teamNum_int = {0,0,0};
    int[] autoSpeaker = {0,0,0}, autoAmp = {0,0,0}, autoGrab = {0,0,0};
    boolean[] boolLeaveStart = {false, false, false};
    int[] teleSpeaker = {0,0,0}, teleAmp = {0,0,0}, teleAmpSpeaker = {0,0,0};
    int[] trapDoor = {0,0,0}, climbAmount = {0,0,0};


    @Override
    public void PreMatchOnDataPass(int[] teamNum) {
        teamNum_int[0] = teamNum[0];
        teamNum_int[1] = teamNum[1];
        teamNum_int[2] = teamNum[2];
        System.out.println("Teams are: " + teamNum_int[0] + teamNum_int[1] + teamNum_int[2]);
    }

    @Override
    public void TeleOpOnDataPass(int[] teleSpeaker, int[] teleAmp, int[] teleAmpSpeaker) {
        for (int i = 0; i  <= 2; i++) {
            this.teleSpeaker[i] = teleSpeaker[i];
            this.teleAmp[i] = teleAmp[i];
            this.teleAmpSpeaker[i] = teleAmpSpeaker[i];
        }
    }

    @Override
    public void AutonOnDataPass(boolean[] autoLeaveSpot, int[] autoSpeaker, int[] autoAmp, int[] autoGrab) {
        for (int i = 0; i  <= 2; i++) {
            boolLeaveStart[i] = autoLeaveSpot[i];
            this.autoSpeaker[i] = autoSpeaker[i];
            this.autoAmp[i] = autoAmp[i];
            this.autoGrab[i] = autoGrab[i];
        }

    }


    @Override
    public void EndgameOnDataPass(int[] trapdoorNum, int[] climbAmount) {
        for (int i = 0; i  <= 2; i++) {
            this.trapDoor[i] = trapdoorNum[i];
            this.climbAmount[i] = climbAmount[i];

        }
    }

    int[] getTeamNums(){
        return teamNum_int;
    }
}