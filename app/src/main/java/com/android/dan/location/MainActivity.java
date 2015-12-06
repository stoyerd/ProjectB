package com.android.dan.location;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dan.location.objects.Zipcode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private Zipcode mZipCode;
    private TextView mainTextView;
    private Button addButton;
    private Button deleteButton;
    private Button searchButton;
//    private Button updateButton;
    private EditText zipcodeEditText;
    private ListView mainListView;
//    private ArrayAdapter mArrayAdapter;
    private ArrayList mCategoryList = new ArrayList();
    protected AlertDialog mAlertDialogCategories;


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
        // a button to show the pop up with a list view
        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v) {
                switch (v.getId()) {



                    case R.id.search_button:
                        String zipcode = zipcodeEditText.getText().toString();
                        mCategoryList.add(zipcode);
                        int result;
                        result = findZipcode(v);
                        //dstoyer TODO get the information for the zipcode and populate showPopUp.
                        if(result != -1) {
                            showPopUp("Recyclable Categories");
//                            Toast.makeText(getBaseContext(), "Zipcode "+zipcode+" found!", Toast.LENGTH_SHORT).show();
//                            updateButton.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getBaseContext(), "Zipcode "+zipcode+" not found!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        searchButton.setOnClickListener(handler);

//        updateButton = (Button) findViewById(R.id.update_button);
//        updateButton.setOnClickListener(this);
//        updateButton.setVisibility(View.INVISIBLE);

        // 3. Access the EditText defined in layout xml
        zipcodeEditText = (EditText) findViewById(R.id.zip_edittext);

//        // 4. Access the ListView
//        mainListView = (ListView) findViewById(R.id.listView);

        // 5. Create an array adapter for the ListView
//        mArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1,
//                mCategoryList
//        );
//        // 6. Set the ListView to use the ArrayAdapter
//        mainListView.setAdapter(mArrayAdapter);



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
        //mainTextView.setText(zipcodeEditText.getText().toString()
        //        + " is learning Android development!");

        // Also add that value to the list shown in the ListView
        if(v == addButton) {
            String zipcode = zipcodeEditText.getText().toString();
            mCategoryList.add(zipcode);
//            mArrayAdapter.notifyDataSetChanged();
            if (addZipcode(v)) {
                Toast.makeText(getBaseContext(), "Zipcode "+zipcode+" added.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getBaseContext(), "Cannot add zipcode "+zipcode+".", Toast.LENGTH_SHORT).show();
            }
        }
        if(v == deleteButton) {
            String zipcode = zipcodeEditText.getText().toString();
            mCategoryList.remove(zipcodeEditText.getText().toString());
            if(deleteZipcode(v)) {
                Toast.makeText(getBaseContext(), "Zipcode "+zipcode+" deleted.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getBaseContext(), "Cannot delete zipcode "+zipcode+".", Toast.LENGTH_SHORT).show();
            }
        }

//        if(v == updateButton) {
//            updateButton.setVisibility(View.INVISIBLE);
//            Log.d("Update:", "I have clicked the update button.");
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Log the item's position and contents
        // to the console in debug
        Log.d("ooh android", position + ": " + mCategoryList.get(position));

    }

    public boolean addZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        String zipcode = zipcodeEditText.getText().toString();
        if (isValidInteger(zipcode)) {
            return db.addDummyZipcode(Integer.parseInt(zipcode));
        }
        // if we made it this far, we could not add the zipcode.
        return false;
    }

    public boolean deleteZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        String zipcode = zipcodeEditText.getText().toString();
        if (isValidInteger(zipcode)) {
            return db.deleteDummyZipcode(Integer.parseInt(zipcode));
        }
        // if we made it this far, we could not remove the zipcode.
        return false;
    }
    //dloughlin TODO this will be a Zipcode object return type someotherday
    public int findZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        String zipcode = zipcodeEditText.getText().toString();
        if (isValidInteger(zipcode)) {
            return db.findZipcode(Integer.parseInt(zipcode));
        }
        // if we made it this far, we could not the find zipcode.
        return -1;
    }

    public void showPopUp(String title){

        // add your items, this can be done programatically
        // your items can be from a database

        List<String> categories = new ArrayList<>();
        categories.add("Plastic");
        categories.add("Paper");
        categories.add("Glass");
        categories.add("Electronics");


        // our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, categories);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnCategoryClickListener());

        // put the ListView in the pop up
        mAlertDialogCategories = new AlertDialog.Builder(MainActivity.this)
                .setView(listViewItems)
                .setTitle(title)
                .show();

    }

    /**
     * Checks the entire string ensuring that it is all digits.
     * @param zipcode
     * @return
     */
    boolean isValidInteger(String zipcode) {

        if (zipcode.length() < 1) {
            return false;
        }
        for (int i = 0; i < zipcode.length(); i++) {
            char c = zipcode.charAt(i);
            if(!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
