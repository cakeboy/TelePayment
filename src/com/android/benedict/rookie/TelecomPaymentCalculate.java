
package com.android.benedict.rookie;

import java.util.Calendar;

import android.R.integer;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

public class TelecomPaymentCalculate {

    private static final String mLocal = "LocalPhone";
    private static ContentResolver mContRes;
    private int mIntraPay;
    private int mOutraPay;
    private int mLocalPay;
    private int mTotalPay;


    public TelecomPaymentCalculate(TelecomInfo telecomInfo, int position,
            TelecomInfoFragment telecomInfoFragment) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.NOVEMBER, 23);
        String fromDate = String.valueOf(calendar.getTimeInMillis());
        calendar.set(2014, Calendar.NOVEMBER, 25);
        String toDate = String.valueOf(calendar.getTimeInMillis());
        String[] whereValue = {
                fromDate, toDate
        };

        int intraCount = 0;
        int outraCount = 0;
        int localCallCount = 0;
        mContRes = telecomInfoFragment.getActivity().getContentResolver();
        
        Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
                android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?", whereValue,
                android.provider.CallLog.Calls._ID + " DESC");
        

        while (cursor.moveToNext()) {
            // Log.d("abcd","numberType=" +
            if (cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.OUTGOING_TYPE) {

                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                // Log.d("abcd","number=" + number.substring(0, 4));
                // Log.d("abcd","number=" +telecomInfo.getCorporation(position)

                String prefix4Number = null;
                String prefix2Number = null;
                
                if (number.length() > 3) {
                    prefix4Number = telecomInfo.getInOutLocalNework(number.substring(0, 4));
                    prefix2Number = telecomInfo.getInOutLocalNework(number.substring(0, 2));
                }

                // Log.d("abcd", "1number=" + prefix4Number);
                if (prefix4Number != null
                        && prefix4Number.equals(telecomInfo.getCorporation(position))) {

                    //Log.d("abcd", "2number=" + prefix4Number);
                    if((intraCount+duration) > Integer.parseInt(telecomInfo.getIntraNework(position))){
                        intraCount +=  duration ;
                        mIntraPay = intraCount- Integer.parseInt(telecomInfo.getIntraNework(position));
                        Log.d("abc","mIntrpay = " +mIntraPay);
                    }
                    else {
                        intraCount+= duration;
                        mIntraPay = 0;
                    }

                    
                } else if (prefix2Number != null && prefix2Number.equals(mLocal)) {
                    //Log.d("abcd", "3number=" + prefix2Number);
                    if((localCallCount+duration) > Integer.parseInt(telecomInfo.getLocalCall(position))){
                        localCallCount += duration;
                        mLocalPay = localCallCount- Integer.parseInt(telecomInfo.getLocalCall(position));
                        
                    }
                    else {
                        localCallCount += duration;
                        mLocalPay = 0;
                    }
                } else {
                    //Log.d("abcd", "4number=" + number);
                    if((outraCount+duration) > Integer.parseInt(telecomInfo.getOutraNework(position))){
                        outraCount += duration;
                        mOutraPay = outraCount- Integer.parseInt(telecomInfo.getOutraNework(position));
                    }
                    else {
                        outraCount+= duration;
                        mOutraPay = 0;
                    }
                }
            }
            //mIntraPay = intraCount;
            //mOutraPay = outraCount;
            //mLocalPay = localCallCount;
            
        }
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
