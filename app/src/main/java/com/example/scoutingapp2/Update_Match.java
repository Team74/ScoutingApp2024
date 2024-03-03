package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class Update_Match extends AppCompatActivity implements frag_Input_TeleOp_Three.TeleOpOnDataPass, //implement the interfaces in the fragments to easily send data from them
        frag_Input_Auton_Three.AutonOnDataPass, frag_Input_PreMatch_Three.PreMatchOnDataPass, frag_Input_Endgame_Three.EndGameOpOnDataPass {

    //This is the screen that is pulled up when you update a match. We receive the id from the recycler view, then search the data
    //in the database with that ID, then send the current or "old" data to the fragments to display what it is currently, then we do the same
    //thing as the add match but pass in the match ID and overwrite that row in the database.

    int id;
    int preMatch_old, auton_old, teleop_old;

    Button auton_btn;
    Button teleop_btn, get_btn, prematch_btn;

    frag_Input_Auton_Three autonFragment;
    frag_Input_TeleOp_Three teleopFragment;
    frag_Input_PreMatch_Three prematchFragment; //making fragments, these are the screens in the main activity

    MyDataBaseHelper myDB = new MyDataBaseHelper(Update_Match.this);

    ArrayList preMatch_ary, auton_ary, teleop_ary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_three);

        auton_btn = findViewById(R.id.auton_btn);
        teleop_btn = findViewById(R.id.teleop_btn);
        get_btn = findViewById(R.id.get_btn);
        prematch_btn = findViewById(R.id.prematch_btn);

        autonFragment = new frag_Input_Auton_Three();
        teleopFragment = new frag_Input_TeleOp_Three();
        prematchFragment = new frag_Input_PreMatch_Three();

        FragmentManager fragmentManager = getSupportFragmentManager(); //getting a manager to manage the fragments

        fragmentManager.beginTransaction() //adding the fragments to the container view
                .replace(R.id.fragmentContainerView, autonFragment, null)
                .add(R.id.fragmentContainerView, teleopFragment, null)
                .add(R.id.fragmentContainerView, prematchFragment, null)
                .commit();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //creating a transaction to hide a fragments
        fragmentTransaction.hide(autonFragment).hide(teleopFragment).commit();

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


        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make the fragments send the data
                prematchFragment.sendData();
                autonFragment.sendData();
                teleopFragment.sendData();
                //put the data into a CV
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
                Intent intent = new Intent(Update_Match.this, Input_View.class);
                startActivity(intent);
            }
        });

        preMatch_ary = new ArrayList<>();
        auton_ary = new ArrayList();
        teleop_ary = new ArrayList();

        //getting the data from the recycler view
        getIntentData();

    }

    void getIntentData()
    {
        if(getIntent().hasExtra("_id")) //if the intent does not have an ID, return
        {
            id = getIntent().getIntExtra("_id", 0);
        }else{
            id = -1;
            return;
        }

        //getting all of the data in the databas
        Cursor cursor = myDB.readAllMatchData();
        if(cursor.getCount() == 0) //AKA no data
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            do
            {
                //syncs the array strings with the right column, make sure the ids match!
                preMatch_ary.add(cursor.getString(1));
                auton_ary.add(cursor.getString(2));
                teleop_ary.add(cursor.getString(3));
            }
            while(cursor.moveToNext()); //TODO make it strings and not ids

        }
        //converting to ints  -1 as the ids start at 1 and the table starts at 0
        preMatch_old = Integer.parseInt(preMatch_ary.get(id-1).toString());
        auton_old = Integer.parseInt(auton_ary.get(id-1).toString());
        teleop_old = Integer.parseInt(teleop_ary.get(id-1).toString());

        //Only after do we send data to fragment as if this function returns, then we should not send data
        sendDataToFragments();
    }

    void sendDataToFragments()
    {
        //Create a CV, put the values in it, then send it as one package to the fragment to display and set values
        ContentValues preMatchCV = new ContentValues();
        preMatchCV.put("data", preMatch_old);
        prematchFragment.retrieveData(preMatchCV);

        ContentValues autonCV = new ContentValues();
        autonCV.put("data", auton_old);
        autonFragment.retriveData(autonCV);

        ContentValues teleopCV = new ContentValues();
        teleopCV.put("data", teleop_old);
        teleopFragment.retriveData(teleopCV);
    }

    //This is the data that we receive to put in the data base.
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
        boolLeaveStart = autoLeaveSpot;
        this.autoSpeaker = autoSpeaker;
        this.autoAmp = autoAmp;
        this.autoGrab = autoGrab;
    }

    @Override
    public void EndgameOnDataPass(int[] trapDoorNum, int[] climbAmount) {
        for (int i = 0; i  <= 2; i++) {
            this.trapDoor[i] = trapDoorNum[i];
            this.climbAmount[i] = climbAmount[i];

        }
    }
}