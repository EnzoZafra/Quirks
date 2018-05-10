package cmput301f17t12.quirks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Geolocation;
import cmput301f17t12.quirks.Models.Quirk;

import static org.junit.Assert.*;

public class QuirkTest {

    // Test get ID
    @Test
    public void testGetID() {
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );

        quirk.setId("123123123");
        String actual = "123123123";
        assertEquals(quirk.getId(), actual);
    }

    //get and addevent
    @Test
    public void testGetEvent(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        Event event = new Event(quirk.getUser(), "comment", null, new Date(), new Geolocation(1,1), "Test");
        quirk.addEvent(event);

        assertEquals(quirk.getEvent(0), event);
    }

    //getEventList and remove event

    @Test
    public void testGetEventList(){

        EventList events = new EventList();

        Quirk quirk = new Quirk (events,"title", "type",
                "reason",new Date(), new ArrayList<Day>(), 2,3, "unittester");

        assertEquals(quirk.getEventList(), events);
    }
    @Test
    public void testRemoveEvent() {

        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 3, "unittester");

        Event event = new Event(quirk.getUser(), "comment", null, new Date(), new Geolocation(1, 1), "Test");
        quirk.addEvent(event);
        quirk.removeEvent(event);
        assertEquals(quirk.getEventList(), events);

    }

    //getTitle
    @Test
    public void testGetTitle(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        quirk.setTitle("testtitle");
        assertEquals(quirk.getTitle(), "testtitle");
    }
    //getType
    @Test
    public void testGetType(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        quirk.setType("testtype");
        assertEquals(quirk.getType(), "testtype");
    }
    //getReason
    @Test
    public void testGetReason(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        quirk.setReason("testreason");
        assertEquals(quirk.getReason(), "testreason");
    }
    //getDate
    @Test
    public void testGetDate(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        Date now = new Date();
        quirk.setDate(now);
        assertEquals(quirk.getDate(), now);
    }
    //getOccDate
    @Test
    public void testGetOccDate(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        ArrayList<Day> testOccDate = new ArrayList<Day>();
        testOccDate.add(Day.MONDAY);
        quirk.setOccDate(testOccDate);
        assertEquals(quirk.getOccDate(), testOccDate);
    }
    //getGoalValue
    @Test
    public void testGetGoal(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        quirk.setGoalValue(4);
        assertEquals(quirk.getGoalValue(),4);
    }
    //getCurrValue
    //inc and dec currValue
    @Test
    public void testGetCurrValue(){
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");

        quirk.incCurrValue();
        quirk.incCurrValue();
        quirk.incCurrValue();
        quirk.decCurrValue();

        assertEquals(quirk.getCurrValue(), 4);
    }
    //getUser
    @Test
    public void testGetUser(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );
        quirk.setUser("TestUser2");
        assertEquals(quirk.getUser(), "TestUser2");

    }

    //buildNewsHeader
    @Test
    public void testBuildNewsHeader(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );

        assertEquals(quirk.buildNewsHeader(), "unittester added a new Quirk!");
    }

    //buildNewsDescription
    @Test
    public void testBuildNewsDesription(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );

        assertEquals(quirk.buildNewsDescription(), "title");
    }
    //toString
    @Test
    public void testToString(){
        Quirk quirk = new Quirk ("title", "type",
                new Date(), new ArrayList<Day>(), 2, "unittester", "reason" );

        assertEquals(quirk.toString(), "title");
    }



}

