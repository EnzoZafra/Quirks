package cmput301f17t12.quirks.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import cmput301f17t12.quirks.Activities.EditEventActivity;
import cmput301f17t12.quirks.Activities.EventListActivity;
import cmput301f17t12.quirks.Activities.FeedActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Interfaces.Newsable;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.R;

public class FollowFeedItemAdapter extends BaseAdapter implements ListAdapter {
    Dialog myDialog;
    private ArrayList<Quirk> quirkList;
    private Context context;

    /**
     * Constructor for the FollowFeedItemAdapter. The Adapter is used for the follow feed ListView
     * @param quirkList QuirkList to shown in the ListView
     * @param context Current state of the application
     */
    public FollowFeedItemAdapter(ArrayList<Quirk> quirkList, Context context) {
        this.quirkList = quirkList;
        this.context = context;
    }

    /**
     * Return the size of the QuirkList
     * @return
     */
    @Override
    public int getCount(){ return quirkList.size(); }

    /**
     * Return a quirk at a specified index
     * @param i Index
     * @return Quirk object
     */
    @Override
    public Object getItem(int i){ return quirkList.get(i); }

    @Override //Not sure if this needs to be changed
    public long getItemId(int i){ return 0; }

    /**
     * Displays the data at specified indexes within the ListView
     * @param pos Position of the item selected
     * @param convertView The old view that can be reused
     * @param viewGroup The parent ListView
     * @return A View representing the data at the corresponding position
     */
    @Override
    public View getView(final int pos, View convertView, final ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.following_feeditem, null);
        }
        final Quirk quirkItem = quirkList.get(pos);

        TextView followQuirkTitle = (TextView) view.findViewById(R.id.followQuirkTitle);
        followQuirkTitle.setText(quirkItem.getFollowingString());

        // Initialize and update progress bar
        ProgressBar followBar = (ProgressBar) view.findViewById(R.id.followBar);
        followBar.setMax(quirkItem.getGoalValue());
        followBar.setProgress(quirkItem.getCurrValue());

        TextView fraction = (TextView) view.findViewById(R.id.fraction);
        fraction.setText(String.format("%s/%s", quirkItem.getCurrValue(), quirkItem.getGoalValue()));

        // Handle Image Button
        ImageButton eventbtn = (ImageButton) view.findViewById(R.id.eventbtn);
        eventbtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Event> eventList = quirkItem.getEventList().getList();
                Collections.sort(eventList, new Comparator<Newsable>() {
                    public int compare(Newsable m1, Newsable m2) {
                        return m2.getDate().compareTo(m1.getDate());
                    }
                });

                myDialog = new Dialog(v.getContext());
                myDialog.setContentView(R.layout.recent_event);

                TextView txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                TextView listItemHeader = (TextView) myDialog.findViewById(R.id.headerTxt);
                TextView listItemDescription = (TextView) myDialog.findViewById(R.id.captionTxt);
                TextView listItemTimeSpan = (TextView) myDialog.findViewById(R.id.timeTxt);
                ImageView imageView = (ImageView) myDialog.findViewById(R.id.imageView);
                TextView recent = (TextView) myDialog.findViewById(R.id.recent);

                if (eventList.size() > 0) {
                    Event mostrecent = eventList.get(0);

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                    listItemHeader.setText(mostrecent.buildNewsHeader());
                    listItemDescription.setText(mostrecent.buildNewsDescription());
                    listItemTimeSpan.setText(df.format(mostrecent.getDate()));
                    recent.setText(R.string.mostrecent);

                    byte[] photoByte = mostrecent.getPhotoByte();
                    if (photoByte.length != 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                        imageView.setImageBitmap(bitmap);
                    }
                } else {
                    listItemHeader.setText("");
                    listItemDescription.setText("");
                    listItemTimeSpan.setText("");
                    recent.setText(String.format("%s has not logged to this quirk", quirkItem.getUser()));
                }

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        return view;
    }
}
