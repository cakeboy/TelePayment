
package com.example.android.effectivenavigation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;

public class TelecomPaymentCalculate {

    private static final String  freecall = "FREE_CALL";
    private static final String mLocal = "LocalPhone";
    private String mTelecomCorporation;
    private static ContentResolver mContRes;
    private int mDuration;
    private int mIntraPay;
    private int mOutraPay;
    private int mLocalPay;

    // private Map<Integer, String> mTelecomPhoneNumMap = new HashMap<Integer,
    // String>();

    public TelecomPaymentCalculate(TelecomInfo telecomInfo, int position,
            TelecomInfoFragment telecomInfoFragment) {

        
        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.NOVEMBER, 12);
        String fromDate = String.valueOf(calendar.getTimeInMillis());
        calendar.set(2014, Calendar.NOVEMBER, 20);
        String toDate = String.valueOf(calendar.getTimeInMillis());
        String[] whereValue = {fromDate,toDate};
        
        mContRes = telecomInfoFragment.getActivity().getContentResolver();
        Cursor c = mContRes.query(CallLog.Calls.CONTENT_URI, null,android.provider.CallLog.Calls.DATE+" BETWEEN ? AND ?", whereValue,
                android.provider.CallLog.Calls._ID + " DESC");
        int intraCount = 0;
        int outraCount = 0;
        int localCallCount = 0;

        
        
        
        while (c.moveToNext()) {
          //Log.d("abcd","numberType=" + c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)));
if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE))==CallLog.Calls.OUTGOING_TYPE){
    
                String number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                int duration = c.getInt(c.getColumnIndex(CallLog.Calls.DURATION));
//Log.d("abcd","number=" + number.substring(0, 4));
                //Log.d("abcd","number=" +telecomInfo.getCorporation(position) );
                
                String prefix4Number = null;
                String prefix2Number = null;
                if(number.length() >3)
                    {
                    prefix4Number = telecomInfo.getInOutLocalNumber(number.substring(0, 4));
                    prefix2Number = telecomInfo.getInOutLocalNumber(number.substring(0, 2));
                    }
                

                //Log.d("abcd", "1number=" + prefix4Number);
                if (prefix4Number != null
                        && prefix4Number.equals(telecomInfo.getCorporation(position))) {
                    // if (telecomInfo.getInOutLocalNumber(number.substring(0,
                    // 2)).equals(mLocal))
                    // {
                    Log.d("abcd", "2number=" + prefix4Number);

                    intraCount += duration;
                } else if (prefix2Number!=null && prefix2Number.equals(mLocal)) {
                    Log.d("abcd",
                            "3number=" + prefix2Number);
                    localCallCount += duration;
                }
                else
                {
                    Log.d("abcd", "4number=" + number);
                    outraCount += duration;
                }   
        }
        mIntraPay = intraCount;
        mOutraPay = outraCount;
        mLocalPay = localCallCount;
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
}
