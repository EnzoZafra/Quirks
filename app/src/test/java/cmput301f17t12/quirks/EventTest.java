package cmput301f17t12.quirks;

import android.text.format.DateUtils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.Geolocation;

public class EventTest {
    String user = "testuser123";
    private Geolocation geolocation = new Geolocation(1,1);

    //Test eventID
    @Test
    public void testgetID(){
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        event.setId("123123123");
        String actual = "123123123";
        assertEquals(event.getId(),actual);
    }
    // Test user
    @Test
    public void testGetUser() {

        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        assertEquals(event.getUser(), "testuser123");
    }

    @Test
    public void testSetUser() {

        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        event.setUser("zafra");
        assertEquals(event.getUser(), "zafra");
    }

    // Test comment
    @Test
    public void testGetComment() {

        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        assertEquals(event.getComment(), "ate lettuce");
    }

    @Test
    public void testSetComment() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        event.setComment("Some new comment");
        assertEquals(event.getComment(), "Some new comment");
    }

    // Test geolocation
    @Test
    public void testGetGeolocation() {
        Geolocation geolocation1 = new Geolocation(53.544389, -113.490927);
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation1, "Test");
        Geolocation returnedGeolocation = event.getGeolocation();
        assertEquals(geolocation1, returnedGeolocation);
    }

    @Test
    public void testSetGeolocation() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");

        Geolocation geolocation1 = new Geolocation(53.544389, -113.490927);
        event.setGeolocation(geolocation1);
        Geolocation returnedGeolocation = event.getGeolocation();
        assertEquals(geolocation1, returnedGeolocation);

        Geolocation geolocation2 = new Geolocation(23.544389, 109.490927);
        event.setGeolocation(geolocation2);
        returnedGeolocation = event.getGeolocation();
        assertEquals(geolocation2, returnedGeolocation);

        Geolocation geolocation3 = new Geolocation(-44.544389, 109.490927);
        event.setGeolocation(geolocation3);
        returnedGeolocation = event.getGeolocation();
        assertEquals(geolocation3, returnedGeolocation);

        Geolocation geolocation4 = new Geolocation(-23.544389, -109.490927);
        event.setGeolocation(geolocation4);
        returnedGeolocation = event.getGeolocation();
        assertEquals(geolocation4, returnedGeolocation);
    }

    @Test
    public void testDeleteGeolocation() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");

        Geolocation geolocation1 = new Geolocation(53.544389, -113.490927);
        event.setGeolocation(geolocation1);
        event.deleteGeolocation();
        Geolocation returnedGeolocation = event.getGeolocation();
        assertEquals(null, returnedGeolocation);
    }

    @Test
    public void testSetPhotoURI() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        byte[] someArray = {-1, -2, 40};
        event.setPhotoByte(someArray);
        assertEquals(event.getPhotoByte(), someArray);
    }

    //isEquals
    @Test
    public void testIsEquals(){
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        Event event1 = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        boolean actual = event.isEquals(event1);
        assertTrue(actual);
    }
    //Type
    @Test
    public void testGetType(){
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        event.setType("TestTest");
        String actual = "TestTest";
        assertEquals(event.getType(), actual);
    }
    //buildNewsHeader
    @Test
    public void testBuildNewsHeader(){
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        String actual = "testuser123 logged to Test!";
        assertEquals(event.buildNewsHeader(), actual);
    }
    @Test
    public void testGetDate() {
        Date date = new Date();
        Event event = new Event(user, "ate lettuce", null, date, geolocation, "Test");
        assertEquals(event.getDate(), date);
    }
    @Test
    public void testSetDate() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        Date date = new Date();
        event.setDate(date);
        assertEquals(event.getDate(), date);
    }


    @Test
    public void testBuildNewsDescription() {
        Event event = new Event(user, "ate lettuce", null, new Date(), geolocation, "Test");
        String actual = "ate lettuce";
        assertEquals(event.buildNewsDescription(), actual);
    }


}

