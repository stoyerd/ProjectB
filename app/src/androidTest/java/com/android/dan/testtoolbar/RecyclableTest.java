package com.android.dan.testtoolbar;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by justin on 12/2/15.
 */
public class RecyclableTest {

    @Test
    public void recyclable_Default_Constructor_Returns_Correct_Default_Values() {
        Recyclable item = new Recyclable();

        assertEquals(item.getNumber(), 0);
        assertEquals(item.getItemName(), "");
        assertEquals(item.getDescription(), "");
        assertEquals(item.getAveragePrice(), 0.00);
    }
}
