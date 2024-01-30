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

public class Input_One extends AppCompatActivity implements frag_Input_TeleOp_One.TeleOpOnDataPass,
        frag_Input_Auton_One.AutonOnDataPass, frag_Input_PreMatch_One.PreMatchOnDataPass {

    Button auton_btn;
    Button teleop_btn, get_btn, prematch_btn;

    frag_Input_Auton_One autonFragment;
    frag_Input_TeleOp_One teleopFragment;
    frag_Input_PreMatch_One prematchFragment; //making fragments, these are the screens in the main activity

    MyDataBaseHelper myDB = new MyDataBaseHelper(Input_One.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_one);

        auton_btn = findViewById(R.id.auton_btn);
        teleop_btn = findViewById(R.id.teleop_btn);
        get_btn = findViewById(R.id.get_btn);
        prematch_btn = findViewById(R.id.prematch_btn);

        autonFragment = new frag_Input_Auton_One();
        teleopFragment = new frag_Input_TeleOp_One();
        prematchFragment = new frag_Input_PreMatch_One();

        FragmentManager fragmentManager = getSupportFragmentManager(); //getting a manager to manage the fragments

        fragmentManager.beginTransaction() //adding the fragments to the container view
                .replace(R.id.fragmentContainerView, autonFragment, null)
                .add(R.id.fragmentContainerView, teleopFragment, null)
                .add(R.id.fragmentContainerView, prematchFragment, null)
                .commit();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //creating a transaction to hide a fragments
        fragmentTransaction.hide(autonFragment).hide(teleopFragment).commit();

        //region Show Hide Fragments
        auton_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2
                        .show(autonFragment)
                        .hide(teleopFragment)
                        .hide(prematchFragment)
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
                Intent intent = new Intent(Input_One.this, Input_View.class);
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