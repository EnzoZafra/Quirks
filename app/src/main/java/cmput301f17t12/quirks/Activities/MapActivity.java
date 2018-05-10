package cmput301f17t12.quirks.Activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Geolocation;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

import static cmput301f17t12.quirks.R.id.map;

public class MapActivity extends BaseActivity
        implements OnMapReadyCallback, OnMapClickListener, OnCameraIdleListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private FusedLocationProviderClient userLocationClient;
    private Geolocation userLoc;
    private String jestID;
    private User currentlylogged;
    SharedPreferences settings;

    private TextView mTapTextView;
    private GoogleMap mMap;

    private Marker userMarker;


    // [MAP]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

//        currentlylogged = HelperFunctions.getUserObject(jestID);
        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        mTapTextView = (TextView) findViewById(R.id.tap_text);
        userLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Default Location for the User
        userLoc = new Geolocation(53.5232,-113.5263);

        Log.d("DEBUG", "Stage 1");

        //TODO: Maybe assign a flag based on the permission and then either show the users location or not

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("DEBUG", "Requesting Permission");
            // ActivityCompat#requestPermissions here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else{
            Log.d("DEBUG", "Not Requesting Permission");

            userLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d("DEBUG", "Got location");

                            userLoc.setLatitude(53.5232);
                            userLoc.setLongitude(-113.5263);

                            if (location != null) {

                                Log.d("DEBUG", "Default Location");
                                // Default Position -> University of Alberta
                                userLoc.setLatitude(location.getLatitude());
                                userLoc.setLongitude(location.getLongitude());
                            }
                        }

                    });

        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        Button NearbyMapButton = (Button) findViewById(R.id.nearby_map_button);
        NearbyMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventList eventLocations = getEventLoc("Nearby");
                displayEventListLoc(eventLocations);
            }
        });

        Button FollowingMapButton = (Button) findViewById(R.id.following_map_button);
        FollowingMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventList eventLocations = getEventLoc("Following");
                displayEventListLoc(eventLocations);
            }
        });

        Button MyEventsMapButton = (Button) findViewById(R.id.my_events_map_button);
        MyEventsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventList eventLocations = getEventLoc("MyEvents");
                displayEventListLoc(eventLocations);
            }
        });

    }

    public EventList getEventLoc(String filterType){
        EventList events = new EventList();
        QuirkList userQuirkList;
        QuirkList friendsQuirkList;

        // Get users quirks
        userQuirkList = currentlylogged.getQuirks();

        //
        friendsQuirkList = getFriendsQuirks();



        if(filterType.equals("Nearby")){
            for (Quirk currQuirk: userQuirkList.getList()) {
                for(Event currEvent: currQuirk.getEventList().getList()){
                    if(currEvent.getGeolocation() != null) {
                        if (haversine(currEvent.getGeolocation().getLatitude(), currEvent.getGeolocation().getLongitude(),
                                userLoc.getLatitude(), userLoc.getLongitude()) < 5){
                            events.addEvent(currEvent);
                        }
                    }
                }
            }

            for (Quirk currQuirk: friendsQuirkList.getList()) {
                for(Event currEvent: currQuirk.getEventList().getList()){
                    if(currEvent.getGeolocation() != null) {
                        if (haversine(currEvent.getGeolocation().getLatitude(), currEvent.getGeolocation().getLongitude(),
                                userLoc.getLatitude(), userLoc.getLongitude()) < 5){
                            events.addEvent(currEvent);
                        }
                    }
                }
            }

        }

        else if(filterType.equals("Following")){
            for (Quirk currQuirk: friendsQuirkList.getList()) {
                for(Event currEvent: currQuirk.getEventList().getList()){
                    if (currEvent.getGeolocation() != null) {
                        events.addEvent(currEvent);
                    }
                }
            }
        }

        else if(filterType.equals("MyEvents")){
            for (Quirk currQuirk: userQuirkList.getList()) {
                for (Event currEvent : currQuirk.getEventList().getList()) {
                    if (currEvent.getGeolocation() != null) {
                        events.addEvent(currEvent);
                    }
                }
            }
        }

        return events;

    }

    public double haversine(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radious of the earth
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return(R * c);
    }


    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }


    public QuirkList getFriendsQuirks(){
        QuirkList quirkList = new QuirkList();

        ArrayList<String> friendslist = currentlylogged.getFriends();
        StringBuilder query = new StringBuilder("{" +
                "  \"query\": {" +
                "    \"filtered\": {" +
                "      \"filter\":  {" +
                "        \"terms\": {" +
                "          \"username\": [");
        for (int i = 0; i < friendslist.size(); i++) {
            query.append("\"").append(friendslist.get(i)).append("\"");
            if (i != friendslist.size() - 1) {
                query.append(", ");
            }
        }
        query.append("           ]" + "        }" + "      }" + "    }" + "  }" + "}");
        ArrayList<User> followingUsers = HelperFunctions.getAllUsers(query.toString());

        if (followingUsers != null){
            if (!followingUsers.isEmpty()) {
                for (User user : followingUsers) {
                    quirkList.addAllQuirks(user.getQuirks());
                }
            }
        }
        

        return quirkList;
    }

    public void displayEventListLoc(EventList events){

        mMap.clear();
        LatLng userLatLon = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
        userMarker = mMap.addMarker(new MarkerOptions().position(userLatLon)
                .title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        for(Event event : events.getList()){
            LatLng currLatLng = new LatLng(event.getGeolocation().getLatitude(), event.getGeolocation().getLongitude());
            String currComment = event.getComment();
            String userName = event.getUser();
            mMap.addMarker(new MarkerOptions().position(currLatLng).title("[ " + userName + " ]: " + currComment));
        }
    }

    // [MAP]
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Log.d("DEBUG", "Map activity is ready");

        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        mMap.setOnCameraIdleListener(this);

        LatLng userLatLon = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
        userMarker = googleMap.addMarker(new MarkerOptions().position(userLatLon)
                .title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLon));

        // If there is a filtered event list from the intent, show it
        Object tmp = getIntent().getSerializableExtra("FILTERED_LIST");
        if (tmp != null) {
            EventList filteredEventList = (EventList) tmp;
            EventList toPlot = new EventList();
            for (Event currEvent : filteredEventList.getList()) {
                if (currEvent.getGeolocation() != null) {
                    toPlot.addEvent(currEvent);
                }
            }
            displayEventListLoc(toPlot);
        }
    }


    @Override
    public void onMapClick(LatLng point) {

        userLoc.setLatitude(point.latitude);
        userLoc.setLongitude(point.longitude);

        // Remove the user location Marker and add it in the new location clicked
        userMarker.remove();

        LatLng userLatLon = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
        userMarker = mMap.addMarker(new MarkerOptions().position(userLatLon)
                .title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    }


    /**
     * This is called during the User Permission Request. This function checks if the user gave the
     * app permission and gets the user's location.
     * If the services utility returns null (or no permission is given). The user's location is
     * assigned to University of Alberta as the default TODO: CHANGE THIS
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("DEBUG", "Getting the user's location");

                    // I have surpressed this error
                    // - Currently tells me that I haven't checked for permission when I clearly have
                    //noinspection MissingPermission
                    userLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    Log.d("DEBUG", "Got location");

                                    userLoc.setLatitude(53.5232);
                                    userLoc.setLongitude(-113.5263);

                                    if (location != null) {

                                        Log.d("DEBUG", "Default Location");
                                        // Default Position -> University of Alberta
                                        userLoc.setLatitude(location.getLatitude());
                                        userLoc.setLongitude(location.getLongitude());
                                    }
                                }
                            });
                } else {

                    // Permission Denied -> Set to default location
                    userLoc.setLatitude(53.5232);
                    userLoc.setLongitude(-113.5263);
                }
                return;
            }

            // we can add other permission codes here if need-be
        }
    }

    @Override
    public void onCameraIdle() { mTapTextView.setText(mMap.getCameraPosition().toString());}


    // [Navigation Bar]
    int getContentViewId() {
        return R.layout.activity_map;
    }


    // [Navigation Bar]
    int getNavigationMenuItemId() {
        return R.id.action_geomap;
    }
}