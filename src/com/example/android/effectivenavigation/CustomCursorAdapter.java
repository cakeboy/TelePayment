
package com.example.android.effectivenavigation;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class CustomCursorAdapter extends SimpleCursorAdapter {

    // protected int[] mFrom;
    // protected int[] mTo;
    String[] mOriginalFrom;

    // private Object setViewText;

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to,
            int flags) {
        super(context, layout, c, from, to, flags);
        // mTo = to;
        mOriginalFrom = from;
        // TODO Auto-generated constructor stub
    }

    // @SuppressLint("SimpleDateFormat")
    // @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        super.bindView(view, context, cursor);
        // String name =
        // cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
        // final int count = mTo.length;
        // final int[] from = mFrom;
        // final int[] to = mTo;
        // final ViewBinder binder ;
        // String text = null;

        // for(int i = 0; i<count; i++){

        // String number =
        // cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
        
        View v = view.findViewById(R.id.name);
        String text = cursor.getString(cursor.getColumnIndex(mOriginalFrom[0]));
        if (text == null)
            setViewText((TextView) v, "UnKnow");
        else
            setViewText((TextView) v, text);
        View v1 = view.findViewById(R.id.mobile);
        String number = cursor.getString(cursor.getColumnIndex(mOriginalFrom[1]));
        setViewText((TextView) v1, number);
        View v2 = view.findViewById(R.id.date);
        long dateTimeMillis = cursor.getLong(cursor.getColumnIndex(mOriginalFrom[2]));
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString = format.format(dateTimeMillis);
        setViewText((TextView) v2, dateString);
    }

    // @Override
    // public View newView(Context context, Cursor cursor, ViewGroup parent) {
    // // TODO Auto-generated method stub
    // return super.newView(context, cursor, parent);
    // }
    //

}
