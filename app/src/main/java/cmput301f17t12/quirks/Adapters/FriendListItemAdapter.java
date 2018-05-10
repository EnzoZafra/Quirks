package cmput301f17t12.quirks.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Activities.FriendActivity;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

/**
 * Created by root on 11/30/17.
 */

public class FriendListItemAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> friends = new ArrayList<>();
    private Context context;
    private static final String TAG = "FriendListItemAdapter" ;

    public FriendListItemAdapter(ArrayList<String>friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @Override
    public int getCount() { return friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.friend_item, null);
        }
            String friend = friends.get(i);
            TextView friendHeader = (TextView) view.findViewById(R.id.Friendtext);
            friendHeader.setText(friend);


            ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delete_friend_but);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof FriendActivity){
                        ((FriendActivity)context).deleteFriend(i);

                    }
                }
            });


        return view;
    }
}
