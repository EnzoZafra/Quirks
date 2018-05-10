package cmput301f17t12.quirks;

/**
 * Created by charleshoang on 2017-12-02.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Enumerations.DropType;
import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.Request;
import cmput301f17t12.quirks.Models.TradeRequest;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.Models.UserRequest;

import static org.junit.Assert.*;

public class TradeRequestTest {

    //GetDetails and getHeader
    @Test
    public void testGetHeader(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        TradeRequest testTradeRequest = new TradeRequest(user1.getUsername(),
                user1.getInventory().getList(), user2.getInventory().getList());
        String actual = "Trade request from username1";

        assertEquals(testTradeRequest.getHeader(),actual);
    }

    //GetDetails
    @Test
    public void testGetDetails(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        TradeRequest testTradeRequest = new TradeRequest(user1.getUsername(),
                user1.getInventory().getList(), user2.getInventory().getList());
        String actual = null;

        assertEquals(testTradeRequest.getDetails(),actual);
    }
    //test getFromUserDrop()
    @Test
    public void testGetFromUserDrop() {
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        User user2 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        TradeRequest testTradeRequest = new TradeRequest(user1.getUsername(),
                user1.getInventory().getList(), user2.getInventory().getList());
        assertEquals(testTradeRequest.getFromUserDrop(), user1.getInventory().getList());
    }
    //test getMyDrop
    @Test
    public void testGetMyDrop() {
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        User user2 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(), new QuirkList());
        TradeRequest testTradeRequest = new TradeRequest(user1.getUsername(),
                user1.getInventory().getList(), user2.getInventory().getList());
        assertEquals(testTradeRequest.getMyDrop(), user2.getInventory().getList());
    }
}

