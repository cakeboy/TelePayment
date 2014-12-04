
package com.android.benedict.rookie;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

public class ComputeTelecomFare {

    private static ContentResolver mResolver;
    private Map<String, String> mNpNumMap;
    private float mIntraPay;
    private float mOutraPay;
    private float mLocalPay;
    private int mTotalPay;
    private float mIntraPayRemain;
    private float mExtraPayRemain;
    private float mLocalPayRemain;
    private static final String INTRA_NETWORK = "IntraNetwork";
    private static final String OTHER = "Others";
    private static final String FREE_CALL = "FreeCall";
    private static final String QUERY_DATE_START = "queryDateStart";
    private static final String QUERY_DATE_END = "queryDateEnd";
    private static final String START_YEAR = "startyear";
    private static final String START_Month = "startmonth";
    private static final String START_Day = "startday";
    private static final String END_YEAR = "endyear";
    private static final String END_MONTH = "endmonth";
    private static final String END_DAY = "endday";

    public ComputeTelecomFare(TelecomInfo telecomInfo, int position,
            TelecomInfoFragment telecomInfoFragment) {

        Calendar calendar = Calendar.getInstance();

        SharedPreferences queryDateStartPreferences = telecomInfoFragment.getActivity()
                .getSharedPreferences(QUERY_DATE_START, 0);
        int startYear = queryDateStartPreferences.getInt(START_YEAR, calendar.get(Calendar.YEAR));
        int startMonth = queryDateStartPreferences
                .getInt(START_Month, calendar.get(Calendar.MONTH));
        int startDay = queryDateStartPreferences.getInt(START_Day,
                calendar.get(Calendar.DAY_OF_MONTH));

        SharedPreferences queryDateEndPreferences = telecomInfoFragment.getActivity()
                .getSharedPreferences(QUERY_DATE_END, 0);
        int endYear = queryDateEndPreferences.getInt(END_YEAR, calendar.get(Calendar.YEAR));
        int endMonth = queryDateEndPreferences.getInt(END_MONTH, calendar.get(Calendar.MONTH));
        int endDay = queryDateEndPreferences.getInt(END_DAY, calendar.get(Calendar.DAY_OF_MONTH));

        calendar.set(startYear, startMonth, startDay, 0, 0, 0);
        Log.d("abc", "year = " + startMonth);
        String fromDate = Float.toString(calendar.getTimeInMillis());
        calendar.set(endYear, endMonth, endDay, 23, 59, 59);
        String toDate = Float.toString(calendar.getTimeInMillis());

        String[] whereValue = {
                fromDate, toDate
        };

        float intraDuration = (float) 0.0;
        float extraDuration = (float) 0.0;
        float localCallDuration = (float) 0.0;

        mIntraPay = (float) 0.0;
        mOutraPay = (float) 0.0;
        mLocalPay = (float) 0.0;
        mIntraPayRemain = 0;
        mExtraPayRemain = 0;
        mLocalPayRemain = 0;

        float intraFree = Float.parseFloat(telecomInfo.getIntraFree(position)) * (float) 60.0;
        float extraFree = Float.parseFloat(telecomInfo.getOutraFree(position)) * (float) 60.0;
        float localCallFree = Float.parseFloat(telecomInfo.getLocalCallFree(position))
                * (float) 60.0;
        float overIntraFree = Float.parseFloat(telecomInfo.getOverIntraFree(position));
        float overExtraFree = Float.parseFloat(telecomInfo.getOverExtraFree(position));
        float overlocalCallFree = Float.parseFloat(telecomInfo.getOverLocalCallFree(position));

        String selectCorporation = telecomInfo.getCorporation(position);

        mNpNumMap = new HashMap<String, String>();
        mResolver = telecomInfoFragment.getActivity().getContentResolver();

        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;

        Cursor npNumCursor = mResolver.query(uri, null, null, null, null);

        while (npNumCursor.moveToNext()) {
            mNpNumMap.put(npNumCursor.getString(1), npNumCursor.getString(2));
        }
        npNumCursor.close();

        Cursor cursor = mResolver.query(CallLog.Calls.CONTENT_URI, null,
                android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?", whereValue,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
        // Cursor cursor = mContRes.query(CallLog.Calls.CONTENT_URI, null,
        // null, null,
        // android.provider.CallLog.Calls._ID + " DESC");
        if (cursor == null)
            return;

        cursor.moveToPosition(-1);

        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)).equals(
                    Integer.toString(CallLog.Calls.OUTGOING_TYPE))) {

                String queryNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                float duration = cursor.getFloat(cursor.getColumnIndex(CallLog.Calls.DURATION));

                String npNumType = mNpNumMap.get(queryNumber);

                if (null != npNumType && npNumType.equals(OTHER))
                    continue;

                if (null != npNumType) {
                    if (npNumType.equals(INTRA_NETWORK)) {
                        intraDuration += duration;
                        if (intraDuration > intraFree) {
                            mIntraPay = (intraDuration - intraFree) * overIntraFree;
                        }
                    } else {
                        extraDuration += duration;
                        if (extraDuration > extraFree) {
                            mOutraPay = (extraDuration - extraFree) * overExtraFree;
                        }
                    }

                } else {

                    if (queryNumber.length() < 4)
                        continue;

                    if (queryNumber.length() > 3) {

                        String prefix4Num = telecomInfo.getInOutLocalNework(queryNumber.substring(
                                0, 4));

                        // String prefix2Num =
                        // telecomInfo.getInOutLocalNework(queryNumber.substring(
                        // 0, 2));

                        if (null != prefix4Num && prefix4Num.equals(FREE_CALL))
                            continue;

                        if (null != prefix4Num) {
                            if (prefix4Num.equals(selectCorporation)) {
                                intraDuration += duration;
                                if (intraDuration > intraFree)
                                    mIntraPay = (intraDuration - intraFree) * overIntraFree;
                            } else {
                                extraDuration += duration;
                                if (extraDuration > extraFree)
                                    mOutraPay = (extraDuration - extraFree) * overExtraFree;
                            }
                        }

                        else {
                            Log.d("abc", "Duration =" + duration);
                            localCallDuration += duration;
                            if (localCallDuration > localCallFree)
                                mLocalPay = (localCallDuration - localCallFree) * overlocalCallFree;
                        }
                    }

                }
            }
        }
        cursor.close();

        if (localCallDuration > localCallFree)
            mLocalPayRemain = 0;
        else
            mLocalPayRemain = localCallFree - localCallDuration;
        if (extraDuration > extraFree)
            mExtraPayRemain = 0;
        else
            mExtraPayRemain = extraFree - extraDuration;
        if (intraDuration > intraFree)
            mIntraPayRemain = 0;
        else
            mIntraPayRemain = intraFree - intraDuration;

        Log.d("abc", "intraCount =" + intraDuration);
        Log.d("abc", "extraCount =" + extraDuration);
        Log.d("abc", "localCallCount =" + localCallDuration);
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

    public String getIntraPayRemain() {
        return Float.toString(mIntraPayRemain);
    }

    public String getOutraPayRemain() {
        return Float.toString(mExtraPayRemain);
    }

    public String getLocalCallPayRemain() {
        return Float.toString(mLocalPayRemain);
    }

    public String getTotalPay() {
        return Integer.toString(mTotalPay);
    }
}
