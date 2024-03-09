package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "FRC_Scouting.db";
    private static final int VERSION = 1;
    public static final String MATCH_TABLE = "Match_Data";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEAMNUM = "team_num";
    public static final String COLUMN_LEAVESTART = "leave_start";
    public static final String COLUMN_AUTOSPEAKER = "auto_speaker";
    public static final String COLUMN_AUTOAMP = "auto_amp";
    public static final String COLUMN_AUTOGRAB = "auto_grab";
    public static final String COLUMN_TELESPEAKER = "tele_speaker";
    public static final String COLUMN_TELEAMP = "tele_amp";
    public static final String COLUMN_TELEAMPSPEAKER = "tele_amped_speaker";
    public static final String COLUMN_CLIMB = "climb";
    public static final String COLUMN_TRAPDOOR = "trapdoor";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    //creates the table. The syntax is very weird and specific. I suggest to just copy a line and then change it to what you want
    //you can create mutible tables by copy and pasting
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + MATCH_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // 0  Keep in mind the ids! put the right id into the storeDataInArray() for the add string method
                COLUMN_TEAMNUM + " INTEGER, " + // 1
                COLUMN_LEAVESTART + " INTEGER, " + // 2
                COLUMN_AUTOSPEAKER + " INTEGER, " + // 3
                COLUMN_AUTOAMP + " INTEGER, " + // 4
                COLUMN_AUTOGRAB + " INTEGER, " + // 5
                COLUMN_TELESPEAKER + " INTEGER, " + // 6
                COLUMN_TELEAMP + " INTEGER, " + // 7
                COLUMN_TELEAMPSPEAKER + " INTEGER, " + // 8
                COLUMN_CLIMB + " INTEGER, " + // 9
                COLUMN_TRAPDOOR + " INTEGER);"; // 10
        Log.d("path123", "table 1");
        db.execSQL(query);
    }

    //on upgrade will get rid of all of the tables and make a new one. Used when wanting to remove old data.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE);
        onCreate(db);
    }

    public void AddUpdateMatch(int MatchID, ContentValues cv, Boolean displayToast)
    {
        try {
            long result; //used to make sure it does not error
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOAMP));
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOSPEAKER));
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOGRAB));
            Log.d("Hello world", cv.getAsString(COLUMN_LEAVESTART));

            // if the match ID is less then one (usually -1), then it will add it as a new match
            if (MatchID < 0) {
                result = db.insert(MATCH_TABLE, null, cv);
            } else { //otherwise, it is treated as an update and find the match id and replace it
                String row_id = String.valueOf(MatchID);
                result = db.update(MATCH_TABLE, cv, "_id=?", new String[]{row_id});
            }

            //If you want to display the toast, then this handles it. You may not want to display toasts when importing many matches, as it slows it down
            if (displayToast) {
                if (result == -1)//failed
                {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Badly Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void InsertMatch(ContentValues cv, Boolean displayToast)
    {
        try {
            long result; //used to make sure it does not error
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOAMP));
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOSPEAKER));
            Log.d("Hello world", cv.getAsString(COLUMN_AUTOGRAB));
            Log.d("Hello world", cv.getAsString(COLUMN_LEAVESTART));

            // if the match ID is less then one (usually -1), then it will add it as a new match
            result = db.insert(MATCH_TABLE, null, cv);

            //If you want to display the toast, then this handles it. You may not want to display toasts when importing many matches, as it slows it down
            if (displayToast) {
                if (result == -1)//failed
                {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Badly Failed", Toast.LENGTH_SHORT).show();
        }
    }


    //Return a cursor of all of the match data by getting a "blank" query of the table.
    Cursor readAllMatchData()
    {
        String query = "SELECT * FROM " + MATCH_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) //make sure there is a table
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //This creates sample data for the app, 360 matches to be exact
    void createSampleData()
    {
        // delete any old data
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);

        int[] sampleTeamNumbers = { 1, 74, 56, 88, 5565 };  // some sample #s

        // we're going to generate some random ints
        Random r = new Random();

        // create a list of 40 team numbers.
        List<Integer> teamNumbers = new ArrayList<Integer>();

        // first get any team numbers that exist in the current team data
        int[] currentTeamNumbers = {};  // tbd !!! daoTeamData.getAllTeamNumbers();
        for(int current : currentTeamNumbers) {
            teamNumbers.add(Integer.valueOf(current));
        }

        // then include any numbers in our sampleTeamNumbers array not already in list
        for(int sample : sampleTeamNumbers) {
            Integer teamNumber = Integer.valueOf(sample);
            if (!teamNumbers.contains(teamNumber)) { // no  dupes!
                teamNumbers.add(teamNumber);
            }
        }

        // lastly, add random team numbers until we have 40
        while (teamNumbers.size() < 40) {
            Integer teamNumber = Integer.valueOf(r.nextInt(8000) + 1); // 1-8000
            if (!teamNumbers.contains(teamNumber)) { // no dupes!
                teamNumbers.add(teamNumber);
            }
        }

        // generate some random match data
        for(int matchNum=1; matchNum<=60; matchNum++) {

            // each match gets 6 team number
            for(int i=0; i<6; i++){
                // get random team number from our list
                int teamNum = teamNumbers.get(r.nextInt(teamNumbers.size()));
                // tbd check if it's been used for this match already

                ContentValues cv = new ContentValues();
                cv.put(COLUMN_TEAMNUM, teamNum);
                cv.put(COLUMN_LEAVESTART, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_AUTOSPEAKER,Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_AUTOAMP, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_AUTOGRAB, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_TELESPEAKER, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_TELEAMP, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_TELEAMPSPEAKER, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_CLIMB, Integer.valueOf(r.nextInt(6)));
                cv.put(COLUMN_TRAPDOOR, Integer.valueOf(r.nextInt(6)));
                // now add random match data for that team
                AddUpdateMatch(-1, cv, false);
            }
        }

        Toast.makeText(context, "Sample Data Created", Toast.LENGTH_SHORT).show();
    }

}
