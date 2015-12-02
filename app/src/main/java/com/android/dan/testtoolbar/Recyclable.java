package com.android.dan.testtoolbar;

/**
 * Created by justin on 12/2/15.
 */
public class Recyclable {
    private int number;
    private String itemName;
    private String description;
    private double averagePrice;

    Recyclable() {
        setNumber(0);
        setItemName("");
        setDescription("");
        setAveragePrice(0.00);
    }

    Recyclable(int startNum, String startName, String startDesc, double startPrice) {
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

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double val) {
        averagePrice = val;
    }
}

