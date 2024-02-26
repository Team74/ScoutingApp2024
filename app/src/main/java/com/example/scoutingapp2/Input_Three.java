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
        frag_Input_Auton_Three.AutonOnDataPass, frag_Input_PreMatch_Three.PreMatchOnDataPass {

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
                FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                fragmentTransaction4
                        .hide(autonFragment)
                        .hide(teleopFragment)
                        .show(prematchFragment)
                        .hide(endgameFragment)
                        .commit();
            }
        });

        endgame_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                fragmentTransaction4
                        .hide(autonFragment)
                        .hide(teleopFragment)
                        .hide(prematchFragment)
                        .show(endgameFragment)
                        .commit();
            }
        });
        //endregion

        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prematchFragment.sendData();
                autonFragment.sendData();
                teleopFragment.sendData();
                ContentValues cv = new ContentValues();
                cv.put(myDB.COLUMN_PREMATCH, teamNum_int);
                cv.put(myDB.COLUMN_LEAVESTART, boolLeaveStart);
                cv.put(myDB.COLUMN_AUTOSPEAKER, autoSpeaker);
                cv.put(myDB.COLUMN_AUTOAMP, autoAmp);
                cv.put(myDB.COLUMN_AUTOGRAB, autoGrab);
                cv.put(myDB.COLUMN_TELEOP, teleop_int);
                Log.d("Hello world", cv.getAsString(myDB.COLUMN_AUTOAMP));
                myDB.AddUpdateMatch(-1, cv, true);

                //after adding the match, we go back to the view screen
                Intent intent = new Intent(Input_Three.this, Input_View.class);
                startActivity(intent);
            }
        });

    }


    int teamNum_int;
    int autoSpeaker, autoAmp, autoGrab;
    boolean boolLeaveStart;
    int teleop_int;

    @Override
    public void PreMatchOnDataPass(int data) {
        teamNum_int = data;
    }

    @Override
    public void TeleOpOnDataPass(int data) {
        teleop_int = data;
    }

    @Override
    public void AutonOnDataPass(boolean autoLeaveSpot, int autoSpeaker, int autoAmp, int autoGrab) {
        boolLeaveStart = autoLeaveSpot;
        this.autoSpeaker = autoSpeaker;
        this.autoAmp = autoAmp;
        this.autoGrab = autoGrab;
    }
}