
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.effectivenavigation.R;

public class CallLogCursorLoaderFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private int mCallType;
    private static final int URL_LOADER = 0;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private CustomCursorAdapter mCustomCursorAdapter;
    private String[] Call_Log_Type = new String[1];
    
    public static CallLogCursorLoaderFragment newInstance(int callType){
        CallLogCursorLoaderFragment fragment = new CallLogCursorLoaderFragment();
        Bundle args = new Bundle();
        args.putInt("callType", callType);
        fragment.setArguments(args);
        return fragment;
        }

//    public CallLogCursorLoaderFragment(int callType) {
//        //mCallType = callType;
//        switch (callType) {
//            case CallLog.Calls.INCOMING_TYPE:
//                Call_Log_Type[0] = Integer.toString(CallLog.Calls.INCOMING_TYPE);
//                //break;
//
//            case CallLog.Calls.OUTGOING_TYPE:
//                Call_Log_Type[0] = Integer.toString(CallLog.Calls.OUTGOING_TYPE);
//                //break;
//
//            case CallLog.Calls.MISSED_TYPE:
//                Call_Log_Type[0] = Integer.toString(CallLog.Calls.MISSED_TYPE);
//                //break;
//
//            default:
//                break;
//        }
//        
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
        // TODO Auto-generated method stub

        // set the selection of cursorLoader
        //String[] Call_Log_Type = new String[1];
        
        //int args = arg1.getInt("callType");

        switch (getArguments().getInt("callType")) {
            case CallLog.Calls.INCOMING_TYPE:
                Call_Log_Type[0] = Integer.toString(CallLog.Calls.INCOMING_TYPE);
                break;

            case CallLog.Calls.OUTGOING_TYPE:
                Call_Log_Type[0] = Integer.toString(CallLog.Calls.OUTGOING_TYPE);
                break;

            case CallLog.Calls.MISSED_TYPE:
                Call_Log_Type[0] = Integer.toString(CallLog.Calls.MISSED_TYPE);
                break;

            default:
                break;
        }

        switch (loaderId) {

            case URL_LOADER:

                return new CursorLoader(getActivity(), CallLog.Calls.CONTENT_URI, null,
                        CallLog.Calls.TYPE + "= ?", Call_Log_Type, android.provider.CallLog.Calls._ID
                                + " DESC");
//                return new CursorLoader(getActivity(), CallLog.Calls.CONTENT_URI, null,
//                        null, null, android.provider.CallLog.Calls._ID
//                                + " DESC");
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        // TODO Auto-generated method stub

        String[] from = {
                CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE
        };
        int[] to = {
                R.id.name, R.id.mobile, R.id.date
        };

        mCustomCursorAdapter = new CustomCursorAdapter(getActivity(), R.layout.call_log, cursor,
                from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(mCustomCursorAdapter);
        mCustomCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        mCustomCursorAdapter.swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getLoaderManager().initLoader(URL_LOADER, null, this);
        return inflater.inflate(R.layout.callogfragment, container, false);

    }

}
