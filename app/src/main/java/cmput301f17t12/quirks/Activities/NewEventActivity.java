package cmput301f17t12.quirks.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class NewEventActivity extends BaseActivity {

    private static final int SELECTED_PICTURE = 0;
    private String photoPath = "";
    private User currentlylogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spinner dropdown = (Spinner)findViewById(R.id.quirk_dropdown);

        // Testing - replace this with a userID search later on.
        String jestID = "AV-xx8ahi8-My2t7XP4j";

        currentlylogged = HelperFunctions.getUserObject(jestID);
        if (currentlylogged != null) {
            QuirkList quirks = currentlylogged.getQuirks();
            ArrayAdapter<Quirk> adapter = new ArrayAdapter<Quirk> (this, android.R.layout.simple_spinner_item, quirks.getList());
            dropdown.setAdapter(adapter);
        }
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
        photoPath = "";
    }

    public void saveCommand(View view) {
        Spinner dropdown = (Spinner) findViewById(R.id.quirk_dropdown);
        EditText commentText = (EditText) findViewById(R.id.comment_edittext);

        Quirk selectedQuirk = (Quirk) dropdown.getSelectedItem();
        String comment = commentText.getText().toString();

        Log.d("testing", selectedQuirk.toString());
        Log.d("testing", comment);
        Log.d("testing", photoPath);

        EventList events = selectedQuirk.getEventList();
        Event newEvent = new Event(currentlylogged.getUsername(), comment, photoPath, new Date());
        events.addEvent(newEvent);

        ElasticSearchUserController.UpdateUserTask updateUserTask
                = new ElasticSearchUserController.UpdateUserTask();
        updateUserTask.execute(currentlylogged);

        startActivity(new Intent(this, MainActivity.class));
    }

    public void cancelCommand(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            setImage(uri);
        }
    }

    private void setImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);

            photoPath = getRealPathFromURI(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
