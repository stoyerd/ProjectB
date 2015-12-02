package com.android.dan.testtoolbar.zipcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dan on 12/1/2015.
 */
public class Zipcode {

    private int _id;
    private String _city;
    private Map <String, List<String>> _standards;

    public Zipcode (int id, String city, Map<String, List<String>> standards) {

        this._id = id;
        this._city = city;
        this._standards = new HashMap<String, List<String>>();

    }

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String _city) {
        this._city = _city;
    }

    public Map<String, List<String>> getStandards() {
        return _standards;
    }

    public void setStandards(Map<String, List<String>> _standards) {
        this._standards = _standards;
    }

}
