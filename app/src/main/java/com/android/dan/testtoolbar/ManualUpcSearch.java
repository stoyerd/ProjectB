package com.android.dan.testtoolbar;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class ManualUpcSearch extends AppCompatActivity {

    TextView textView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    RequestQueue queue;             // Where requests go to be made.
    StringRequest stringRequest;    // Actual request.
    EditText mUpcCode;              // Upc code.
    private Recyclable item;                // Item to store response from UPC Database.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_upc_search);
        textView = (TextView) findViewById(R.id.action_manual_upc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);                       // Initialize an HTTP request queue.

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manual UPC Lookup home.
     *
     * Sample upc database API url: "http://api.upcdatabase.org/xml/7b0ef3fc28082d0c1a3a8d9cdce71730/4029764001807"
     */
    public void upcLookup(View view) {
        EditText mUpcCode = (EditText) findViewById(R.id.upc_code);

        String code = mUpcCode.getText().toString();                                // Get UPC code entered by user.
        String apiUrl = getString(R.string.upc_lookup_uri);                         // Get base API url.
        String apiKey = getString(R.string.upc_lookup_api_key);                     // Get API key.

        // Create request url.
        String requestUrl = apiUrl + "/" + apiKey + "/" + code;

//        url = url + "/xml/7b0ef3fc28082d0c1a3a8d9cdce71730/4029764001807";   TODO: This url was to find 'club mate' soda, use in test case.
        getRecyclable(requestUrl);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ManualUpcSearch Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.android.dan.testtoolbar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    /**
     * Get Recyclable Item.
     */
    private void getRecyclable(String url) {
        Log.d("requestUrl", url);

        // Make request to UPC DB API.
        stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {                               // Request response event listener.
                    InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));   // Create an input stream that the parser can understand.

                    UpcDatabaseXmlParser parser = new UpcDatabaseXmlParser();                                   // Instantiate the parser

                    try {
                        item = parser.parse(stream);
                        String description = (item.getDescription() == null) ? "Login failed!" : item.getDescription();
                        if(item != null) {
                            Log.d("Description", description);
                        }
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
//                        item = null;
                    } catch (IOException e) {
                        e.printStackTrace();
//                        item = null;
                    }

                }

            }, new Response.ErrorListener() {                                           // Error response event listener.
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR!", "Error doing something");
                }
            }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);           // This actually sends the request.
    }


    /* Override onStop() */
    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ManualUpcSearch Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.android.dan.testtoolbar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        if (queue != null) {
            stringRequest.cancel();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


}
