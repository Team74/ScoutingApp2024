package com.example.scoutingapp2;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CSV {

    private  Context context;

    String baseDir;

    public CSV(@Nullable Context context) {
        this.context = context;
    }

    // files created programmatically in downloads folder may not
    // appear immediately to other system utilities.  This sends out
    // a notification to the system about it.
    private void makeCreatedFileVisibleInDownloads(String downloadFilename) {
        File downloadFile = new File(downloadFilename);
        String mimeType = "text/csv";
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.addCompletedDownload(downloadFile.getName(), downloadFilename, true,
                    mimeType, downloadFile.getAbsolutePath(), downloadFile.length(), true);
        }
    }

    // export MatchData to CSV
    public void exportMatchData(String baseDir, String message) {
        try {
            MyDataBaseHelper myDB = new MyDataBaseHelper(this.context);
            Random r = new Random();

            Cursor cursor = myDB.readAllMatchData();
            if(cursor.getCount() == 0) {
                Toast.makeText(this.context, "No Data", Toast.LENGTH_SHORT).show();
            } else {

                // makes the filepath
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                String filePath = baseDir + File.separator + "MatchData-" + androidId + r.nextInt(1000) + message + currentDate + ".csv"; //todo add note to file name

                // creates file and attaches CSV writer to it
                CSVWriter writer = new CSVWriter(new FileWriter(filePath, false));

                // write a header record to the CSV file to aid in importing into Tableau
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
                writer.writeNext(csvHeaderLine);

                cursor.moveToFirst();
                do
                {
                    List<String> csvDataLine = new ArrayList<String>();
                    for(String col : csvHeaderLine) {
                        Integer idx = cursor.getColumnIndex(col);
                        if (idx >= 0) {
                            csvDataLine.add(cursor.getString(idx));
                        } else {
                            throw new Exception("bad column");
                        }
                    }

                    // write the CSV record to the file
                    String[] line = csvDataLine.toArray(new String[0]);
                    writer.writeNext(line);

                }
                while(cursor.moveToNext());

                // close the CSV file and "publish" it to the system
                writer.close();

                makeCreatedFileVisibleInDownloads(filePath);
                Toast.makeText(this.context, "MatchData CSV file successfully exported", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.context, "Error creating MatchData CSV file", Toast.LENGTH_SHORT).show();
        }
    }
}
