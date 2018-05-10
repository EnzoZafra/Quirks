package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301f17t12.quirks.Adapters.FindFriendListItemAdapter;
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
 * Created by root on 11/30/17.
 */

public class FindFriendActivity extends SocialActivity {
    public User currentlylogged;
    private FindFriendListItemAdapter adapter;
    private ArrayList<String> userList;
    private ArrayList<String> friendslist;
    ArrayList<User> allusers;
    private StringBuilder query;
    private static final String TAG = "FindFriendActivity" ;

    EditText searchFriendUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userList = new ArrayList<>();
        searchFriendUser = (EditText) findViewById(R.id.editTextFindFriend);
        ImageButton searchFriendButton = (ImageButton) findViewById(R.id.imageButtonSearch);

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
        friendslist = currentlylogged.getFriends();

        if (friendslist == null) {
            friendslist = new ArrayList<>();
        }

        if (currentlylogged != null) {
            query = new StringBuilder("{" +
                    "  \"from\": 0, \"size\": 5000, " +
                    "  \"query\": {" +
                    "    \"bool\": {" +
                    "      \"must_not\":  {" +
                    "        \"terms\": {" +
                    "          \"username\": [");
            query.append("\"").append(currentlylogged.getUsername()).append("\"");
            if (friendslist.size() > 0) query.append(", ");

            for (int i = 0; i < friendslist.size(); i++) {
                query.append("\"").append(friendslist.get(i)).append("\"");
                if (i != friendslist.size() - 1) {
                    query.append(", ");
                }
            }

            query.append("           ]" + "        }" + "      }" + "    }" + "  }" + "}");
        }

        allusers = HelperFunctions.getAllUsers(query.toString());
        if (allusers != null){
            ListView lView = (ListView) findViewById(R.id.findfriend_listview);
            adapter = new FindFriendListItemAdapter(allusers, this);
            lView.setAdapter(adapter);
        }
        else{
            String text = "Social activities are disabled when offline";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
//        allusers = HelperFunctions.getUsersGeneral(getApplicationContext());


    }


    public void findFriendSearchBut(View view) {
        String stringSearchUser = searchFriendUser.getText().toString();

        if(stringSearchUser.equals(currentlylogged.getUsername())){
            Toast.makeText(FindFriendActivity.this,"Cannot input yourself",Toast.LENGTH_SHORT).show();
            return;
        }
        if (stringSearchUser.equals("")) {
            Toast.makeText(FindFriendActivity.this,"Please input a username",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( friendslist.contains(stringSearchUser)) {
            Toast.makeText(FindFriendActivity.this, "You're already friends with them", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(FindFriendActivity.this, "Searching for user", Toast.LENGTH_SHORT).show();
            String query = "{" +
                    "  \"query\": {" +
                    "    \"match\": {" +
                    "      \"username\": \"" + stringSearchUser + "\"" +
                    "    }" +
                    "  }" +
                    "}";

            ElasticSearchUserController.GetUsersTask getUsersTask = new ElasticSearchUserController.GetUsersTask();
            getUsersTask.execute(query);
            ArrayList<User> UserfromQueries = new ArrayList<>();
            UserfromQueries.clear();
            Log.d(TAG, "findFriendSearchBut: the userfromqueries is " + UserfromQueries.size());
            try {
                UserfromQueries = getUsersTask.get();
            } catch (InterruptedException e) {
                Toast.makeText(FindFriendActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ExecutionException e) {
                Toast.makeText(FindFriendActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            allusers.clear();
            allusers.addAll(UserfromQueries);
            if (allusers.size() == 0) {
                Toast.makeText(FindFriendActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void addFriend(int selectFriendIndex) {
        Toast.makeText(FindFriendActivity.this,"Sending Friend Request",Toast.LENGTH_SHORT).show();
        String username = allusers.get(selectFriendIndex).getUsername();
        Log.d(TAG, "addFriend: the index is this " + username);
        UserRequest user = new UserRequest(currentlylogged.getUsername());
        allusers.get(selectFriendIndex).addUserRequest(user);
        ElasticSearchUserController.UpdateUserTask updateUserTask
                = new ElasticSearchUserController.UpdateUserTask();
        updateUserTask.execute(allusers.get(selectFriendIndex));
        HelperFunctions.clearFile(getApplicationContext(), "allUsers.txt");
        HelperFunctions.saveInFile(allusers, getApplicationContext(), "allusers.txt");


        Toast.makeText(FindFriendActivity.this,"Sent Friend Request",Toast.LENGTH_SHORT).show();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_findfriends;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_findFriends;
    }
}
