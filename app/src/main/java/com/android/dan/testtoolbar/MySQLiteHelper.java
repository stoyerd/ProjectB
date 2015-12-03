package com.android.dan.testtoolbar;
// Ref: http://www.vogella.com/tutorials/AndroidSQLite/article.html#databasetutorial_database
// Used the above as a guide for working with SQLite on Android

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.dan.testtoolbar.zipcode.Zipcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_STANDARDS = "all_standards";
    public static final String ZIP_CODE = "_id";
    public static final String RECYCLING_STANDARDS = "recycling_standards";
    public static final String CITY = "city";

    private static final String DATABASE_NAME = "recycle.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_STANDARDS + "(" + ZIP_CODE
            + " integer primary key, " + CITY
            + " text not null default 0, " + RECYCLING_STANDARDS
            + " text not null default 0);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_STANDARDS);
        onCreate(database);
    }

    // We are querying the db using a zipcode and returning
    // the city and recycling standards.
    public Zipcode queryZipCode(String zipcodeID) {

        String query = "Select * FROM " + TABLE_STANDARDS + " WHERE " + ZIP_CODE + " = \"" + zipcodeID + "\"";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        try {
            // Do we have 1 row
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                // dstoyer TODO We need to parse the standards into the item type and the list of products.
                // the object needs to be a Map<String, List<String>
                Map<String, List<String>> standards = new HashMap<>();
                cursor.getString(2);
                Zipcode zipcode = new Zipcode(
                        Integer.parseInt(zipcodeID),
                        cursor.getString(1),
                        standards
                        );
                cursor.close();
                return zipcode;
            } else {
                //dloughlin(TODO): if not foundm lets display something different to the user
                return null;
            }
        }
        finally {
            database.close();
        }
    }

    // For seeding a database and testing
    public void addDummyZipcode(int zipcodeID) {
        ContentValues values = new ContentValues();
        values.put(ZIP_CODE, zipcodeID);
        values.put(CITY, "denver");
        values.put(RECYCLING_STANDARDS, "toxic waste");

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STANDARDS, null, values);
        db.close();
    }

    // dstoyer TODO make method for parsing the standards. The object needs to be a Map<String, List<String>

}

// List<String> sp
// sp.add(product);

// save key name in variable
// build list
// put in the map.

// map.put(key, sp)