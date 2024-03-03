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
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;



public class Admin extends AppCompatActivity {

    Button createSampleData_btn, exportCSV_btn, deleteAll_btn, importCSV_btn, test_btn, deleteRow_btn;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    CSV csv;
    String baseDir;
    Intent myFileIntent;
    Uri filePath;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        csv = new CSV(Admin.this);
        baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS;
        builder = new AlertDialog.Builder(Admin.this);
        createSampleData_btn = findViewById(R.id.createSampleData_btn);
        exportCSV_btn = findViewById(R.id.exportCSV_btn);
        deleteAll_btn = findViewById(R.id.deleteAll_btn);
        importCSV_btn = findViewById(R.id.importCSV_btn);
        deleteRow_btn = findViewById(R.id.deleteRow_btn);
        test_btn = findViewById(R.id.test_btn);

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
                        MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);

                       myDB.createSampleData(); //TODO port the create sample data to the new game
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

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
                                        String editTextInput = " " + textBox.getText().toString() + " ";
                                        Log.d("path123","editext value is: "+ editTextInput);
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

                    }
                });
                builder.show();
            }
        });

        deleteAll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is the code for the Are You sure for the create sample data
                builder.setTitle("Are You Sure?");
                builder.setMessage("This will DELETE ALL of the data");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        myDB.onUpgrade(db, 0, 0);
                        Toast.makeText(Admin.this, "All Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        importCSV_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                            String editTextInput = textBox.getText().toString().trim();
                                            Log.d("path123", "editext value is: " + editTextInput);
                                            int id = Integer.parseInt(editTextInput);
                                            MyDataBaseHelper myDB = new MyDataBaseHelper(Admin.this);
                                            SQLiteDatabase wrtDB = myDB.getWritableDatabase();
                                            String[] whereClause = {String.valueOf(id)};
                                            //wrtDB.delete(myDB.TABLE_NAME, myDB.COLUMN_ID + "=?", whereClause);
                                            Toast.makeText(Admin.this, "Row " + id + " was Deleted", Toast.LENGTH_SHORT).show();
                                        }catch (NumberFormatException e)
                                        {
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

                    }
                });
                builder.show();
            }
        });

    }

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
                        filePath =  uri;//TODO get the true file path, as it crashes rn
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

        try {
            ContentValues cv = new ContentValues();
            MyDataBaseHelper myDB = new MyDataBaseHelper(this);
            SQLiteDatabase db = myDB.getWritableDatabase();

            String[] csvHeaderLine = {
                    myDB.COLUMN_ID,
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
                if (csvLine[0].equals("_id")) {
                    continue;
                }

                int i = 0;

                for(String col : csvHeaderLine) {
                    cv.put(col, Integer.parseInt(csvLine[i]));
                    i++;
                }

                if (   (Integer.parseInt(csvLine[1]) > 0)
                        && ((Integer.parseInt(csvLine[0]) > 0) && (Integer.parseInt(csvLine[0]) < 200))) {
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


    void importCSV2(Uri csvFileUri) throws IOException, CsvValidationException {
        Context context = this;

        try {
            ContentValues cv = new ContentValues();
            MyDataBaseHelper myDB = new MyDataBaseHelper(this);
            SQLiteDatabase db = myDB.getWritableDatabase();

            // open file and attach a file reader to the uri
            FileReader fileReader = new FileReader(
                    this.getContentResolver()
                            .openFileDescriptor(csvFileUri, "r")
                            .getFileDescriptor()
            );

            // now attach a CSV reader to file reader
            CSVReader reader = new CSVReader(fileReader);

            // create a CSV and DB record that we will fill in
            //String[] csvLine;

            //fileReader.close();
            // for each record returned from the CSV file, add a record to DB
            reader.getLinesRead();
            List<String[]> fullCSV = reader.readAll();

            int i = 0;
            while (i < fullCSV.size()) {

                String[] csvLine = fullCSV.get(i++);

                // check for the CSV header row and skip it
                if (csvLine[0].equals("match_num")) {
                    continue;
                }

                Log.d("path123", csvLine[0]);
               // cv.put(myDB.COLUMN_MATCHNUM, Integer.parseInt(csvLine[0]));
                Log.d("path123", "3");

                if (   (Integer.parseInt(csvLine[1]) > 0)
                        && ((Integer.parseInt(csvLine[0]) > 0) && (Integer.parseInt(csvLine[0]) < 200))) {
                    // ...add the team round data record to the DB
                    db.insert("Match_Data", null, cv);
                }

                cv.clear();
            }
            reader.close();
            fileReader.close();
            this.getContentResolver()
                    .openFileDescriptor(csvFileUri, "r")
                    .close();
            Toast.makeText(context, "Imported CSV", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            Log.d("path123", String.valueOf(e));
            e.printStackTrace();
            // throw new RuntimeException(e);

        } /*catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }*/ catch (CsvException e) {
            throw new RuntimeException(e);
        }

    }


    void oldImportCSV(Uri uriFile)
    {
        Context context = this;
        InputStream inStream = null;
        try {
            inStream = getContentResolver().openInputStream(uriFile);;
            Log.d("path123", "confirm");
        } catch (IOException e) {
            Log.d("path123", "error");
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        MyDataBaseHelper myDB = new MyDataBaseHelper(this);
        SQLiteDatabase db = myDB.getWritableDatabase();
        try {
            boolean skipLine = true;
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
                Log.d("path123", String.valueOf(colums.length));
                if (colums.length != 23) {
                    Log.d("path123", "Skipping Bad CSV Row");
                    continue;
                }

                if(!skipLine) { //TODO do the parse int for all of them.
                    ContentValues cv = new ContentValues();
                    //cv.put(myDB.COLUMN_MATCHNUM, Integer.parseInt(colums[0].trim().substring(1,colums[0].length()-1)));

                    db.insert("Match_Data", null, cv);
                    Log.d("path123", "yes");
                }else{
                    skipLine = false;
                }
            }
            Toast.makeText(context, "Imported CSV", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            Log.d("path123", "no");
            e.printStackTrace();
        }
    }
}