package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cmput301f17t12.quirks.Adapters.CollectibleItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class TradeActivity extends SocialActivity {

    private User currentlylogged;
    private User tradedUser;
    private CollectibleItemAdapter curruserAdapter;
    private CollectibleItemAdapter tradeduserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Spinner dropdown = (Spinner) findViewById(R.id.collectionspinner);

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        currentlylogged = HelperFunctions.getUserObject(jestID);

        if (currentlylogged != null) {
            String query = "{" +
                    "  \"from\": 0, \"size\": 5000, " +
                    "  \"query\": {" +
                    "    \"bool\": {" +
                    "      \"must_not\": {" +
                    "        \"term\": { \"username\" : \"" + currentlylogged.getUsername() + "\"}" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";

            ArrayList<User> allusers = HelperFunctions.getAllUsers(query);

            if (allusers != null) {
                if (allusers.size() > 0) {


                    ArrayAdapter<User> useradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allusers);
                    dropdown.setAdapter(useradapter);

                    final TextView myUsername = (TextView) findViewById(R.id.usernametext);
                    final TextView theirUsername = (TextView) findViewById(R.id.theirusername);
                    Button tradebutton = (Button) findViewById(R.id.tradebtn);

                    tradedUser = (User) dropdown.getSelectedItem();

                    myUsername.setText(currentlylogged.getUsername());
                    theirUsername.setText(tradedUser.getUsername());

                    // instantiate custom adapter for both inventories
                    final ArrayList<Drop> currInventory = currentlylogged.getInventory().getList();
                    curruserAdapter = new CollectibleItemAdapter(currInventory, this);

                    final ArrayList<Drop> theirInventory = tradedUser.getInventory().getList();
                    tradeduserAdapter = new CollectibleItemAdapter(theirInventory, this);

                    // handle listview and assign adapter
                    ListView yourCollection = (ListView) findViewById(R.id.yourcollection);
                    ListView theircollection = (ListView) findViewById(R.id.theircollection);
                    yourCollection.setAdapter(curruserAdapter);
                    theircollection.setAdapter(tradeduserAdapter);

                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            tradedUser = (User) parentView.getItemAtPosition(position);
                            ArrayList<Drop> tmp = new ArrayList<>();
                            tmp.addAll(tradedUser.getInventory().getList());
                            theirInventory.clear();
                            theirInventory.addAll(tmp);
                            theirUsername.setText(tradedUser.getUsername());
                            tradeduserAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            theirInventory.clear();
                            theirUsername.setText("");
                            tradeduserAdapter.notifyDataSetChanged();
                        }
                    });

                    // Set listener for the trade button
                    tradebutton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ArrayList<Drop> curr_receive = getSelectedDrops(theirInventory);
                            ArrayList<Drop> curr_give = getSelectedDrops(currInventory);
                            TradeRequest trade = new TradeRequest(currentlylogged.getUsername(), curr_give, curr_receive);
                            tradedUser.addTradeRequest(trade);

                            ElasticSearchUserController.UpdateUserTask updateUserTask
                                    = new ElasticSearchUserController.UpdateUserTask();
                            updateUserTask.execute(tradedUser);

                            String text = "Trade request sent!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(v.getContext(), text, duration);
                            toast.show();

                            tradeduserAdapter.notifyDataSetChanged();
                            curruserAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else{
                    String text = "No friends to trade with";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }

            }
            else{
                String text = "Social activities are disabled when offline";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        }
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_trade;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_trade;
    }

    /**
     * Clear the isSelected boolean value of each Drop in the list and clears it
     * @param list ArrayList<Drop> list of drops
     */
    ArrayList<Drop> getSelectedDrops(ArrayList<Drop> list) {
        ArrayList<Drop> tmp = new ArrayList<>();
        for (Drop item : list) {
            if (item.isSelected()) {
                tmp.add(item);
                item.setSelected(false);
            }
        }
        return tmp;
    }
}
