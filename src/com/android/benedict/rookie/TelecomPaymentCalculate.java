
package com.android.benedict.rookie;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

public class TelecomPaymentCalculate {

    private static final String mLocal = "LocalPhone";
    private static ContentResolver mContRes;
    private Map<String, String> mNpNumMap;
    private float mIntraPay;
    private float mOutraPay;
    private float mLocalPay;
    private int mTotalPay;
    private String INTRA_NETWORK = "IntraNetwork";
    private String EXTRA_NETWORK = "ExtraNetwork";
    private String OTHER = "Others";

    public TelecomPaymentCalculate(TelecomInfo telecomInfo, int position,
            TelecomInfoFragment telecomInfoFragment) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.NOVEMBER, 24, 0, 0);
        String fromDate = Float.toString(calendar.getTimeInMillis());
        calendar.set(2014, Calendar.NOVEMBER, 27, 24, 0);
        String toDate = Float.toString(calendar.getTimeInMillis());
        String[] whereValue = {
                fromDate, toDate
        };

        float intraCount = (float) 0.0;
        float extraCount = (float) 0.0;
        float localCallCount = (float) 0.0;
        
        mIntraPay = (float) 0.0;
        mOutraPay = (float) 0.0;
        mLocalPay = (float) 0.0;
        
        float intraFree = Float.parseFloat(telecomInfo.getIntraFree(position));
        float extraFree = Float.parseFloat(telecomInfo.getOutraFree(position));
        float localCallFree = Float.parseFloat(telecomInfo.getLocalCallFree(position));
        float overIntraFree = Float.parseFloat(telecomInfo.getOverIntraFree(position));
        float overExtraFree = Float.parseFloat(telecomInfo.getOverExtraFree(position));
        float overlocalCallFree = Float.parseFloat(telecomInfo.getOverLocalCallFree(position));
        String selectCorporation = telecomInfo.getCorporation(position);

        mNpNumMap = new HashMap<String, String>();
        mContRes = telecomInfoFragment.getActivity().getContentResolver();

        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;

        Cursor npNumCursor = mContRes.query(uri, null, null, null, null);

        while (npNumCursor.moveToNext()) {
            mNpNumMap.put(npNumCursor.getString(1), npNumCursor.getString(2));
        }
        npNumCursor.close();

        Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
                android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?", whereValue,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
        // Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
        // null, null,
        // android.provider.CallLog.Calls._ID + " DESC");

        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)).equals(
                    Integer.toString(CallLog.Calls.OUTGOING_TYPE))) {

                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                float duration = cursor.getFloat(cursor.getColumnIndex(CallLog.Calls.DURATION));


                String npNumType = mNpNumMap.get(number);

                if (null != npNumType) {
                    if (npNumType.equals(INTRA_NETWORK)) {
                        intraCount += duration;
                        if (intraCount > intraFree) {
                            mIntraPay = (intraCount - intraFree) * overIntraFree;
                        }
                    } else {
                        extraCount += duration;
                        if (extraCount > extraFree) {
                            mOutraPay = (extraCount - extraFree)*overExtraFree;
                        }
                    }

                } else {
                    if (number.length() > 3) {
                        String prefix4Num = telecomInfo.getInOutLocalNework(number.substring(0, 4));;
                        if (null != prefix4Num) {
                            if (prefix4Num.equals(selectCorporation)) {
                                intraCount += duration;
                                if (intraCount > intraFree)
                                    mIntraPay = (intraCount - intraFree) * overIntraFree;
                            } else {
                                extraCount += duration;
                                if (extraCount > extraFree)
                                    mOutraPay = (extraCount - extraFree) * overExtraFree;
                            }
                        }

                        else {
                            localCallCount += duration;
                            if (localCallCount > localCallFree)
                                mLocalPay = (localCallCount - localCallFree) * overlocalCallFree;
                        }
                    }

                }
            }
        }
        cursor.close();

    }

    public String getIntraPay() {
        return Float.toString(mIntraPay);
    }

    public String getOutraPay() {
        return Float.toString(mOutraPay);
    }

    public String getLocalCallPay() {
        return Float.toString(mLocalPay);
    }

    public String getTotalPay() {
        return Integer.toString(mTotalPay);
    }
}
