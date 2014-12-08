
package com.android.benedict.rookie;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class NpPhoneNumberContentProvider extends ContentProvider {

    private NpPhoneNumberDbHelper NpPhoneNumberDbHp;
    public static final String AUTHORITY = "com.benedict.npphonenumber";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/npphonenumbers");

    public NpPhoneNumberContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = NpPhoneNumberDbHp.getWritableDatabase();
        String rowId = uri.getPathSegments().get(1);
        // Log.i("","...............rowId="+rowId);
        return db.delete("npNumTable", "_id = " + rowId, null);
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = NpPhoneNumberDbHp.getWritableDatabase();
        long rowID = db.insert("npNumTable", null, values);
        if (rowID > 0) {
            Uri url = Uri.parse("content://" + AUTHORITY + "/npphonenumbers" + "/" + rowID);
            // Log.d( "abc" , ".............run insert.........url=" +url);
            return url;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        NpPhoneNumberDbHp = new NpPhoneNumberDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = NpPhoneNumberDbHp.getWritableDatabase();

        return db.query("npNumTable", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }
}
