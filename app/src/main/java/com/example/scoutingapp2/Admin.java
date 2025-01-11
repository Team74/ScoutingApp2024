package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


//This is the script for the admin functions. This is NOT for the password screen
//Here you will find a lot of helper functions that work with the database

public class Admin extends AppCompatActivity {

    //Create all of the buttons on the screens
    Button createSampleData_btn, exportCSV_btn, deleteAll_btn, importCSV_btn, test_btn, deleteRow_btn;

    //Class for the pop up text
    AlertDialog dialog;
    AlertDialog.Builder builder;

    //For inporting the CSV file
    CSV csv;
    String baseDir;
    Intent myFileIntent;
    Uri filePath;
    Context context = this;

    //Holds all of the UI for the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Initialize everything
        csv = new CSV(Admin.this);
        baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS;
        builder = new AlertDialog.Builder(Admin.this);

        //Get all of the buttons
        createSampleData_btn = findViewById(R.id.createSampleData_btn);
        exportCSV_btn = findViewById(R.id.exportCSV_btn);
        deleteAll_btn = findViewById(R.id.deleteAll_btn);
        importCSV_btn = findViewById(R.id.importCSV_btn);
        deleteRow_btn = findViewById(R.id.deleteRow_btn);
        test_btn = findViewById(R.id.test_btn);

        //When a user presses the create sample data button...
        //Creates Sample Data by calling the function from My Database Helper. It overwrites the data so we ask are you sure
        createSampleData_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //This makes a builder that will hold an alert to make sure people do not click it on accident
                builder.setTitle("Are You Sure?");
                builder.setMessage("This will overwrite the data.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //Get the database
                        MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);
                        //create the new data
                       myDB.createSampleData(); //UPDATE The sample data to the new game
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

        //When the user clicks the export csv button...
        exportCSV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is the code for the Are You sure for the create sample data
                builder.setTitle("Are You Sure?");
                builder.setMessage("This will export the data as a CSV.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText textBox = new EditText(context);
                        AlertDialog dialog = new AlertDialog.Builder(context)
                                .setTitle("Set Title")
                                .setMessage("This will be added to the CSV file name")
                                .setView(textBox)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Create the file name
                                        String editTextInput = " " + textBox.getText().toString() + " ";
                                        Log.d("path123","editext value is: "+ editTextInput);
                                        //Export the Data
                                        csv.exportMatchData(baseDir, editTextInput);
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

        //When the user clicks the delete all button...
        deleteAll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is the code for the Are You sure for the create sample data
                builder.setTitle("Are You Sure?");
                builder.setMessage("This will DELETE ALL of the data");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Get database
                        MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);
                        //Get the writable version
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        //Upgrade the database. This will clear it
                        myDB.onUpgrade(db, 0, 0);
                        Toast.makeText(Admin.this, "All Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

        //On import button press
        importCSV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the file explorer
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10); //dont worry about the error, the tablets are very old

            }
        });

        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath != null)
                {
                    importCSV(filePath);
                }
            }
        });

        //Delete Row button
        deleteRow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is the code for the Are You sure for the create sample data
                builder.setTitle("Are You Sure?");
                builder.setMessage("This will delete one Row");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText textBox = new EditText(context);
                        AlertDialog dialog = new AlertDialog.Builder(context)
                                .setTitle("Choose Row ID")
                                .setMessage("This row will be deleted (ONLY NUMBERS)")
                                .setView(textBox)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            //Convert the text to a int
                                            String editTextInput = textBox.getText().toString().trim();
                                            Log.d("path123", "editext value is: " + editTextInput);
                                            int id = Integer.parseInt(editTextInput);
                                            //Get the database
                                            MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);
                                            SQLiteDatabase wrtDB = myDB.getWritableDatabase();
                                            String[] whereClause = {String.valueOf(id)};
                                            //Delete the row
                                            wrtDB.delete(myDB.MATCH_TABLE, myDB.COLUMN_ID + "=?", whereClause);
                                            Toast.makeText(Admin.this, "Row " + id + " was Deleted", Toast.LENGTH_SHORT).show();
                                        }catch (NumberFormatException e)
                                        {
                                            //If it failed
                                            Toast.makeText(Admin.this, "Not a Number", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();
            }
        });

    }

    //When we get back from the file explorer
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Path123", String.valueOf(requestCode));
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if(data != null)
                    {
                        uri = data.getData();
                        filePath =  uri;
                        //import via the found filepath
                        importCSV(filePath);
                    }

                }
                break;
            default:
                Log.d("path123", "failed");
                break;
        }
    }


    void importCSV(Uri csvFileUri) {
        Context context = this;

        //Massive try catch
        try {
            //get all of the helper functions
            ContentValues cv = new ContentValues();
            MyDataBaseHelper myDB = new MyDataBaseHelper(this);
            SQLiteDatabase db = myDB.getWritableDatabase();

            //UPDATE This to the new table headers
            //These will have to be changed to match the table headers
            String[] csvHeaderLine = {
                    myDB.COLUMN_TEAMNUM,
                    myDB.COLUMN_LEAVESTART,
                    myDB.COLUMN_AUTOSPEAKER,
                    myDB.COLUMN_AUTOAMP,
                    myDB.COLUMN_AUTOGRAB,
                    myDB.COLUMN_TELESPEAKER,
                    myDB.COLUMN_TELEAMP,
                    myDB.COLUMN_TELEAMPSPEAKER,
                    myDB.COLUMN_CLIMB,
                    myDB.COLUMN_TRAPDOOR
            };

            // open file via the uri
            InputStream inputStream = getContentResolver().openInputStream(csvFileUri);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // now attach a CSV reader to file reader
            CSVReader reader = new CSVReader(bufferedReader);

            // create a CSV and DB record that we will fill in
            String[] csvLine;

            // for each record returned from the CSV file, add a record to DB
            while ((csvLine = reader.readNext()) != null) {

                // check for the CSV header row and skip it
                if (csvLine[0].equals("team_num")) {
                    continue;
                }

                int i = 0;

                for(String col : csvHeaderLine) {
                    cv.put(col, Integer.parseInt(csvLine[i]));
                    i++;
                }

                if (   (Integer.parseInt(csvLine[1]) > -1)
                        && ((Integer.parseInt(csvLine[0]) > 0) && (Integer.parseInt(csvLine[0]) < 10000))) {
                    // ...add the team round data record to the DB
                    db.insert("Match_Data", null, cv);
                }

                cv.clear();
            }
            reader.close();
            bufferedReader.close();
            inputStreamReader.close();
            Toast.makeText(context, "Imported CSV", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            Log.d("path123", String.valueOf(e));
            e.printStackTrace();
            // throw new RuntimeException(e);

        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

    }


}