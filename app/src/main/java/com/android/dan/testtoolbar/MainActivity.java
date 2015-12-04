package com.android.dan.testtoolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private TextView mainTextView;
    private Button addButton;
    private Button deleteButton;
    private Button searchButton;
    private EditText mainEditText;
    private ListView mainListView;
    private ArrayAdapter mArrayAdapter;
    private ArrayList mCategoryList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Commit change

        // 1. Access the TextView defined in layout XML
        // and then set its text.
        mainTextView = (TextView) findViewById(R.id.main_textview);

        // 2. Access the Button defined in layout xml
        // and listen for it here.
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        // 3. Access the EditText defined in layout xml
        mainEditText = (EditText) findViewById(R.id.main_edittext);

        // 4. Access the ListView
        mainListView = (ListView) findViewById(R.id.listView);

        // Create an array adapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mCategoryList
        );
        // Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        // Take what was typed into EditText and use in TextView
        //mainTextView.setText(mainEditText.getText().toString()
        //        + " is learning Android development!");

        // Also add that value to the list shown in the ListView
        if(v == addButton) {
            mCategoryList.add(mainEditText.getText().toString());
            mArrayAdapter.notifyDataSetChanged();
            addZipcode(v);
        }
        if(v == deleteButton) {
            mCategoryList.remove(mainEditText.getText().toString());
            deleteZipcode(v);
        }

        if(v == searchButton) {
            mCategoryList.add(mainEditText.getText().toString());
            int result;
            result = findZipcode(v);
            if(result != -1) {
                mainTextView.setText("We found your zipcode!");
            } else {
                mainTextView.setText("We did not found your zipcode!");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Log the item's position and contents
        // to the console in debug
        Log.d("ooh android", position + ": " + mCategoryList.get(position));

    }

    public void addZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        db.addDummyZipcode(Integer.parseInt(mainEditText.getText().toString()));
    }

    public void deleteZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        db.deleteDummyZipcode(Integer.parseInt(mainEditText.getText().toString()));
    }
    //dloughlin TODO this will be a Zipcode object return type someotherday
    public int findZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        return db.findZipcode(Integer.parseInt(mainEditText.getText().toString()));
    }
}
