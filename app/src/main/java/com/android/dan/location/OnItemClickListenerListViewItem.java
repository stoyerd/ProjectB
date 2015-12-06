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
public class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {

    Context mContext = null;
    ListView mListView = null;
    AlertDialog mAlertDialogCategories = null;

    public OnItemClickListenerListViewItem (Context context, AlertDialog alertDialog, ListView listView) {
        mContext = context;
        mListView = listView;
        mAlertDialogCategories = alertDialog;
    }

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

        showPopUp("Category: "+listItemText, products);

        ((MainActivity) context).mAlertDialogCategories.cancel();

    }

    public void showPopUp(String title, List<String> products){

        // add your items, this can be done programatically
        // your items can be from a database

        // our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(mContext, R.layout.list_view_row_item, products);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(mContext);
        listViewItems.setAdapter(adapter);
        // put the ListView in the pop up
        mAlertDialogCategories = new AlertDialog.Builder(mContext)
                .setView(listViewItems)
                .setTitle(title)
                .show();

    }

}
