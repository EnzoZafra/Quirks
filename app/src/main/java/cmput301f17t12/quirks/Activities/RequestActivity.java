package cmput301f17t12.quirks.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301f17t12.quirks.Adapters.RequestListItemAdapter;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Drop;

import cmput301f17t12.quirks.Models.Request;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;
import cmput301f17t12.quirks.R;

public class RequestActivity extends  SocialActivity{

    public User currentlylogged;
    private RequestListItemAdapter adapter;

    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<UserRequest> userRequests;
    private ArrayList<Request> requests;


    private SparseArray<ArrayList<Drop>> giveMap;
    private SparseArray<ArrayList<Drop>> receiveMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requests = new ArrayList<>();

        SharedPreferences settings = getSharedPreferences("dbSettings", Context.MODE_PRIVATE);
        String jestID = settings.getString("jestID", "defaultvalue");

        if (jestID.equals("defaultvalue")) {
            Log.i("Error", "Did not find correct jestID");
        }

        //currentlylogged = HelperFunctions.getUserObject(jestID);
        currentlylogged = HelperFunctions.getSingleUserGeneral(getApplicationContext());
        requests = new ArrayList<>();

        userRequests = currentlylogged.getUserRequests();
        tradeRequests = currentlylogged.getTradeRequests();

        updateRequests();

        giveMap = new SparseArray<>();
        receiveMap = new SparseArray<>();
        buildMaps(currentlylogged.getTradeRequests());

        ListView lView = (ListView)findViewById(R.id.listviewRequest);

        adapter = new RequestListItemAdapter(requests,this);
        lView.setAdapter(adapter);
    }

    void updateRequests() {
        requests.clear();

        // Must add trade requests first or getGiveInfo & getReceiveInfo breaks.
        // Indexing of the adapter relies on the order of addition
        requests.addAll(tradeRequests);
        requests.addAll(userRequests);
    }

    void buildMaps(ArrayList<TradeRequest> tradeRequests) {
        for (TradeRequest item : tradeRequests) {
            giveMap.put(tradeRequests.indexOf(item), item.getMyDrop());
            receiveMap.put(tradeRequests.indexOf(item), item.getFromUserDrop());
        }
    }

    public ArrayList<Drop> getGiveInfo(int index) {
        return giveMap.get(index);
    }

    public ArrayList<Drop> getReceiveInfo(int index) {
        return receiveMap.get(index);
    }

    public void acceptRequest(int pos){
        Request request = requests.get(pos);
        // Trade request
        if (request.getDetails() == null) {
            TradeRequest trade = tradeRequests.get(pos);
            acceptTrade(trade);
        } else {
            int friendindex = pos - tradeRequests.size();
            UserRequest friend = userRequests.get(friendindex);
            acceptFriend(friend);
        }
        String text = "Request accepted!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    public void declineRequest(int pos){
        Request request = requests.get(pos);
        // Trade request
        if (request.getDetails() == null) {
            TradeRequest trade = tradeRequests.get(pos);
            declineTrade(trade);
        } else {
            int friendindex = pos - tradeRequests.size();
            UserRequest friend = userRequests.get(friendindex);
            declineFriend(friend);
        }
        String text = "Request declined";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    public void acceptTrade(TradeRequest trade) {
        String toasttext = "";
        int duration = Toast.LENGTH_LONG;

        User toTradeWith = HelperFunctions.getSingleUser(trade.getFromUser());
        ArrayList<Drop> give = trade.getMyDrop();
        ArrayList<Drop> receive = trade.getFromUserDrop();

        int hasDrops = checkHasDrops(toTradeWith, give, receive);
        // Proceed with trade
        if (hasDrops == 0) {
            for (Drop item : give) {
                currentlylogged.trade(item, toTradeWith);
            }

            for (Drop item: receive) {
                toTradeWith.trade(item, currentlylogged);
            }
            ElasticSearchUserController.UpdateUserTask updateUserTask2 = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask2.execute(toTradeWith);
        }
        // if 1, currentUser has traded a requested drop away already.
        else if (hasDrops == 1) {
            toasttext = "You have traded away one of the requested items already";
        }
        else if (hasDrops == 2) {
            toasttext = toTradeWith.getUsername() + " have traded away one of the requested items already";
        }

        currentlylogged.deleteTradeRequest(trade);
        updateObjects();
        Toast toast = Toast.makeText(this, toasttext, duration);
        toast.show();
    }

    public void declineTrade(TradeRequest trade) {
        currentlylogged.deleteTradeRequest(trade);
        updateObjects();
    }

    public void acceptFriend(UserRequest friend){
        String toasttext = "";
        int duration = Toast.LENGTH_LONG;

        if (currentlylogged.hasFriend(friend.getFromUser())) {
            toasttext = "Friend is already in your friends list";
        } else {
            User toAdd = HelperFunctions.getSingleUser(friend.getFromUser());
            currentlylogged.addFriend(toAdd.getUsername());
            toAdd.addFriend(currentlylogged.getUsername());
            toasttext = "Friend accepted";
            ElasticSearchUserController.UpdateUserTask updateUserTask2 = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask2.execute(toAdd);
        }

        currentlylogged.deleteUserRequest(friend);
        updateObjects();
        Toast toast = Toast.makeText(this, toasttext, duration);
        toast.show();
    }

    public void declineFriend(UserRequest friend){
        currentlylogged.deleteUserRequest(friend);
        updateObjects();
    }

    public void updateObjects() {
        updateRequests();
        adapter.notifyDataSetChanged();
        ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
        updateUserTask.execute(currentlylogged);
    }

    public int checkHasDrops (User toTradeWith, ArrayList<Drop> give, ArrayList<Drop> receive) {
        for (Drop item : give) {
            if (!currentlylogged.getInventory().hasDrop(item)) {
                return 1;
            }
        }

        for (Drop item: receive) {
            if (!toTradeWith.getInventory().hasDrop(item)) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_requests;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_request;
    }
}
