package cmput301f17t12.quirks.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Activities.FindFriendActivity;
import cmput301f17t12.quirks.Activities.RequestActivity;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

/**
 * Created by root on 11/30/17.
 */

public class FindFriendListItemAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<User> users = new ArrayList<>();
    private Context context;
    private static final String TAG = "FindFriendListItem";



    public FindFriendListItemAdapter(ArrayList<User> users, Context context){
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
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
            view = inflater.inflate(R.layout.findfriend_item, null);
        }
          final User user = users.get(i);
            TextView friendHeader = (TextView) view.findViewById(R.id.FindFriendtext);
            friendHeader.setText(user.getUsername());

            ImageButton addfriendbut = (ImageButton) view.findViewById(R.id.imageButtonAddFriend);

            addfriendbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof FindFriendActivity){
                        Log.d(TAG, "onClick: the position i clicked at is " + i);
                        ((FindFriendActivity)context).addFriend(i);
                    }
                }
            });

        return view;
    }
}
