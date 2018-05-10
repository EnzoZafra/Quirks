package cmput301f17t12.quirks;

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

public class UserTest {
    //Test GetID
    @Test
    public void testGetID(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),
                new QuirkList());
        user1.setId("123123123");
        String actual = "123123123";
        assertEquals(user1.getId(),actual);
    }
    //TestGetUsername
    @Test
    public void testGetUsername(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),
                new QuirkList());

        String actual = "username1";
        assertEquals(user1.getUsername(),actual);
    }

    //Test get Inventory
    @Test
    public void testGetInventory(){
        Inventory testInv = new Inventory();
        User user1 = new User("username1", testInv, new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),
                new QuirkList());


        assertEquals(user1.getInventory(),testInv);
    }
    //TestGetFriends
    @Test
    public void testGetFriendsList() {
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());

        user1.addFriend(user2.getUsername());

        ArrayList<String> list = new ArrayList<String>();
        list.add(user2.getUsername());

        assertEquals(user1.getFriends(), list);
    }
    //Test getUserRequests
    @Test
    public void testGetUserRequests() {
        ArrayList<UserRequest> testUserReq = new ArrayList<UserRequest>();
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), testUserReq, new ArrayList<TradeRequest>(),new QuirkList());


        assertEquals(user1.getUserRequests(), testUserReq);
    }
    //test GetTradeRequests
    @Test
    public void testGetTradeRequests() {
        ArrayList<TradeRequest> testTradeReq = new ArrayList<TradeRequest>();
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), testTradeReq,new QuirkList());


        assertEquals(user1.getUserRequests(), testTradeReq);
    }
    //has Requests Add/Delete Trade&User Requests
    @Test
    public void testHasAddTradeRequests() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        assertFalse(user1.hasRequests());
        TradeRequest testReq= new TradeRequest(user2.getUsername(), user2.getInventory().getList(), user1.getInventory().getList());
        user1.addTradeRequest(testReq);
        assertTrue(user1.hasRequests());
    }
    @Test
    public void testHasDeleteTradeRequests() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());

        TradeRequest testReq= new TradeRequest(user2.getUsername(), user2.getInventory().getList(), user1.getInventory().getList());
        user1.addTradeRequest(testReq);
        user1.deleteTradeRequest(testReq);
        assertFalse(user1.hasRequests());
    }
    @Test
    public void testHasAddUserRequest() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        assertFalse(user1.hasRequests());
        UserRequest testReq= new UserRequest(user2.getUsername());
        user1.addUserRequest(testReq);
        assertTrue(user1.hasRequests());
    }
    @Test
    public void testHasDeleteUserRequest() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());

        UserRequest testReq= new UserRequest(user2.getUsername());
        user1.addUserRequest(testReq);
        user1.deleteUserRequest(testReq);
        assertFalse(user1.hasRequests());
    }

    //Test hasFriend Add/Delete friend

    @Test
    public void testAddFriend() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());


        user1.addFriend(user2.getUsername());
        assertTrue(user1.hasFriend(user2.getUsername()));
    }
    @Test
    public void testDeleteFriend() {

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        User user2 = new User("username2", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());

        user1.addFriend(user2.getUsername());
        user1.deleteFriend(user2.getUsername());
        assertFalse(user1.hasFriend(user2.getUsername()));
    }
    @Test
    public void testGetQuirkList() {
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        QuirkList quirks = new QuirkList();
        quirks.addQuirk(quirk);

        User user1 = new User("username1", new Inventory(), new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),quirks);



        QuirkList returnedQuirkList = user1.getQuirks();
        assertTrue(returnedQuirkList.equals(quirks));
    }

    @Test
    public void testAddQuirkToUser() {
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );


        User user1 = new User("username1", new Inventory(), new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());


        assertFalse(user1.getQuirks().hasQuirk(quirk));
        user1.addQuirk(quirk);
        assertTrue(user1.getQuirks().hasQuirk(quirk));

    }

    @Test
    public void testDeleteQuirkFromUser() {
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );


        User user1 = new User("username1", new Inventory(), new ArrayList<String>(),
                new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());

        user1.addQuirk(quirk);
        assertTrue(user1.getQuirks().hasQuirk(quirk));
        user1.deleteQuirk(quirk);
        assertFalse(user1.getQuirks().hasQuirk(quirk));
    }


}

