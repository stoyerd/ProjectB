package com.android.dan.testtoolbar;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertNull;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;



/**
 * Created by justin on 12/2/15.
 */

public class ManualUpcSearchTest {

    public ManualUpcSearch mManualUpcSearch;
    public TextView textView;

    @Before
    public void createManualUpcSearch() {
        mManualUpcSearch = new ManualUpcSearch();
    }

    @Test
    public void onCreate_Initializes_Empty_Http_Request_Queue() throws Exception {
        assertNull(mManualUpcSearch.queue);

    }

    @UiThreadTest
    public void upcLookup_Adds_Request_Item_To_Request_Queue() throws Exception {
        mManualUpcSearch.upcLookup(null);
    }







}
