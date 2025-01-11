package com.example.scoutingapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//This is the screen for the different types of data analysis. Currently, it is just one due to lack of time

public class DataAnalysisSelection extends AppCompatActivity {

    Button toMatchData_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis_selection);

        toMatchData_btn = findViewById(R.id.toMatchData_btn);
        toMatchData_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch the table screen
                Intent intent = new Intent(DataAnalysisSelection.this, MatchDataTable.class);
                startActivity(intent);
            }
        });
    }
}