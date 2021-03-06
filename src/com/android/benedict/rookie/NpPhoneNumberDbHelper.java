
package com.android.benedict.rookie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NpPhoneNumberDbHelper extends SQLiteOpenHelper {

    public String sCreatetableCommand;
    public static final String DATABASE_NAME = "npNum.db";
    public static final String CACHED_NAME = "name";
    public static final String CACHED_CORPORATION = "corporation";
    private final static int VERSION = 1;

    public NpPhoneNumberDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE npNumTable" + "(" + "_id INTEGER PRIMARY KEY,"
                + "number TEXT NOT NULL," + "coporation TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS npNumTable");
        onCreate(db);
    }
}
