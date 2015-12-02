package com.android.dan.testtoolbar;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by justin on 12/2/15.
 * Class to handle parsing XML response from UPC Database.
 */

public class UpcDatabaseXmlParser {
    private static final String ns = null;              // TODO: Figure out why namespace must be null!

    /* This method sets up the XML parsing tool. */
    public Recyclable parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readResponse(parser);
        } finally {
            in.close();
        }
    }

    private Recyclable readResponse(XmlPullParser parser) throws XmlPullParserException, IOException {
        Recyclable item = new Recyclable();                         // Create a blank recyclable item to hold parsed xml data.

        parser.require(XmlPullParser.START_TAG, ns, "output");

        String name = parser.getName();                         // Get the name of the tag, which should be "output".

        if (name.equals("output")) {                            // Only start parsing data at "output" tag.
            item = readItem(parser);
        }

        return item;
    }

    /**
     * This method returns a new local instance of a Recyclable item.
     * @param parser
     * @return
     */
    private Recyclable readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "output");

        // Initialize local variables to be set and used when constructing the returned recyclable item.
        int number = 0;
        String itemName = null;
        String description = null;
        float averagePrice = 0;

        while (parser.next() != XmlPullParser.END_TAG) {        // Loop through all tags in the response.
            String tag = parser.getName();
            if(tag == null) {                                   // Catch null variables.
                continue;
            }

            // Set each local variable to the values contained in the XML response.
            if (tag.equals("number")) {
                number = Integer.parseInt(parser.getText());
            } else if (tag.equals("itemname")) {
                itemName = parser.getText();
            } else if (tag.equals("description")) {
                description = parser.getText();
            } else if (tag.equals("avgprice")) {
                averagePrice = Float.parseFloat(parser.getText());
            }
        }

        return new Recyclable(number, itemName, description, averagePrice);
    }
}
