package com.android.dan.location.test;

import android.test.InstrumentationTestCase;

import com.android.dan.location.objects.Zipcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dan on 12/5/2015.
 */
public class UnitTest extends InstrumentationTestCase{

    public void testZipcodeObjectCreate() throws Exception {
        // Create a zipcode, with city and items
        List<String> plasticItems = new ArrayList<>();
        plasticItems.add("plastic cups");
        plasticItems.add("plastic utensils");

        List<String> glassItems = new ArrayList<>();
        glassItems.add("beer bottles");
        glassItems.add("coke bottles");

        Map<String, List<String>> items = new HashMap<>();
        items.put("plastic", plasticItems);
        items.put("glass", glassItems);

        Zipcode zip = new Zipcode(10011, "New York", items);

        // Test positive results
        assertTrue(10011 == zip.getID());
        assertTrue("New York" == zip.getCity());
        assertTrue(zip.getStandards().get("glass").contains("beer bottles"));
        assertTrue(zip.getStandards().get("glass").contains("coke bottles"));
        assertTrue(zip.getStandards().get("plastic").contains("plastic cups"));
        assertTrue(zip.getStandards().get("plastic").contains("plastic utensils"));

        // Test negative results
        assertFalse(10022 == zip.getID());
        assertFalse("New Dork" == zip.getCity());
        assertFalse(zip.getStandards().containsKey("paper"));
        assertFalse(zip.getStandards().get("glass").contains("water bottles"));
        assertFalse(zip.getStandards().get("plastic").contains("glass bottles"));


    }
}
