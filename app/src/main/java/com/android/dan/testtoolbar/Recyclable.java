package com.android.dan.testtoolbar;

/**
 * Created by justin on 12/2/15.
 */
public class Recyclable {
    private int number;
    private String itemName;
    private String description;
    private float averagePrice;

    Recyclable() {

    }

    Recyclable(int startNum, String startName, String startDesc, float startPrice) {
        setNumber(startNum);
        setItemName(startName);
        setDescription(startDesc);
        setAveragePrice(startPrice);
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

    public float getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(float val) {
        averagePrice = val;
    }
}

