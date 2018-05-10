package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cmput301f17t12.quirks.Adapters.FollowFeedItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Interfaces.Newsable;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class FeedActivity extends SocialActivity {

    private String jestID;
    private FollowFeedItemAdapter adapter;
    private ArrayList<Quirk> quirkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        jestID = settings.getString("jestID", "defaultvalue");
        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        final User currentlylogged = HelperFunctions.getUserObject(jestID);
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
                    quirkList.addAll(user.getQuirks().getList());
                }
            }

            // -1 to reverse the order
            Collections.sort(quirkList, new Comparator<Quirk>() {
                public int compare(Quirk m1, Quirk m2) {
                    return -1 * m2.getFollowingString().compareTo(m1.getFollowingString());
                }
            });

            // create instance of custom adapter
            adapter = new FollowFeedItemAdapter(quirkList, this);

            // create listView and assign custom adapter
            ListView lView = (ListView) findViewById(R.id.listViewFeed);
            lView.setAdapter(adapter);
        }
        else{
            String text = "Social activities are disabled when offline";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_feed;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_feed;
    }
}
