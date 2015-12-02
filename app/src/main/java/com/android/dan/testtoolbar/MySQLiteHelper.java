package com.android.dan.testtoolbar;
// Ref: http://www.vogella.com/tutorials/AndroidSQLite/article.html#databasetutorial_database
// Used the above as a guide for working with SQLite on Android

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.dan.testtoolbar.zipcode.Zipcode;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_STANDARDS = "all_standards";
    public static final String ZIP_CODE = "_id";
    public static final String RECYCLING_STANDARDS = "recycling_standards";
    public static final String CITY = "city";

    private static final String DATABASE_NAME = "recycle.db";
    private static final int DATABASE_VERSION = 1;

    // Create the database
    //dloughlin(TODO): we need to add categories for recycling (eg. plastic, paper, etc)
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

    //dloughlin(TODO):make ZipCode Class
    public Zipcode queryZipCode(int zipcodeID) {

        String query = "Select * FROM " + TABLE_STANDARDS + " WHERE " + ZIP_CODE + " = \"" + zipcodeID + "\"";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        //dloughlin(TODO): Change the below to something that will work with zip code class
        //ZipCodeClassName zipcode = new ZipCodeClassName();

        // dstoyer TODO: replace the null arguments with valid info.
        Zipcode zipcode = new Zipcode(zipcodeID, null, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            zipcode.setID(Integer.parseInt(cursor.getString(0)));
            zipcode.setCity(cursor.getString(1));
//            zipcode.setStandards(cursor.getString(1));
            cursor.close();
        } else {
            zipcode = null;
        }

        database.close();
        return zipcode;
    }
}
