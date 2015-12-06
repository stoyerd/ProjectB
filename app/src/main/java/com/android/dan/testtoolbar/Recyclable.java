package com.android.dan.testtoolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 12/2/15.
 * This class is for a recyclable object.
 */
public class Recyclable {
    private int number;
    private String itemName;
    private String description;
    private double averagePrice;
    private List<String> materials = new ArrayList<>();


    Recyclable() {
        setNumber(0);
        setItemName("");
        setDescription("");
        setAveragePrice(0.00);
//        materials = new ArrayList<String>();
    }

    Recyclable(int startNum, String startName, String startDesc, double startPrice) {
        setNumber(startNum);
        setItemName(startName);
        setDescription(startDesc);
        setAveragePrice(startPrice);
        materials = new ArrayList<String>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int val) {
        number = val;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        itemName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String text) {
        description = text;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double val) {
        averagePrice = val;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(String text) {
        materials.add(text);
    }

    public boolean getRecyclability () {
        return materials.isEmpty();
    }
}

