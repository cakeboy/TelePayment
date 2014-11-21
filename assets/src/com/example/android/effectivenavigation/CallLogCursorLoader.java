
package com.example.android.effectivenavigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class CallLogCursorLoader extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private int mCallType;
    
    private int mIncoming=1;
    private int mMissed=3;
    private int mCallInfo;
    private String[] OUT_GOING;
    private static final int URL_LOADER = 0;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private CustomCursorAdapter mCustomCursorAdapter;
    
    public CallLogCursorLoader(int callType) {
        mCallType = callType;
        
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
        // TODO Auto-generated method stub
        
        OUT_GOING = new String[1];
        switch (mCallType) {
            case CallLog.Calls.INCOMING_TYPE:
                OUT_GOING[0]= Integer.toString(CallLog.Calls.INCOMING_TYPE);
                break;
            
            case CallLog.Calls.OUTGOING_TYPE:
                OUT_GOING[0]= Integer.toString(CallLog.Calls.OUTGOING_TYPE);
                break;
            case CallLog.Calls.MISSED_TYPE:
                OUT_GOING[0]= Integer.toString(CallLog.Calls.MISSED_TYPE);
                break;
            default:
                break;
        }
        
        
        switch (loaderId) {
            case URL_LOADER:

                
                return new CursorLoader(getActivity(), CallLog.Calls.CONTENT_URI, null, CallLog.Calls.TYPE + "= ?" ,  OUT_GOING,
                        android.provider.CallLog.Calls._ID + " DESC");

            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        // TODO Auto-generated method stub
        /*
         * while (cursor.moveToNext()){ Map<String, Object> item = new
         * HashMap<String, Object>(); String name =
         * cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
         * String number =
         * cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)); String
         * duration =
         * cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)); long
         * dateTimeMillis =
         * cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
         * SimpleDateFormat format = new SimpleDateFormat( "MM-dd HH:mm:ss" );
         * String dateString = format.format(dateTimeMillis);
         * if(cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE))==2){
         * if(name == null) item.put("name", "unknow"); else
         * item.put("name",name); item.put("number",number);
         * item.put("date",dateString); items.add(item);} }
         */
        String[] from = {
                CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE
        };
        int[] to = {
                R.id.name, R.id.mobile, R.id.date
        };

        // mSimpleAdapter = new SimpleAdapter(getActivity(), items,
        // R.layout.call_log, new String[]{"name","number","date"}, new
        // int[]{R.id.name,R.id.mobile,R.id.date});
        // setListAdapter(mSimpleAdapter);
        // mSimpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
        // R.layout.call_log, cursor, from, to,
        // mCustomCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // setListAdapter(mSimpleCursorAdapter);
        //int callType = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
        //if(callType == mCallType){
        mCustomCursorAdapter = new CustomCursorAdapter(getActivity(), R.layout.call_log, cursor,
                from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(mCustomCursorAdapter);
        mCustomCursorAdapter.swapCursor(cursor);
        //}
        // mSimpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        mCustomCursorAdapter.swapCursor(null);
        // mSimpleCursorAdapter.swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getLoaderManager().initLoader(URL_LOADER, null, this);
        return inflater.inflate(R.layout.callogfragment, container, false);

    }

}
