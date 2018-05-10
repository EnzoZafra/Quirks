package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cmput301f17t12.quirks.*;
import cmput301f17t12.quirks.Adapters.QuirkListItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.*;

public class QuirksActivity extends BaseActivity {
    private QuirkList quirkList = new QuirkList();
    private Date dateFilter;
    private QuirkListItemAdapter adapter;
    private String jestID;
    private Spinner spinner;
    private Button applyButton;
    private HashMap filterHashMap;

    /**
     * Called when the activity is first created. This initializes the activity and all class variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        jestID = settings.getString("jestID", "defaultvalue");
        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        applyButton = (Button) findViewById(R.id.applyFilterButton);
//        final User currentlylogged = HelperFunctions.getUserObject(jestID);
        final User currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        quirkList = currentlylogged.getQuirks();

        // Filter Hash Map: Filter position: Old Position
        // Initialize HashMap so each key = value
        filterHashMap = new HashMap(quirkList.size());
        for(int i = 0; i < quirkList.size(); i++){
            filterHashMap.put(i, i);
        }

        // create instance of custom adapter
        adapter = new QuirkListItemAdapter(quirkList, this);

        // create listView and assign custom adapter
        ListView lView = (ListView) findViewById(R.id.quirk_listview);
        lView.setAdapter(adapter);

        Button QuirkCreate = (Button)findViewById(R.id.add_quirk_button);
        QuirkCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuirksActivity.this,AddQuirkActivity.class);
                intent.putExtra("Editing",1);
                startActivityForResult(intent,1);
            }
        });

        // Spinner element
        spinner = (Spinner) findViewById(R.id.filter_type);

        // TODO: Handle spinner click
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<>();
        categories.add("All Habits");
        categories.add("Today\'s Habits");

        // Creating adapter for spinner, and attach adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String query = "";
//                String extraString = "";
                if (String.valueOf(spinner.getSelectedItem()).equals("All Habits")){
                    query = getQueryFilterAll();
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Today\'s Habits")){
                    query = getQueryFilterToday();
                }
                if (query.equals("")){
                    Log.i("Error", "Failed to get query based on spinner selection");
                }
                else{
//                    applyFilter(query); // part 5
                    offlineFilter(query, currentlylogged);

                }
            }

        });

        // Create listView handler (for custom listview important that all items must have focusable = false)
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int clickedPosition,
                                    long id) {
                int quirkPosition = getFilteredIndex(clickedPosition);
                Intent intent = new Intent(QuirksActivity.this, EditQuirkActivity.class);
                intent.putExtra("SELECTED_QUIRK_INDEX", quirkPosition);
                startActivity(intent);
            }
        });
    }

    /**
     * Function called when the app returns to this activity. This calls functions to update the ListView and notifies the adapter of any changes
     */
    @Override
    protected void onResume(){
        super.onResume();
        updateQuirkList(jestID);
        filterHashMap.clear();
        for(int i = 0; i < quirkList.size(); i++){
            filterHashMap.put(i, i);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Get the index of the quirk in the user's quirklist based on the filtered index
     * @param filterIndex Index of the filtered quirk
     * @return Index of the quirk within the full quirkList
     */
    public int getFilteredIndex(Integer filterIndex) {
        return (int) filterHashMap.get(filterIndex);
    }

    /**
     * Update the quirkList used in the Quirk ListView. This is called if the user has added or edited a quirk
     * @param jestID
     */
    public void updateQuirkList(String jestID){
//        User currentlylogged = HelperFunctions.getUserObject(jestID);
        User currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        QuirkList tempList = currentlylogged.getQuirks();
        quirkList.clearAndAddQuirks(tempList);
    }

    public String getQueryFilterAll(){
        String query = "all";
        // for the user
        // look in quirklist
        // match all
        return query;
    }

    public String getQueryFilterToday(){
        String query = "today";
        // for the user
        // look in quirklist
        // match where Day today is within occDate
        return query;
    }

    /**
     * Creates a new quirkList of all filtered quirks. It then calls applyOfflineFilter to apply the filter
     * @param query
     * @param user
     */
    public void offlineFilter(String query, User user){
        QuirkList userQuirks = user.getQuirks();
        QuirkList filteredQuirks = new QuirkList();
        int size = user.getQuirks().size();


        if (query.equals("all")){
            // show all
            filterHashMap.clear();
            for(int i = 0; i < quirkList.size(); i++){
                filterHashMap.put(i, i);
            }

            updateQuirkList(jestID);
            adapter.notifyDataSetChanged();
        }
        else if (query.equals("today")){
            // show all today
            int j = 0;
            filterHashMap.clear();
            for (int i = 0; i < size; i++){
                Quirk curQuirk = userQuirks.getQuirk(i);
                ArrayList<Day> occurences = curQuirk.getOccDate();
                Day today = getToday();

                if (occurences.contains(today)){
                    filterHashMap.put(j, i);
                    filteredQuirks.addQuirk(curQuirk);
                    j++;
                }
            }
            applyOfflineFilter(filteredQuirks);
        }
        else{
            System.out.println("offline filter failed if/else statements");
        }
    }

    /**
     * Updates the listView to now show the current filtered quirks
     * @param filteredQuirks
     */
    public void applyOfflineFilter(QuirkList filteredQuirks){
        quirkList.clearAndAddQuirks(filteredQuirks);
        adapter.notifyDataSetChanged();
    }


    // @TODO will be implemented in part 5
    public void applyFilter(String query){

        ElasticSearchUserController.GetQuirksTask getQuirksTask
                = new ElasticSearchUserController.GetQuirksTask();
        getQuirksTask.execute(query, jestID);

        try {
            ArrayList<Quirk> quirks = getQuirksTask.get();

            System.out.println("size: " + quirks.size());
            for (int i=0; i< quirks.size(); i++){
                System.out.println(quirks.get(i).getType());
            }

        }
        catch (Exception e) {
            Log.i("Error", "Failed to get filtered quirks from the async object");
            Log.i("Error", e.toString());
        }

    }

    @Override
    int getContentViewId() { return R.layout.activity_quirks; }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_quirklist;
    }

    /**
     * Return enumerated date based on today's day of the week
     * @return The enumerated value for the current weekday
     */
    private Day getToday() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return Day.SUNDAY;

            case Calendar.MONDAY:
                return Day.MONDAY;

            case Calendar.TUESDAY:
                return Day.TUESDAY;

            case Calendar.WEDNESDAY:
                return Day.WEDNESDAY;

            case Calendar.THURSDAY:
                return Day.THURSDAY;

            case Calendar.FRIDAY:
                return Day.FRIDAY;

            case Calendar.SATURDAY:
                return Day.SATURDAY;
        }
        return null;
    }

}
