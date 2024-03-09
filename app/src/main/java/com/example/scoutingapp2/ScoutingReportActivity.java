package com.example.scoutingapp2;

import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.List;

public class ScoutingReportActivity extends AppCompatActivity{

    // These are used for sorting and filtering report tables
    protected String ReportFilteredTeamNumberStringList = "0";
    protected boolean ReportSortAsc = true;
    protected int ReportSortColumn = 0;
    //MyDataBaseHelper myDB = new MyDataBaseHelper(this);

    // This interface is used for updating the sorting of a report when a heading is clicked
    public interface ReportUpdateCommand
    {
        void update(String orderBy, String orderType);
    }

    //region set background color UNUSED
    // set layout background color
    protected void SetLayoutBackgroundColor(int layoutId, String teamColor) {
        if (layoutId != 0) {
            ConstraintLayout layout = (ConstraintLayout) findViewById(layoutId);
            if (teamColor.equals("Blue")) {
                layout.setBackgroundColor(getResources().getColor(R.color.light_red));
            } else if (teamColor.equals("Red")) {
                layout.setBackgroundColor(getResources().getColor(R.color.light_blue));
            }
        }
    }


    // this sets layout background color
    protected void UpdateCommonLayoutItems(int layoutId) {
        if (layoutId != 0) {
            // tbd - determine background color based on team, etc.
            // SetLayoutBackgroundColor(layoutId, "Red");
        }
    }
    //endregion

    //region  add string to spinner UNUSED
    // add strings to spinner and set font size
    protected void AddStringsToSpinner(int spinnerViewId, List<String> stringList, final int fontSize) {
        if ((spinnerViewId != 0) && (stringList != null)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, stringList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            final Spinner spinnerItems = (Spinner) findViewById(spinnerViewId);
            spinnerItems.setAdapter(adapter);

            // set the text size of the spinner's selected view
            spinnerItems.setSelection(0, true);
            View selectedItemView = spinnerItems.getSelectedView();
            if (selectedItemView != null) {
                ((TextView) selectedItemView).setTextSize(fontSize);
            }

            // when changed, set the text size of the team number selection spinner
            spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> parent) { }
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // TBD: this can throw an exception on screen rotates, so we put
                    // the null check in, but that means in those cases the text size
                    // reverts back to the default.  Need to figure that out!
                    if (selectedItemView != null) {
                        // set the text size of the spinner's selected view
                        ((TextView) selectedItemView).setTextSize(fontSize);
                    }
                }
            });
        }
    }
    //endregion

    // add heading strings to specified table.  used for reports.
    protected void AddHeaderStringsAsRowToReportTable(int tableId, String[] headerStrings,
                                                      final ReportUpdateCommand reportUpdateCommand, int headerTextSize) {
        // get handle to display table (TableLayout)
        TableLayout table = (TableLayout)findViewById(tableId);
        table.removeAllViews();
        Log.d("string array124", Arrays.deepToString(headerStrings));
        // create a common layout param group for all of our rows
        TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, headerStrings.length);
        TableRow.LayoutParams lpItem = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);

        // create a header row
        TableRow hdr = new TableRow(this);
        hdr.setLayoutParams(lpRow);
        hdr.setBackgroundResource(R.color.white);
        Log.d("table123", String.valueOf(headerStrings.length));
        // add each heading string to our header row
        for(int i = 0; i < headerStrings.length; i++) {
            final int headingIndex = i; // needs to be final for onClick
            String heading = headerStrings[headingIndex];
            TextView hdrView = new TextView(this);
            hdrView.setLayoutParams(lpItem);
            hdrView.setText(heading);
            hdrView.setTextSize(headerTextSize);
            // make the column heading we are sorting on italic
            hdrView.setTypeface(null, (ReportSortColumn == headingIndex) ? Typeface.BOLD_ITALIC : Typeface.BOLD);
            hdrView.setPadding(2, 0, 2, 0);
            hdrView.setGravity(Gravity.CENTER);
            Log.d("table test", String.valueOf(table.getMeasuredWidth()));
            // set an onclick handler for each header so we can update the sort when clicked
            hdrView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //make sure this list has both the simple and advance strings
                    String[] columns = {"team_num", "Total_Points", "_Auto_Pieces","_Cycles","_teleSpeaker", "_teleAmp"};
                    if (ReportSortColumn == headingIndex) {
                        // reverse the current sort order
                        ReportSortAsc = !ReportSortAsc;
                    } else {
                        // set sort column to new index
                        ReportSortColumn = headingIndex;
                        // the first time a heading is clicked, default to descending
                        // except for index 0, which tends to be TeamNumber
                        ReportSortAsc = (headingIndex == 0);
                    }
                    // redisplay the entire table
                    // tbd this is used for sorting.  figure it out
                    if(ReportSortAsc == false)
                    {
                        reportUpdateCommand.update("DESC", columns[headingIndex]);
                        Log.d("table123", "yes");
                    }else{
                        reportUpdateCommand.update("ASC", columns[headingIndex]);
                        Log.d("table123", "no");
                    }
                    Log.d("table123", "Index is " + columns[headingIndex]);

                }
            });
            hdr.addView(hdrView);
        }
        // add the header row to the table (it will be the first row)
        table.addView(hdr);
    }

    // add data strings to specified table.  used for reports.
    protected TableRow AddDataStringsAsRowToReportTable(int tableId, String[] dataStrings) {
        // get handle to display table (TableLayout)
        TableLayout table = (TableLayout)findViewById(tableId);
        int rowNumber = table.getChildCount();

        // create a common layout param group for all of our rows and items
        TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, dataStrings.length);
        TableRow.LayoutParams lpItem = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);

        // create a new row to hold our data values
        TableRow row = new TableRow(this);
        row.setLayoutParams(lpRow);

        // alternate the background color of each row
        if ((rowNumber % 2) == 0) {
            row.setBackgroundResource(R.color.light_blue);
        } else {
            row.setBackgroundResource(R.color.light_red);
        }

        // add each data string as an item to our row
        for(String dataString : dataStrings) {
            TextView dataView = new TextView(this);
            dataView.setLayoutParams(lpItem);
            dataView.setText(dataString);
            dataView.setPadding(2, 0, 2, 0);
            dataView.setGravity(Gravity.CENTER);
            dataView.setTextSize(15);
            row.addView(dataView);
        }

        // add the data row to the end of the table
        table.addView(row);
        return row;
    }

    // set a spinners value
    protected void SetSpinnerByValue(int spinnerId, String value) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        for(int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}