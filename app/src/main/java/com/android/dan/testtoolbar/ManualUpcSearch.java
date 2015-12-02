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

public class ManualUpcSearch extends AppCompatActivity {

    TextView textView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    RequestQueue queue;             // Where requests go to be made.
    StringRequest stringRequest;    // Actual request.
    EditText mUpcCode;                  // Upc code.


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

//        url = url + "/xml/7b0ef3fc28082d0c1a3a8d9cdce71730/4029764001807";   TODO: This url was to find 'club mate' soda, use in test case.

        // Create request url.
        String requestUrl = apiUrl + "/" + apiKey + "/" + code;

        Log.d("requestUrl", requestUrl);

        // Make request
        stringRequest = new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {                               // Request response event listener.
                InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));   // Create an input stream that the parser can understand.

                UpcDatabaseXmlParser parser = new UpcDatabaseXmlParser();                                   // Instantiate the parser

                try {
                    parser.parse(stream);

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {                                           // Error response event listener.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR!", "Error doing something");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);           // This actually sends the request.
    }


    /* Override onStop() */
    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            stringRequest.cancel();
        }
    }


}
