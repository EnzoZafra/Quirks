package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import cmput301f17t12.quirks.Adapters.NewsItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.BottomNavigationViewHelper;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Interfaces.Newsable;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class MainActivity extends BaseActivity {
    EventList filteredEvents = new EventList();
    private ArrayList<Newsable> newsitems = new ArrayList<>();
    private NewsItemAdapter adapter;
    private User currentlylogged;
    private Spinner spinner;
    private Button applyButton;
    private Button mapButton;
    private EditText filterValue;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);

        // get the user
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
//        if (extras != null){
//            if (extras.containsKey("user")){
//                currentlylogged = (User) getIntent().getSerializableExtra("user");
//            }
//            else{
//                Log.i("Error", "Intent had extras but not user");
//            }
//        }
//        else{
//
//
//            String jestID = settings.getString("jestID", "defaultvalue");
//
//            if (jestID.equals("defaultvalue")) {
//                Log.i("Error", "Did not find correct jestID");
//            }
//
//            currentlylogged = HelperFunctions.getUserObject(jestID);
//        }

        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());

        spinner = (Spinner) findViewById(R.id.spinner);
        applyButton = (Button) findViewById(R.id.applyFilterButton);
        mapButton = (Button) findViewById(R.id.mapButton);
        filterValue = (EditText) findViewById(R.id.filterValue);

        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<>();
        categories.add("By Type");
        categories.add("By Comment");

        // Creating adapter for spinner, and attach adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        ArrayList<Quirk> quirks = currentlylogged.getQuirks().getList();

        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String query = "";
                String extraString = "";
                if (String.valueOf(spinner.getSelectedItem()).equals("By Type")){
                    query = getQueryFilterType();
                    extraString = filterValue.getText().toString();
                }
                else if (String.valueOf(spinner.getSelectedItem()).equals("By Comment")){
                    query = getQueryFilterComment();
                    extraString = filterValue.getText().toString();
                }
                if (query.equals("")){
                    Log.i("Error", "Failed to get query based on spinner selection");
                }
                else{
                    offlineFilter(query, extraString, currentlylogged);
                }

            }

        });

        for (int i = 0; i < quirks.size(); i++) {
            ArrayList<Event> temp = quirks.get(i).getEventList().getList();
            for (int j = 0; j < temp.size(); j++) {
                newsitems.add(temp.get(j));
                filteredEvents.addEvent(temp.get(j));
            }
        }

        Collections.sort(newsitems, new Comparator<Newsable>() {
            public int compare(Newsable m1, Newsable m2) {
                return m2.getDate().compareTo(m1.getDate());
            }
        });

        // instantiate custom adapter
        adapter = new NewsItemAdapter(newsitems, this);

        // handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.newsfeed_listview);
        lView.setAdapter(adapter);

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(v.getContext(), MapActivity.class);
                mapIntent.putExtra("FILTERED_LIST", filteredEvents);
                startActivity(mapIntent);
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_home;
    }

    public String getQueryFilterType(){
        String query = "type";
        // for the user
        // look in quirklist
        // match where type is type
        return query;
    }

    public String getQueryFilterComment(){
        String query = "comment";
        // for the user
        // look in quirklist
        // look at events
        // match where comment contains the word
        return query;
    }

    public void offlineFilter(String query, String arg, User user){
        QuirkList userQuirks = user.getQuirks();
        filteredEvents.getList().clear();
        int size = user.getQuirks().size();

        if (arg.equals("")){ // no filter -> show all
            for (int i = 0; i < size; i++){
                Quirk curQuirk = userQuirks.getQuirk(i);
                int size2 = curQuirk.getEventList().size();
                for (int j = 0; j < size2; j++){
                    filteredEvents.addEvent(curQuirk.getEvent(j));
                }
            }
            applyOfflineTypeFilter(filteredEvents);
        }
        else if (query.equals("type") && !arg.equals("")){
            // show all events with that type -> just give the eventlist of that quirk
            for (int i = 0; i < size; i++){
                Quirk curQuirk = userQuirks.getQuirk(i);
                if (curQuirk.getType().equals(arg)){
                    EventList templist = curQuirk.getEventList();
                    for (int z = 0; z < templist.size(); z++) {
                        filteredEvents.addEvent(templist.getEvent(z));
                    }
                }
            }
            applyOfflineTypeFilter(filteredEvents);
        }
        else if (query.equals("comment") && !arg.equals("")){
            // show all with comment matching
            for (int i = 0; i < size; i++){
                Quirk curQuirk = userQuirks.getQuirk(i);
                EventList curEventList = curQuirk.getEventList();
                int size2 = curEventList.size();
                for (int j = 0; j < size2; j++){
                    String pattern = ".*" + arg + ".*";
                    if (curEventList.getEvent(j).getComment().matches(pattern)){
                        filteredEvents.addEvent(curEventList.getEvent(j));
                    }
                }
            }


            applyOfflineCommentFilter(filteredEvents);

        }
        else{
            Log.i("Error", "offline filter failed if/else statements");
        }
    }

    public void applyOfflineTypeFilter(EventList events){
        newsitems.clear();
        newsitems.addAll(events.getList());
        adapter.notifyDataSetChanged();
    }

    public void applyOfflineCommentFilter(EventList events){
        newsitems.clear();
        newsitems.addAll(events.getList());
        adapter.notifyDataSetChanged();
    }


}


