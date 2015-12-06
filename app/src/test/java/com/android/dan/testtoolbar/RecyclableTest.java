package com.android.dan.testtoolbar;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by justin on 12/2/15.
 */
public class RecyclableTest {
    /**
     * Recyclable class tests.
     * @throws Exception
     */
    @Test
    public void recyclable_Default_Constructor_Returns_Correct_Default_Values() throws Exception {
        Recyclable item = new Recyclable();

        assertEquals(item.getNumber(), 0);
        assertEquals(item.getItemName(), "");
        assertEquals(item.getDescription(), "");
        assertEquals(item.getAveragePrice(), 0.00);
        assertEquals(item.getMaterials().size(), 0);
    }

    @Test
    public void recyclable_Additional_Constructor_Sets_Values_Correctly() throws Exception {
        Recyclable item = new Recyclable(1, "item1", "desc1", 1.50);

        assertEquals(item.getNumber(), 1);
        assertEquals(item.getItemName(), "item1");
        assertEquals(item.getDescription(), "desc1");
        assertEquals(item.getAveragePrice(), 1.50);
        assertEquals(item.getMaterials().size(), 0);
    }

    @Test
    public void recyclable_Computes_If_Item_Is_Recyclable() throws Exception {
        Recyclable item = new Recyclable(1, "item1", "desc1", 1.50);
        item.setMaterials("Cardboard Box");                 // Add a recyclable component.

        assertEquals(item.getRecyclability(), false);
    }

    @Test
    public void recyclable_Computes_If_Item_Isnt_Recyclable() throws Exception {
        Recyclable item = new Recyclable(1, "item1", "desc1", 1.50);
        assertEquals(item.getRecyclability(), true);
    }



}
