
package com.android.benedict.rookie;

import java.text.SimpleDateFormat;

import com.android.benedict.rookie.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class CustomCursorAdapter extends SimpleCursorAdapter {

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
    @SuppressLint("SimpleDateFormat")
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        super.bindView(view, context, cursor);

        View vName = view.findViewById(R.id.name);
        String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
        if (name == null)
            setViewText((TextView) vName, "UnKnow");
        else
            setViewText((TextView) vName, name);

        View vNumber = view.findViewById(R.id.mobile);
        String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
        setViewText((TextView) vNumber, number);

        View vDate = view.findViewById(R.id.date);
        long dateTimeMillis = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString = format.format(dateTimeMillis);

        setViewText((TextView) vDate, dateString);
    }

}
