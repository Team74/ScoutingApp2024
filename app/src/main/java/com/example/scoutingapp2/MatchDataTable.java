package com.example.scoutingapp2;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class MatchDataTable extends ScoutingReportActivity {

    MyDataBaseHelper myDB;
    String minMax = "AVG";
    Switch minMaxSwitch;
    RadioGroup radioGroup;
    RadioButton radioButton;
    int radioIndex = 0;
    String simpleData = "";
    String advData = "";
    String allData = ""; //TODO Decide if its better to have all data show just the weird data or have all of the data just small
    String delistTeams = "";

    //region TeleOp Points and Auton Points strings
    String totalTeleopPoints = "(tele_speaker * 2) + (tele_amp * 1) + (tele_amped_speaker * 5)";

    String totalAutoPoints = "(auto_speaker * 2) + (auto_amp * 1)";
    //endregion

    public class UpdateMatchDataTable implements ScoutingReportActivity.ReportUpdateCommand {

        public void update(String orderBy, String orderType) {
            // get all the data records from the DB
            String simpleColumns[] = {myDB.COLUMN_TEAMNUM, "Total_Points", "_Auto_Pieces","_Cycles","_teleSpeaker", "_teleAmp"};

            String simpleHeadings[] = {"Team #", "Total Points", "Auto", "Cycles", "Avg Speaker", "Avg Amp"};

            String query = "SELECT " + myDB.COLUMN_TEAMNUM +
                    simpleData +
                    advData + allData +
                    " FROM " + myDB.MATCH_TABLE +
                    " WHERE " + myDB.COLUMN_TEAMNUM + " NOT IN (" + delistTeams + ")" +
                    " GROUP BY " + myDB.COLUMN_TEAMNUM +
                    " ORDER BY " + orderType + " " + orderBy + " ";

            SQLiteDatabase db = myDB.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            Log.d("testing123", "abc " + String.valueOf(radioIndex));

                AddHeaderStringsAsRowToReportTable(R.id.matchDataTableHeader,
                        simpleHeadings, this, 10);

            if (cursor.getCount() == 0) {
                //Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            } else {
                TableLayout table = findViewById(R.id.matchDataTable);
                table.removeAllViews();
                // create a data row for each data record returned from DB
                cursor.moveToFirst();
                do {
                    // add each data value to an array of strings
                    List<String> values = new ArrayList<String>();
                        for (String col : simpleColumns) {
                            Integer idx = cursor.getColumnIndex(col);
                            if (idx >= 0) {
                                values.add(cursor.getString(idx));
                            }
                        }
                    String[] data = values.toArray(new String[0]);

                    // add the data strings as a row to our table
                    AddDataStringsAsRowToReportTable(R.id.matchDataTable, data);
                }
                while (cursor.moveToNext());
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_data_table);

        if(getIntent().hasExtra("delistTeams"))
        {
            getDelistIntent();
        }else
        {
            getDelistFromFile();
        }
        refreshSimpleData();
        advData = "";
        myDB = new MyDataBaseHelper(MatchDataTable.this);
        minMaxSwitch = findViewById(R.id.switch1);
        radioGroup = findViewById(R.id.radioGroup);
        Button delist_btn = findViewById(R.id.delist_btn);
        UpdateMatchDataTable updateMatchDataTable = new UpdateMatchDataTable();

        updateMatchDataTable.update("ASC", myDB.COLUMN_TEAMNUM);
        minMaxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(minMaxSwitch.isChecked())
                {
                    minMax = "MAX";
                    refreshSimpleData();
                    updateMatchDataTable.update("ASC", myDB.COLUMN_TEAMNUM);
                }else if(!minMaxSwitch.isChecked())
                {
                    minMax = "AVG";
                   refreshSimpleData();
                    refreshAdvData();
                    updateMatchDataTable.update("ASC", myDB.COLUMN_TEAMNUM);
                }
            }
        });
        //TODO finish the max min bug
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioID);
                radioIndex = radioGroup.indexOfChild(radioButton);
                /*if(radioIndex == 1) //advance
                {
                    advData = ", ROUND(" + minMax + "(autoBalance), 2) AS MAX_autoBalance, ROUND(" + minMax + "(teleOpBalance), 2) AS _teleOpBalance, ROUND(" + minMax + "(autoConesTotal), 2) AS _autoConesTotal, " +
                            "ROUND(" + minMax + "(autoCubesTotal), 2) AS _autoCubesTotal, ROUND(" + minMax + "(autonWorked), 2) AS _autonWorked, ROUND(" + minMax + "(broke), 2) AS _broke, " +
                            "ROUND(" + minMax + "(Defence), 2) AS _Defence";

                    updateMatchDataTable.update("ASC", myDB.COLUMN_PREMATCH);
                } else if (radioIndex == 0) { //simple
                    advData = "";
                    updateMatchDataTable.update("ASC", "teleOpConesTotal");
                } else if (radioIndex == 2) { //all
                    advData = ", ROUND((autoBalance), 2) AS MAX_autoBalance, ROUND((teleOpBalance), 2) AS _teleOpBalance, ROUND((autoConesTotal), 2) AS _autoConesTotal, " +
                            "ROUND((autoCubesTotal), 2) AS _autoCubesTotal, ROUND((autonWorked), 2) AS _autonWorked, ROUND((broke), 2) AS _broke, " +
                            "ROUND((Defence), 2) AS _Defence";
                    allData = ", ROUND((autoConesLow), 2) AS _autoConesLow, ROUND((autoConesMid), 2) AS _autoConesMid, ROUND((autoConesHigh), 2) AS _autoConesHigh," +
                            "ROUND((autoCubesLow), 2) AS _autoCubesLow, ROUND((autoCubesMid), 2) AS _autoCubesMid, ROUND((autoCubesHigh), 2) AS _autoCubesHigh," +
                            "ROUND((teleOpConesLow), 2) AS _teleOpConesLow, ROUND((teleOpConesMid), 2) AS _teleOpConesMid, ROUND((teleOpConesHigh), 2) AS _teleOpConesHigh," +
                            "ROUND((teleOpCubesLow), 2) AS _teleOpCubesLow, ROUND((teleOpCubesMid), 2) AS _teleOpCubesMid, ROUND((teleOpCubesHigh), 2) AS _teleOpCubesHigh";

                    updateMatchDataTable.update("ASC", myDB.COLUMN_PREMATCH);
                }*/
                Log.d("testing123", String.valueOf(radioIndex));
            }
        });

        delist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MatchDataTable.this, DeList_Team_Tables.class);
                //startActivity(intent);
            }
        });

    }

    String[] combine2Strings(String[] array1, String[] array2)
    {
        String [] result = new String[array1.length + array2.length];
        int index = 0;

        for (String item: array1) {
            if(item != null)
            {
                result[index++] = item;
            }
        }
        for (String item: array2) {
            if(item != null)
            {
                result[index++] = item;
            }
        }
        return result;
    }

    void refreshSimpleData()
    {
        //Round(min, max, avg) + "database coloum, digits as table coloum name
        simpleData = " , ROUND("+ minMax + " (" + totalTeleopPoints + "+" + totalAutoPoints + "), 2) AS Total_Points " +
                " , ROUND("+ minMax + "(auto_speaker + auto_amp), 2) AS _Auto_Pieces " +
                " , ROUND("+ minMax + "(tele_speaker + tele_amp + tele_amped_speaker), 2) AS _Cycles " +
                " , ROUND("+ minMax + "(tele_speaker), 2) AS _teleSpeaker " +
                " , ROUND("+ minMax + "(tele_amp), 2) AS _teleAmp ";

    }
    void refreshAdvData()
    {
        /*advData = ", ROUND(" + minMax + "(autoBalance), 2) AS MAX_autoBalance, ROUND(" + minMax + "(teleOpBalance), 2) AS _teleOpBalance, ROUND(" + minMax + "(autoConesTotal), 2) AS _autoConesTotal, " +
                "ROUND(" + minMax + "(autoCubesTotal), 2) AS _autoCubesTotal, ROUND(" + minMax + "(autonWorked), 2) AS _autonWorked, ROUND(" + minMax + "(broke), 2) AS _broke, " +
                "ROUND(" + minMax + "(Defence), 2) AS _Defence";*/

    }

    void getDelistIntent()
    {
        String[] delistTeamsArray;
        if(getIntent().hasExtra("delistTeams"))
        {
            delistTeamsArray = getIntent().getStringArrayExtra("delistTeams");
            delistTeams = String.join(",", delistTeamsArray);
            Log.d("path123", delistTeams);
            SharedPreferences save = getSharedPreferences("TeamsToDelist", 0);
            save.edit().putString("delistTeams", delistTeams).apply();
            Log.d("path123", delistTeams + " 54395i");
        }
    }
    private void getDelistFromFile()
    {
        Log.d("path123", delistTeams);
        if(!getIntent().hasExtra("delistTeams"))
        {
            Log.d("path123", delistTeams);
            SharedPreferences load = getSharedPreferences("TeamsToDelist", 0);
            delistTeams = load.getString("delistTeams", "0");
            Log.d("path123", delistTeams);
        }
    }

}