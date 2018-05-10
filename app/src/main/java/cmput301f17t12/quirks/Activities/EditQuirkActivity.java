package cmput301f17t12.quirks.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class EditQuirkActivity extends AppCompatActivity {

    private static final String TAG = "EditQuirk";
    private ArrayList<Day> occurence;
    public static String date2;
    public CheckBox radButMon;
    public CheckBox radButTue;
    public CheckBox radButWed;
    public CheckBox radButThur;
    public CheckBox radButFri;
    public CheckBox radButSat;
    public CheckBox radButSun;
    public Quirk incomingQuirk;
    private Date startDate;
    public TextView SelectDate;
    private DatePickerDialog.OnDateSetListener SelectDateListener;

    User currentlylogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quirk);

        radButMon = (CheckBox)findViewById(R.id.QuirkEditradioButtonMon);
        radButTue = (CheckBox)findViewById(R.id.QuirkEditradioButtonTue);
        radButWed = (CheckBox)findViewById(R.id.QuirkEditradioButtonWed);
        radButThur = (CheckBox)findViewById(R.id.QuirkEditradioButtonThur);
        radButFri = (CheckBox)findViewById(R.id.QuirkEditradioButtonFri);
        radButSat = (CheckBox)findViewById(R.id.QuirkEditradioButtonSat);
        radButSun = (CheckBox)findViewById(R.id.QuirkEditradioButtonSun);

        EditText TitleEdit = (EditText)findViewById(R.id.QuirkeditTextTitle);
        EditText TypeEdit = (EditText)findViewById(R.id.QuirkeditTextType);
        EditText ReasonEdit = (EditText)findViewById(R.id.QuirkeditTextReason);
        EditText GoalEdit = (EditText)findViewById(R.id.QuirkeditTextGoal);
        SelectDate = (TextView)findViewById(R.id.textViewSelectStartingDateEdit);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

//        currentlylogged = HelperFunctions.getUserObject(jestID);
        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());

        Integer incomingQuirkIndex = getIntent().getIntExtra("SELECTED_QUIRK_INDEX", -1);

        if (incomingQuirkIndex == -1) {
            Log.i("Error", "Failed to receive SELECTED_QUIRK_INDEX");
            finish();
        }

        incomingQuirk = currentlylogged.getQuirks().getQuirk(incomingQuirkIndex);
        TitleEdit.setText(incomingQuirk.getTitle());
        TypeEdit.setText(incomingQuirk.getType());
        ReasonEdit.setText(incomingQuirk.getReason());
        GoalEdit.setText(String.valueOf(incomingQuirk.getGoalValue()));
        final Date incomingDate = incomingQuirk.getDate();
        startDate = incomingQuirk.getDate();

      /*  Log.d(TAG, "onCreate: the incoming date is " + incomingDate);
        Log.d(TAG, "onCreate: the incoming date is " + incomingDate.getDate());
        Log.d(TAG, "onCreate: the incoming date is " + incomingDate.getYear());
        Log.d(TAG, "onCreate: the incoming date is " + incomingDate.getDay());
        Log.d(TAG, "onCreate: the incoming date is " + incomingDate.getMonth());
       */
        String dateFormat = "MM/dd/yyyy ";
        //  Log.d(TAG, "onCreate: the incoming date year is " + incomingDate.getYear());
        SimpleDateFormat datePat = new SimpleDateFormat(dateFormat);
        String dateToSet = datePat.format(incomingDate);
        Log.d(TAG, "onCreate: the date is  " +  incomingDate.toString());
        SelectDate.setText(dateToSet);
        setOccurences();

        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal  = Calendar.getInstance();
                int year = incomingQuirk.getDate().getYear()+1900;
                int month = incomingQuirk.getDate().getMonth();
                int day = incomingQuirk.getDate().getDate();
                DatePickerDialog dialog = new DatePickerDialog(EditQuirkActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,SelectDateListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        SelectDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date2 = month + "/" + day + "/" + year;
                SelectDate.setText(date2);
                Log.d(TAG, "onDateSet: the date is now   " + date2);
                Log.d(TAG, "onDateSet: the year is now " + year);
                Log.d(TAG, "onDateSet: the month is now " + month);
                Log.d(TAG, "onDateSet: the day is now " + day);
                startDate = new Date(year-1900,month-1,day);
                Log.d(TAG, "onDateSet: the startdate is now " + startDate);

            }
        };

    }

    public void saveButtonClicked(View v){
        String type = ((EditText)findViewById(R.id.QuirkeditTextType)).getText().toString();
        String title = ((EditText)findViewById(R.id.QuirkeditTextTitle)).getText().toString();
        String reason = ((EditText)findViewById(R.id.QuirkeditTextReason)).getText().toString();
        String goal = ((EditText)findViewById(R.id.QuirkeditTextGoal)).getText().toString();

        ArrayList<Day> occurences = occurenceItemSelected();

        if((type.equals(""))||(title.equals(""))||(goal.equals(""))){
            Toast.makeText(EditQuirkActivity.this,"All blanks must be filled", Toast.LENGTH_SHORT).show();

        }

        else{
            incomingQuirk.setTitle(title);
            incomingQuirk.setType(type);
            incomingQuirk.setReason(reason);
            incomingQuirk.setOccDate(occurences);
            incomingQuirk.setGoalValue(Integer.parseInt(goal));
            Log.d(TAG, "saveButtonClicked: the startDate is now " + startDate);
            incomingQuirk.setDate(startDate);
            Log.d(TAG, "saveButtonClicked:  the incoming Quirkdate is now " + incomingQuirk.getDate());

//            ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
//            updateUserTask.execute(currentlylogged);
            HelperFunctions.updateSingleUser(getApplicationContext(), currentlylogged);

            finish();
        }
    }

    public void cancelButtonClicked(View v){
        finish();
    }

    public void DeleteButtonClicked(View v) {
        currentlylogged.getQuirks().removeQuirk(incomingQuirk);
//        ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
//        updateUserTask.execute(currentlylogged);
        HelperFunctions.updateSingleUser(getApplicationContext(), currentlylogged);
        finish();
    }

    private ArrayList<Day> occurenceItemSelected(){
        ArrayList<Day> Day = new ArrayList<Day>();
        if(radButMon.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.MONDAY);
        }
        if(radButTue.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.TUESDAY);
        }
        if(radButWed.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.WEDNESDAY);
        }
        if(radButThur.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.THURSDAY);
        }
        if(radButFri.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.FRIDAY);
        }
        if(radButSat.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.SATURDAY);
        }
        if(radButSun.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.SUNDAY);
        }
        return Day;
    }

    private void setOccurences() {
        ArrayList<Day> occurence = incomingQuirk.getOccDate();

        if (occurence.contains(Day.MONDAY)) {
            radButMon.setChecked(true);
        }
        if (occurence.contains(Day.TUESDAY)) {
            radButTue.setChecked(true);
        }
        if (occurence.contains(Day.WEDNESDAY)) {
            radButWed.setChecked(true);
        }
        if (occurence.contains(Day.THURSDAY)) {
            radButThur.setChecked(true);
        }
        if (occurence.contains(Day.FRIDAY)) {
            radButFri.setChecked(true);
        }
        if (occurence.contains(Day.SATURDAY)) {
            radButSat.setChecked(true);
        }
        if (occurence.contains(Day.SUNDAY)) {
            radButSun.setChecked(true);
        }
    }
}


