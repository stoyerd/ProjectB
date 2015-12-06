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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private Zipcode mZipCode;
    private TextView mainTextView;
    private Button addButton;
    private Button deleteButton;
    private Button searchButton;
//    private Button updateButton;
    private EditText zipcodeEditText;
    private EditText cityEditText;
    private EditText categoryEditText;
    private EditText product1;
    private EditText product2;
    private EditText product3;
    private ListView mainListView;
//    private ArrayAdapter mArrayAdapter;
//    private ArrayList mCategoryList = new ArrayList();
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
                        Zipcode result;
                        result = findZipcode(v);
                        //dstoyer TODO get the information for the zipcode and populate showPopUp.
                        if(result != null) {
                            Map<String, List<String>> standards = result.getStandards();

                            Set<String> catSet = standards.keySet();

                            List<String> catList = new ArrayList<>();

                            for(String cat : catSet) {
                                catList.add(cat);
                            }

                            showPopUp("Recyclable Categories", catList);
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

        cityEditText = (EditText) findViewById(R.id.city_edittext);

        categoryEditText = (EditText) findViewById(R.id.category_edit);

        product1 = (EditText) findViewById(R.id.product_edit1);
        product2 = (EditText) findViewById(R.id.product_edit2);
        product3 = (EditText) findViewById(R.id.product_edit3);


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
            if (addZipcode(v)) {
                Toast.makeText(getBaseContext(), "Zipcode "+zipcode+" added.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getBaseContext(), "Cannot add zipcode "+zipcode+".", Toast.LENGTH_SHORT).show();
            }
        }
        if(v == deleteButton) {
            String zipcode = zipcodeEditText.getText().toString();
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
    }

    public boolean addZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        String zipcode = zipcodeEditText.getText().toString();
        if (isValidInteger(zipcode)) {
            String city = cityEditText.getText().toString();
            if(city.isEmpty()) {
                city = null;
            }


            String category = ":"+categoryEditText.getText().toString();
            if(category.length() > 1) {
                category += ","+product1.getText().toString();
                category += ","+product2.getText().toString();
                category += ","+product3.getText().toString();
            }
            else {
                category = null;
            }


            return db.addDummyZipcode(Integer.parseInt(zipcode), city, category);
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
    public Zipcode findZipcode(View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);
        String zipcode = zipcodeEditText.getText().toString();
        if (isValidInteger(zipcode)) {
            return db.findZipcode(Integer.parseInt(zipcode));
        }
        // if we made it this far, we could not the find zipcode.
        return null;
    }

    public void showPopUp(String title, List<String> categories){

        // add your items, this can be done programatically
        // your items can be from a database


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
