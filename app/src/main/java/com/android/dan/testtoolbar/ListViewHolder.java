package com.android.dan.testtoolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Dan on 12/4/2015.
 */
public class ListViewHolder extends ArrayAdapter {

    Context mContext;

    public ListViewHolder(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        ListViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ListViewHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    }
}
