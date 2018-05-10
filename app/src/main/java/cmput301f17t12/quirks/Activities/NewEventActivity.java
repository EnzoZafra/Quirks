package cmput301f17t12.quirks.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Enumerations.DropType;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Geolocation;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

import static cmput301f17t12.quirks.R.id.event_map;
import static cmput301f17t12.quirks.R.id.map;

public class NewEventActivity extends BaseActivity
        implements OnMapReadyCallback, OnMapClickListener {

    Dialog myDialog;
    private static final int SELECTED_PICTURE = 0;
    private Bitmap bitmap;
    private User currentlylogged;

    private GoogleMap eventMap;
    private TextView newEventMapTextView;
    private Geolocation userLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);

        // Currently just set the user's initial location to UofA
        //userLoc.setLatitude(53.5232);
        //userLoc.setLongitude(-113.5263);
        userLoc = null;

        Spinner dropdown = (Spinner) findViewById(R.id.quirk_dropdown);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

//        currentlylogged = HelperFunctions.getUserObject(jestID);
        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());

        if (currentlylogged != null) {
            QuirkList quirks = currentlylogged.getQuirks();
            ArrayAdapter<Quirk> adapter = new ArrayAdapter<Quirk>(this, android.R.layout.simple_spinner_item, quirks.getList());
            dropdown.setAdapter(adapter);

            Button savebutton = (Button) findViewById(R.id.save_button);
            TextView errormsg = (TextView) findViewById(R.id.errormsg);

            if (quirks.getList().isEmpty()) {
                savebutton.setEnabled(false);
                errormsg.setVisibility(View.VISIBLE);
            }
            else {
                savebutton.setEnabled(true);
                errormsg.setVisibility(View.INVISIBLE);
            }
        }

        newEventMapTextView = (TextView) findViewById(R.id.event_tap_text);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(event_map);
        mapFragment.getMapAsync(this);
    }


    public void pickPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECTED_PICTURE);
    }


    public void removePhoto(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(0);
        bitmap = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventMap = googleMap;

        eventMap.setOnMapClickListener(this);

        // Currently the userLoc is always null at the beginning, just adding this in case we want to
        // grab the user position at the beginning
        if(userLoc != null) {
            LatLng userLatLon = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(userLatLon)
                    .title("Your Location"));
        }

        LatLng cameraLatLng = new LatLng(53.5232, -113.5263);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraLatLng));
    }


    @Override
    public void onMapClick(LatLng point) {;
        newEventMapTextView.setText("tapped, point=" + point);

        eventMap.clear();
        eventMap.addMarker(new MarkerOptions().position(point));

        if(userLoc == null){
            userLoc = new Geolocation(point.latitude, point.longitude);
        }

        userLoc.setLatitude(point.latitude);
        userLoc.setLongitude(point.longitude);
    }


    public void saveCommand(View view) {
        Spinner dropdown = (Spinner) findViewById(R.id.quirk_dropdown);
        EditText commentText = (EditText) findViewById(R.id.comment_edittext);

        Quirk selectedQuirk = (Quirk) dropdown.getSelectedItem();
        selectedQuirk.incCurrValue();
        String comment = commentText.getText().toString();

        byte[] photoByte = new byte[] {};
        if (bitmap != null) {
            photoByte = bitmapToByte(bitmap);
        }

        EventList events = selectedQuirk.getEventList();
        Event newEvent = new Event(currentlylogged.getUsername(), comment, photoByte, new Date(), userLoc, selectedQuirk.getType());
        events.addEvent(newEvent);

        Inventory inventory = currentlylogged.getInventory();
        DropType randomDrop = getDrop();
        inventory.addDrop(new Drop(randomDrop));

//        ElasticSearchUserController.UpdateUserTask updateUserTask
//                = new ElasticSearchUserController.UpdateUserTask();
//        updateUserTask.execute(currentlylogged);
        HelperFunctions.updateSingleUser(getApplicationContext(), currentlylogged);

        TextView txtclose, dropname;

        myDialog.setContentView(R.layout.drop_popup);
        dropname =(TextView) myDialog.findViewById(R.id.dropname);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);

        dropname.setText(randomDrop.getName());
        switch (randomDrop.getType()) {
            case "COMMON":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.commondrop));
                break;
            case "UNCOMMON":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.uncommondrop));
                break;
            case "RARE":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.raredrop));
                break;
            case "MYTHICAL":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.mythicaldrop));
                break;
            case "LEGENDARY":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.legendarydrop));
                break;
            case "ANCIENT":
                dropname.setTextColor(ContextCompat.getColor(this, R.color.ancientdrop));
                break;
        }

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }


    public void cancelCommand(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri photouri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photouri);
                bitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.5), (int)(bitmap.getHeight()*0.5), true);
                setImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setImage(Bitmap photo) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(photo);

        Button savebutton = (Button) findViewById(R.id.save_button);
        TextView errormsg = (TextView) findViewById(R.id.errormsg);
        if (bitmapToByte(photo).length >= 65536) {
            savebutton.setEnabled(false);
            errormsg.setVisibility(View.VISIBLE);
            errormsg.setText("Photo size is too big!");
        } else {
            savebutton.setEnabled(true);
            errormsg.setVisibility(View.INVISIBLE);
        }
    }


    public byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    private DropType getDrop() {
        HashMap<Double, LinkedList<DropType>> dropTypesMap = new HashMap<>();
        List<DropType> dropTypeList = Arrays.asList(DropType.values());
        double totalWeight = 0.0d;

        for (DropType droptype : dropTypeList) {
            if (!dropTypesMap.containsKey(droptype.getWeight())) {
                LinkedList<DropType> tmp = new LinkedList<>();
                tmp.add(droptype);
                dropTypesMap.put(droptype.getWeight(), tmp);
                totalWeight += droptype.getWeight();
            } else {
                LinkedList<DropType> tmp = dropTypesMap.get(droptype.getWeight());
                tmp.add(droptype);
            }
        }

        double randomWeight = -1;
        double random = Math.random() * totalWeight;
        Double[] weights = dropTypesMap.keySet().toArray(new Double[0]);
        for (int i = 0; i < weights.length; i++) {
            random -= weights[i];
            if (random <= 0.0d) {
                randomWeight = weights[i];
                break;
            }
        }

        LinkedList<DropType> drops = dropTypesMap.get(randomWeight);
        int randomIndex = (int) (Math.random() * drops.size() - 1);
        Log.d("droprandom", String.valueOf(randomWeight));
        Log.d("droprandom", String.valueOf(randomIndex));
        Log.d("droprandom", drops.get(randomIndex).getName());
        return drops.get(randomIndex);
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_new_event;
    }


    @Override
    int getNavigationMenuItemId() {
        return R.id.action_newevent;
    }

}
