
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

public class CallLogFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CALL_LOG_LOADER = 0;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private CallLogCursorAdapter mCustomCursorAdapter;
    private String[] callLogType = new String[1];
    private static String CALL_TYPE;

    public static CallLogFragment newInstance(int callType) {
        CallLogFragment fragment = new CallLogFragment();
        Bundle args = new Bundle();
        args.putInt(CALL_TYPE, callType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
        // TODO Auto-generated method stub

        switch (getArguments().getInt(CALL_TYPE)) {
            case CallLog.Calls.INCOMING_TYPE:
                callLogType[0] = Integer.toString(CallLog.Calls.INCOMING_TYPE);
                break;

            case CallLog.Calls.OUTGOING_TYPE:
                callLogType[0] = Integer.toString(CallLog.Calls.OUTGOING_TYPE);
                break;

            case CallLog.Calls.MISSED_TYPE:
                callLogType[0] = Integer.toString(CallLog.Calls.MISSED_TYPE);
                break;

            default:
                break;
        }

        switch (loaderId) {

            case CALL_LOG_LOADER:

                return new CursorLoader(getActivity(), CallLog.Calls.CONTENT_URI, null,
                        CallLog.Calls.TYPE + "= ?", callLogType, android.provider.CallLog.Calls._ID
                                + " DESC");
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

        mCustomCursorAdapter = new CallLogCursorAdapter(getActivity(), R.layout.call_log_adapter,
                cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
        getLoaderManager().initLoader(CALL_LOG_LOADER, null, this);
        return inflater.inflate(R.layout.callog_fragment, container, false);
    }
}
