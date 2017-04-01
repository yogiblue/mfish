package com.muwuprojects.mfish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by swann on 01/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "mfish";

    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_DATE = "lastdate";

    // status 0 - locked, 1 unlocked, 2 complete
    // Contacts Table ->Columns names!!!!!!!!!!!
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT_DESCRIPTION = "the_text";
    private static final String KEY_TEXT_DATE = "the_date";
    private static final String KEY_TEXT_CARDNAME = "cardname";
    private static final String KEY_INT_COUNT = "numtimes";
    private static final String KEY_INT_TYPE = "type";
    private static final String KEY_TEXT_EXTRA = "extra";


    private static final String KEY_LONG_DATE = "the_date";


    private Context theContext;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        theContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT_DESCRIPTION + " TEXT,"
                + KEY_TEXT_DATE + " TEXT,"
                + KEY_TEXT_CARDNAME + " TEXT,"
                + KEY_INT_COUNT + " INTEGER,"
                + KEY_INT_TYPE + " INTEGER,"
                + KEY_TEXT_EXTRA + " TEXT"
                + ")";

        db.execSQL(CREATE_MESSAGE_TABLE);


        String CREATE_DATE_TABLE = "CREATE TABLE " + TABLE_DATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LONG_DATE + " INT"
                + ")";

        db.execSQL(CREATE_DATE_TABLE);

        ContentValues valuesStats = new ContentValues();

        valuesStats.put(KEY_TEXT_DESCRIPTION, "Enjoy it all today."); // Description
        valuesStats.put(KEY_TEXT_DATE, "");
        valuesStats.put(KEY_TEXT_CARDNAME, "ad");
        valuesStats.put(KEY_INT_COUNT, 0);
        valuesStats.put(KEY_INT_TYPE, 0);
        valuesStats.put(KEY_TEXT_EXTRA, "");
        db.insert(TABLE_MESSAGES, null, valuesStats);
        valuesStats.clear();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        // Create tables again
        onCreate(db);

    }

    public Long getLastDate() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATE, new String[] { KEY_LONG_DATE}, KEY_ID + "=?",
                new String[] { Long.toString(1) }, null, null, null, null);

        if (cursor != null)
        {
            if(!cursor.isAfterLast())
            {
                cursor.moveToFirst();
                return cursor.getLong(0);
            }

        }
        return (long) 0;

        //SQLiteDatabase db = this.getReadableDatabase();
        //String dateString = (String) DateFormat.format("dd/MM/yyyy", System.currentTimeMillis());
        //return dateString;
    }

    public void updateDate() {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(KEY_LONG_DATE, System.currentTimeMillis());
        db.update(TABLE_DATE, args, KEY_ID + "=1", null);


    }

    public void createDate() {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesStats = new ContentValues();
        valuesStats.put(KEY_LONG_DATE, System.currentTimeMillis());
        db.insert(TABLE_DATE, null, valuesStats);

    }

    public long getNumDaysSinceLastVisit() {
        // TODO Auto-generated method stub
        long lastVisitLong = getLastDate();
        long nowLong = System.currentTimeMillis();
        long oneDay = 1000 * 60 * 60 * 24;
        long diff = (nowLong - lastVisitLong) / oneDay;
        return diff;
    }

    public long getNumHoursSinceLastVisit() {
        // TODO Auto-generated method stub
        long lastVisitLong = getLastDate();
        long nowLong = System.currentTimeMillis();
        long oneDay = 1000 * 60 * 60;
        long diff = (nowLong - lastVisitLong) / oneDay;
        return diff;
    }

}
