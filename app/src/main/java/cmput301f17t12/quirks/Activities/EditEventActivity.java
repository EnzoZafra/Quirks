package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.Geolocation;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

import static cmput301f17t12.quirks.R.id.event_map;

public class EditEventActivity extends AppCompatActivity implements OnMapReadyCallback, OnMapClickListener {

    private static final int SELECTED_PICTURE = 0;
    private User currentlylogged;
    Bitmap bitmap;
    private Event referenced_event;
    private Quirk referenced_quirk;

    private GoogleMap eventMap;
    private TextView editEventMapTextView;
    private Geolocation userLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());

        final EditText commentEdit = (EditText) findViewById(R.id.comment_edittext);

        Integer eventIndex = getIntent().getIntExtra("SELECTED_EVENT_INDEX", -1);
        Integer quirkIndex = getIntent().getIntExtra("SELECTED_QUIRK_INDEX", -1);

        if (eventIndex == -1 || quirkIndex == -1) {
            Log.i("Error", "Failed to read eventIndex or quirkIndex");
            finish();
        }

        ArrayList<Quirk> quirklist = currentlylogged.getQuirks().getList();

        referenced_quirk = quirklist.get(quirkIndex);
        referenced_event = referenced_quirk.getEvent(eventIndex);
        byte[] photoByte = referenced_event.getPhotoByte();

        Log.d("testing", Arrays.toString(referenced_event.getPhotoByte()));

        if (photoByte.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
            Log.d("testing", bitmap.toString());
            setImage(bitmap);
        }

        commentEdit.setText(referenced_event.getComment());
        Geolocation eventGeo = referenced_event.getGeolocation();
        if (eventGeo != null) {
            userLoc = new Geolocation(referenced_event.getGeolocation().getLatitude(),referenced_event.getGeolocation().getLongitude());
        }

        editEventMapTextView = (TextView) findViewById(R.id.event_tap_text);

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

    public void saveCommand(View view) {
        EditText commentText = (EditText) findViewById(R.id.comment_edittext);

        String comment = commentText.getText().toString();

        referenced_event.setComment(comment);

        byte[] photoByte = new byte[] {};
        if (bitmap != null) {
            photoByte = bitmapToByte(bitmap);
        }

        referenced_event.setPhotoByte(photoByte);
        referenced_event.setDate(new Date());
        referenced_event.setGeolocation(userLoc);

//        ElasticSearchUserController.UpdateUserTask updateUserTask
//                = new ElasticSearchUserController.UpdateUserTask();
//        updateUserTask.execute(currentlylogged);
        HelperFunctions.updateSingleUser(getApplicationContext(), currentlylogged);

        startActivity(new Intent(this, MainActivity.class));
    }

    public void cancelCommand(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void deleteCommand(View view) {
        referenced_quirk.removeEvent(referenced_event);
        referenced_quirk.decCurrValue();

//        ElasticSearchUserController.UpdateUserTask updateUserTask
//                = new ElasticSearchUserController.UpdateUserTask();
//        updateUserTask.execute(currentlylogged);
        HelperFunctions.updateSingleUser(getApplicationContext(), currentlylogged);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri photouri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photouri);
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

            if (errormsg.getVisibility() == View.VISIBLE) {
                errormsg.setVisibility(View.INVISIBLE);
            }
        }
    }

    public byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventMap = googleMap;

        eventMap.setOnMapClickListener(this);

        LatLng cameraLatLng = new LatLng(53.5232, -113.5263);

        if(userLoc != null) {
            LatLng userLatLon = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
            cameraLatLng = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(userLatLon)
                    .title(referenced_event.getComment()));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraLatLng));
    }

    @Override
    public void onMapClick(LatLng point) {
        editEventMapTextView.setText("tapped, point=" + point);

        eventMap.clear();
        eventMap.addMarker(new MarkerOptions().position(point));

        if(userLoc == null){
            userLoc = new Geolocation(point.latitude, point.longitude);
        }
        else {
            userLoc.setLatitude(point.latitude);
            userLoc.setLongitude(point.longitude);
        }
    }

}
