package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Adapters.EventListItemAdapter;
import cmput301f17t12.quirks.Adapters.QuirkListItemAdapter;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class EventListActivity extends AppCompatActivity {

    private ImageView ivTopBorder;
    private TextView tvQuirkName;
    private EventListItemAdapter adapter;
    private EventList eventList = new EventList();
    private Integer pos;
    private Quirk quirk;
    private String jestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ivTopBorder = (ImageView) findViewById(R.id.el_topborder);
        tvQuirkName = (TextView) findViewById(R.id.el_quirkname);
        ivTopBorder.setBackgroundColor(0x8B999D);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        jestID = settings.getString("jestID", "defaultvalue");
        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }
        Log.d("DEBUG", jestID);

        // Use the quirk's pos to get the Eventlist
        pos = (Integer) getIntent().getSerializableExtra("quirkPos");
//        User currentlylogged = HelperFunctions.getUserObject(jestID);
        User currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        quirk = currentlylogged.getQuirks().getQuirk(pos);
        eventList = quirk.getEventList();

        tvQuirkName.setText(String.format("%s - Events", quirk.getTitle()));
        adapter = new EventListItemAdapter(eventList, this);
        ListView lView = (ListView) findViewById(R.id.el_eventslistview);
        lView.setAdapter(adapter);
    }

    // Called if activity resumes. Loads any event updates from the database
    @Override
    protected void onResume(){
        super.onResume();
        updateEventList(jestID);
        adapter.notifyDataSetChanged();
    }

    // Grabs the latest events from the db
    public void updateEventList(String jestID){
//        User currentlylogged = HelperFunctions.getUserObject(jestID);
        User currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        quirk = currentlylogged.getQuirks().getQuirk(pos);
        EventList tempEventList = currentlylogged.getQuirks().getQuirk(pos).getEventList();
        eventList.clearAndAddEvents(tempEventList);
    }

    public void launchEditEvent(Integer selectEventIndex) {
        Intent intent = new Intent(EventListActivity.this, EditEventActivity.class);
        intent.putExtra("SELECTED_EVENT_INDEX", selectEventIndex);
        intent.putExtra("SELECTED_QUIRK_INDEX", pos);
        startActivity(intent);
    }
}
