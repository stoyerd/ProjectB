package com.android.dan.location;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dan on 12/5/2015.
 * Here you can control what to do next when the user selects an item
 */
public class OnCategoryClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context = view.getContext();

        TextView textViewItem = ((TextView) view.findViewById(R.id.textViewItem));

        // get the clicked item name
        String listItemText = textViewItem.getText().toString();

        // We want to get the products of the category.
        // Then show them in a new pop up.
        String[] productArray = {"milk jugs","Coca-Cola 20oz", "Tupper-Ware"};
        List<String> products = new ArrayList<>(Arrays.asList(productArray));

        ((MainActivity) context).mAlertDialogCategories.cancel();

    }

}