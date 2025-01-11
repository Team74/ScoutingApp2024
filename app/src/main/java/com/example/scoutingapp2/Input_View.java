package com.example.scoutingapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


//This is for the list of the matches

public class Input_View extends AppCompatActivity {

    FloatingActionButton toOneInput_btn;
    RecyclerView recyclerView;
    MatchCustomAdapter matchCustomAdapter;
    ArrayList<String> _id, prematch, auton, teleop; //creating an array list that will be later filled with the table data to display

    MyDataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_view);

        //recyclerView is a list type
        recyclerView = findViewById(R.id.recyclerView);
        toOneInput_btn = findViewById(R.id.addOne_btn);

        myDB = new MyDataBaseHelper(Input_View.this);
        _id = new ArrayList<>(); //making sure they are instantiated
        prematch = new ArrayList<>();
        auton = new ArrayList<>();
        teleop = new ArrayList<>();

        storeDataInArray();

        //after getting the data, we then put it in the custom adapter to get it in a form the recycler view can use
        matchCustomAdapter = new MatchCustomAdapter(Input_View.this, Input_View.this,
                _id, prematch, auton, teleop);
        recyclerView.setAdapter(matchCustomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Input_View.this));


        //Start the add match screen
        toOneInput_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Input_View.this, Input_Three.class);
                startActivity(intent);
            }
        });
    }

    void storeDataInArray() //This function creates a cursor to run over the database and get the data to add to the array lists
    {
        Cursor cursor = myDB.readAllMatchData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show(); //no data, could be an error
        }else{
            cursor.moveToLast(); //This makes it so that the most recent entry is at the top
            do
            {
                //syncs the array strings with the right column, make sure the ids match!
                _id.add(cursor.getString(0));
                prematch.add(cursor.getString(1));
                auton.add(cursor.getString(2));
                teleop.add(cursor.getString(3));
            }
            while(cursor.moveToPrevious());

        }
    }

    //This refreshes the screen to get the new or updated data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) //the update screen returns one when it loads this activity
        {
            recreate();
        }
    }

}