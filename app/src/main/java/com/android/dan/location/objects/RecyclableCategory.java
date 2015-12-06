package com.android.dan.location.objects;

import java.util.List;

/**
 * Created by Dan on 12/5/2015.
 */
public class RecyclableCategory {

    private String mName = null;
    private List<String> mProducts = null;

    public RecyclableCategory(String name, List<String> products) {
        this.mName = name;
        this.mProducts = products;
    }


}
