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

public class UserRequestTest {
    //GetDetails and getHeader
    @Test
    public void testGetHeader(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        UserRequest testUserReq = new UserRequest(user1.getUsername());
        String actual = "Friend request!";

        assertEquals(testUserReq.getHeader(),actual);
    }
    @Test
    public void testGetDetails(){
        User user1 = new User("username1", new Inventory(), new ArrayList<String>(), new ArrayList<UserRequest>(), new ArrayList<TradeRequest>(),new QuirkList());
        UserRequest testUserReq = new UserRequest(user1.getUsername());
        String actual = "Would you like to add username1?";

        assertEquals(testUserReq.getDetails(),actual);
    }



}

