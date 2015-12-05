package com.android.dan.testtoolbar.test;

import android.test.InstrumentationTestCase;

import com.android.dan.testtoolbar.zipcode.Zipcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

        assertTrue(10011 == zip.getID());
        assertTrue("New York" == zip.getCity());

        zip.getStandards().get("glass");
        assertTrue(glassItems == zip.getStandards().get("glass"))

    }
}
