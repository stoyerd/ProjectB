package com.android.dan.location;
// Ref: http://www.vogella.com/tutorials/AndroidSQLite/article.html#databasetutorial_database
// Used the above as a guide for working with SQLite on Android

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.dan.location.objects.Zipcode;

import java.util.ArrayList;
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

    /**
     * For seeding a database and testing
     */
    public boolean addDummyZipcode(int zipcodeID, String city, String standards) {
        ContentValues values = new ContentValues();
        values.put(ZIP_CODE, zipcodeID);
        values.put(CITY, city);
        values.put(RECYCLING_STANDARDS, standards);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean result = false;
        if (-1 != db.insert(TABLE_STANDARDS, null, values)) {
            result = true;
        }
        db.close();

        return result;
    }
    /**
     * For deleting a zipcode from database and testing
     */
    public boolean deleteDummyZipcode(int zipcodeID) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_STANDARDS + " WHERE "
                + ZIP_CODE + " = \"" + zipcodeID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        //dloughlin TODO instantiate zipcode object here

        if (cursor.moveToFirst()) {
            db.delete(TABLE_STANDARDS, ZIP_CODE + " = ?",
                    new String[] { String.valueOf(cursor.getString(0)) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //dloughlin TODO this will be a Zipcode object return type someotherday
    public Zipcode findZipcode(int zipcodeID) {
        String query = "Select * FROM " + TABLE_STANDARDS + " WHERE "
                + ZIP_CODE + " = \"" + zipcodeID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Zipcode result = null;

        Cursor cursor = db.rawQuery(query, null);
        //dloughlin TODO instantiate zipcode object here
        if (cursor.moveToFirst()) {

            // :plastic,coca-cola,pepsi:paper,cardboard,construction,origami:metals
            // Splits our string into categories, which are colon separated. The category name is the first word in each
            // index. The words in each index are comma seperated
            String standards = cursor.getString(2);

            Map<String, List<String>> map = new HashMap<String, List<String>>();

            List<String> products;

            String[] standardsArray = standards.split(":");
            // iterate over each category in the array.
            for(String cat : standardsArray) {
                String[] catArray = cat.split(",");
                String key = null;
                products = new ArrayList<>();
                for (String name: catArray) {
                    // the first name is the category name (key), the rest are placed in the list
                    if(null == key) {
                        key = name;
                    }
                    else {
                        products.add(name);
                    }
                }
                map.put(key, products);
            }

            result = new Zipcode(cursor.getInt(0), cursor.getString(1), map);

            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;

    }

    // dstoyer TODO make method for parsing the standards. The object needs to be a Map<String, List<String>

}