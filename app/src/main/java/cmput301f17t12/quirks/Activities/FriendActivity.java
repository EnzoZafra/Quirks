package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cmput301f17t12.quirks.Adapters.FriendListItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.Request;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;
import cmput301f17t12.quirks.R;

/**
 * Created by root on 11/29/17.
 */

public class FriendActivity extends SocialActivity {
    public User currentlylogged;
    private FriendListItemAdapter adapter;
    private ArrayList<String> friendlist;
    private static final String TAG = "FriendActivity" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        Inventory dummyInv = new Inventory();
        ArrayList<String> friends = new ArrayList<>();
        QuirkList quirks = new QuirkList();
        ArrayList<TradeRequest> traderequests = new ArrayList<>();
        ArrayList<UserRequest> requests = new ArrayList<>();



//        currentlylogged = HelperFunctions.getUserObject(jestID);
        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: the size of currentylog is " + currentlylogged.getFriends().size());

        friendlist = currentlylogged.getFriends();
        adapter = new FriendListItemAdapter(friendlist,this);
        ListView lView = (ListView)findViewById(R.id.friendlistView);
        lView.setAdapter(adapter);


    }

    public void deleteFriend(int deletingFriendIndex) {

        User otherFriend = HelperFunctions.getSingleUser(friendlist.get(deletingFriendIndex));
        currentlylogged.deleteFriend(friendlist.get(deletingFriendIndex));


        otherFriend.deleteFriend(currentlylogged.getUsername());
        ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
        updateUserTask.execute(currentlylogged);

        ElasticSearchUserController.UpdateUserTask updateUserTask2 = new ElasticSearchUserController.UpdateUserTask();
        updateUserTask2.execute(otherFriend);
        adapter.notifyDataSetChanged();
    }



    @Override
    int getContentViewId() {
        return R.layout.activity_friends;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_friends;
    }
}
