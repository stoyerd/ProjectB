package com.android.dan.testtoolbar.test;

import android.test.InstrumentationTestCase;

/**
 * Created by Dan on 12/5/2015.
 */
public class UnitTest extends InstrumentationTestCase{

    public void test() throws Exception {

        assertFalse(4 == 5+2);
        assertTrue(4 == 2+2);

    }
}
