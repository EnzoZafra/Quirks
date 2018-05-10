package cmput301f17t12.quirks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Event;
import cmput301f17t12.quirks.Models.EventList;
import cmput301f17t12.quirks.Models.Geolocation;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;

import static org.junit.Assert.*;

public class QuirksListTest {

    // Test to getList from QuirkList
    @Test
    public void testAddQuirk() {
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");

        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);

        ArrayList<Quirk> quirks2 = new ArrayList<Quirk>();
        quirks2.add(quirk);

        assertEquals(quirks1.getList(), quirks2);
    }

    // Test if Quirk exists in QuirkList
    @Test
    public void testHasQuirk() {
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");
        Quirk quirk1 = new Quirk(events, "notin", "type",
                "test", new Date(), new ArrayList<Day>(), 2, 10, "unittester");

        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);

        assertTrue(quirks1.hasQuirk(quirk));
        assertFalse(quirks1.hasQuirk(quirk1));
    }
    //ClearAndAddQuirks
    @Test
    public void testClearAndAddQuirks() {
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");
        Quirk quirk1 = new Quirk(events, "notin", "type",
                "test", new Date(), new ArrayList<Day>(), 2, 10, "unittester");

        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);
        QuirkList quirks2 = new QuirkList();
        quirks2.addQuirk(quirk1);
        quirks1.clearAndAddQuirks(quirks2);

        assertEquals(quirks2.getList(), quirks1.getList());


    }

    // Test to return the Quirk at location from QuirkList
    @Test
    public void testGetQuirk(){
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");
        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);

        assertEquals(quirk, quirks1.getQuirk(0));

    }

    // Test removeQuirk
    @Test

    public void testRemoveQuirk(){
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");
        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);
        quirks1.removeQuirk(quirk);
        assertFalse(quirks1.hasQuirk(quirk));

    }

    //Size of QuirkList

    public void testSize(){
        EventList events = new EventList();

        Quirk quirk = new Quirk(events, "title", "type",
                "reason", new Date(), new ArrayList<Day>(), 2, 10, "unittester");
        QuirkList quirks1 = new QuirkList();

        quirks1.addQuirk(quirk);
        quirks1.removeQuirk(quirk);
        assertEquals(0,quirks1.size());

    }

}
