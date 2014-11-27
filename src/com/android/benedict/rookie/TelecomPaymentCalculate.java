
package com.android.benedict.rookie;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

public class TelecomPaymentCalculate {

    private static final String mLocal = "LocalPhone";
    private static ContentResolver mContRes;
    private Map<String, String> mNpNumMap;
    private int mIntraPay;
    private int mOutraPay;
    private int mLocalPay;
    private int mTotalPay;

    public TelecomPaymentCalculate(TelecomInfo telecomInfo, int position,
            TelecomInfoFragment telecomInfoFragment) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.NOVEMBER,24,0,0);
        String fromDate = Float.toString(calendar.getTimeInMillis());
        calendar.set(2014, Calendar.NOVEMBER, 27,0,0);
        String toDate = Float.toString(calendar.getTimeInMillis());
        String[] whereValue = {
                fromDate, toDate
        };

        int intraCount = 0;
        int outraCount = 0;
        int localCallCount = 0;

        mNpNumMap = new HashMap<String, String>();
        mContRes = telecomInfoFragment.getActivity().getContentResolver();

        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;

        Cursor npNumCursor = mContRes.query(uri, null, null, null, null);

        while (npNumCursor.moveToNext()) {
            mNpNumMap.put(npNumCursor.getString(1), npNumCursor.getString(2));

        }

        Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
                android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?", whereValue,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
//      Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
//      null, null,
//      android.provider.CallLog.Calls._ID + " DESC");

        while (cursor.moveToNext()) {
            // Log.d("abcd","numberType=" +
            if (cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)).equals(Integer.toString(CallLog.Calls.OUTGOING_TYPE))) {

                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                String id = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
                Log.d("abc","id = "+id +"number ="  + number);
                // Log.d("abcd","number=" + number.substring(0, 4));
                // Log.d("abcd","number=" +telecomInfo.getCorporation(position)
                if (mNpNumMap.get(number) != null) {
                    if (mNpNumMap.get(number).equals(telecomInfo.getCorporation(position))) {
                        if ((intraCount + duration) > Integer.parseInt(telecomInfo
                                .getIntraNework(position))) {
                            intraCount += duration;
                            mIntraPay = intraCount
                                    - Integer.parseInt(telecomInfo.getIntraNework(position));
                            Log.d("abc", "mIntrpay = " + mIntraPay);
                        } else {
                            intraCount += duration;
                            mIntraPay = 0;
                        }
                    } else {
                        if ((outraCount + duration) > Integer.parseInt(telecomInfo
                                .getOutraNework(position))) {
                            outraCount += duration;
                            mOutraPay = outraCount
                                    - Integer.parseInt(telecomInfo.getOutraNework(position));
                            //Log.d("abc", "mOutraPay = " + mOutraPay);
                        } else {
                            outraCount += duration;
                            mOutraPay = 0;
                            //Log.d("abc", "mOutraPay2 = " + mOutraPay);
                        }
                    }
                } else {
                    String prefix4Number = null;
                    String prefix2Number = null;

                    if (number.length() > 3) {
                        prefix4Number = telecomInfo.getInOutLocalNework(number.substring(0, 4));
                        prefix2Number = telecomInfo.getInOutLocalNework(number.substring(0, 2));
                    }

                    // Log.d("abcd", "1number=" + prefix4Number);
                    if (prefix4Number != null
                            && prefix4Number.equals(telecomInfo.getCorporation(position))) {

                        // Log.d("abcd", "2number=" + prefix4Number);
                        if ((intraCount + duration) > Integer.parseInt(telecomInfo
                                .getIntraNework(position))) {
                            intraCount += duration;
                            mIntraPay = intraCount
                                    - Integer.parseInt(telecomInfo.getIntraNework(position));
                            //Log.d("abc", "mIntrpay2 = " + mIntraPay);
                        } else {
                            intraCount += duration;
                            mIntraPay = 0;
                        }

                    } else if (prefix2Number != null && prefix2Number.equals(mLocal)) {
                        // Log.d("abcd", "3number=" + prefix2Number);
                        if ((localCallCount + duration) > Integer.parseInt(telecomInfo
                                .getLocalCall(position))) {
                            localCallCount += duration;
                            mLocalPay = localCallCount
                                    - Integer.parseInt(telecomInfo.getLocalCall(position));

                        } else {
                            localCallCount += duration;
                            mLocalPay = 0;
                        }
                    } else {
                        // Log.d("abcd", "4number=" + number);
                        if ((outraCount + duration) > Integer.parseInt(telecomInfo
                                .getOutraNework(position))) {
                            outraCount += duration;
                            mOutraPay = outraCount
                                    - Integer.parseInt(telecomInfo.getOutraNework(position));
                            //Log.d("abc", "mOutraPay3 = " + mOutraPay);
                        } else {
                            outraCount += duration;
                            mOutraPay = 0;
                        }
                    }
                }
                // mIntraPay = intraCount;
                // mOutraPay = outraCount;
                // mLocalPay = localCallCount;
            }
        }//cursor.close();
        
    }

    public String getIntraPay() {
        return Integer.toString(mIntraPay);
    }

    public String getOutraPay() {
        return Integer.toString(mOutraPay);
    }

    public String getLocalCallPay() {
        return Integer.toString(mLocalPay);
    }

    public String getTotalPay() {
        return Integer.toString(mTotalPay);
    }
}
