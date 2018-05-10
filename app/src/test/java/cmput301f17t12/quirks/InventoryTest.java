package cmput301f17t12.quirks;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import cmput301f17t12.quirks.Enumerations.DropType;
import cmput301f17t12.quirks.Models.Drop;
import cmput301f17t12.quirks.Models.Inventory;

import static org.junit.Assert.*;

public class InventoryTest {

    // Test to getInventory
    @Test
    public void testGetList() {
        Drop drop = new Drop(DropType.larrybird);
        Drop drop2 = new Drop(DropType.jeremylin);

        Inventory myInventory = new Inventory();
        myInventory.addDrop(drop);
        myInventory.addDrop(drop2);

        ArrayList<Drop> dropList = new ArrayList<Drop>();
        dropList.add(drop);
        dropList.add(drop2);

        assertEquals(myInventory.getList(), dropList);
    }

    // Test adding Drop to inventory
    @Test
    public void testAddDrop() {
        Drop drop = new Drop(DropType.larrybird);

        Inventory myInventory = new Inventory();

        assertFalse(myInventory.hasDrop(drop));
        myInventory.addDrop(drop);
        assertTrue(myInventory.hasDrop(drop));
    }

    // Test if Drop exists in Inventory
    @Test
    public void testHasDrop() {
        Drop drop = new Drop(DropType.larrybird);
        Inventory myInventory = new Inventory();
        myInventory.addDrop(drop);

        assertTrue(myInventory.hasDrop(drop));
    }

    // Test to return the Drop at location from Inventory
    @Test
    public void testGetDrop() {
        Drop drop = new Drop(DropType.larrybird);
        Inventory myInventory = new Inventory();
        myInventory.addDrop(drop);

        assertEquals(myInventory.getDrop(0), drop);
    }

    // Test to remove Drop from Inventory
    @Test
    public void testRemoveDrop() {
        Drop drop = new Drop(DropType.larrybird);
        Inventory myInventory = new Inventory();
        myInventory.addDrop(drop);
        assertTrue(myInventory.hasDrop(drop));
        myInventory.removeDrop(drop);
        assertFalse(myInventory.hasDrop(drop));
    }

}

