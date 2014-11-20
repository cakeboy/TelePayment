package com.example.android.effectivenavigation;

//import android.content.ContentResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

@SuppressLint("SimpleDateFormat")
public class callLogFragemt extends ListFragment{
    
    //private Cursor cursor=null;
    //private int FLAG_REGISTER_CONTENT_OBSERVER;
    private static ContentResolver mContRes ;
    public List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
    private SimpleAdapter simpleAdapter;
    int rowNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        //ContentProvider
        mContRes = getActivity().getContentResolver();

        Cursor c = mContRes.query(CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                android.provider.CallLog.Calls._ID + " DESC");
        //rowNum = c.getCount();
        //Log.d("abc", "rowNum = " + rowNum);
        //if (rowNum != 0){
        //c.moveToFirst();
        //if(c.getInt((CallLog.Calls.OUTGOING_TYPE))==1)
            /*while (c.moveToNext())
            {
                Map<String, Object> item = new HashMap<String, Object>();
                //c.moveToNext();
        
                
                //String id = c.getString(c.getColumnIndex(CallLog.Calls._ID));
                String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));
                long dateTimeMillis = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                SimpleDateFormat format =   new SimpleDateFormat( "MM-dd HH:mm:ss" );  
                String dateString = format.format(dateTimeMillis);
                
                if(c.getInt(c.getColumnIndex(CallLog.Calls.TYPE))==2){
                if(name == null)
                    item.put("name", "unknow");
                else
                item.put("name",name);
                item.put("number",number);
                item.put("date",dateString);
                items.add(item);}
            }*/
            //Log.d("abc", "items count = " + items.size());
                //c.moveToNext();
            
            //} 
            //c.close();
        //}
        
        /*long dateTimeMillis = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
        //long dateTimeMillis = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
        SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
        dateString = format.format(dateTimeMillis);*/

        String[] from = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" )
                                .format( new  Date(Long.parseLong(CallLog.Calls.DATE)))};
        int to[] = {
                R.id.name, R.id.mobile, R.id.date
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.call_log, c,
                from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        /*simpleAdapter = new SimpleAdapter(getActivity(), items, R.layout.call_log, new String[]{"name","number","date"}, new int[]{R.id.name,R.id.mobile,R.id.date});
        setListAdapter(simpleAdapter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.callogfragment, container, false);
        //getLoaderManager().initLoader(0, null , this );
    }
    
    

}
