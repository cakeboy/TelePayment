
package com.android.benedict.rookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;

public class TelecomInfo {

    private static final String CHT = "CHTtelecom";
    private static final String TWM = "TWMtelecom";
    private static final String FET = "FETtelecom";
    private static final String LOCAL = "LocalPhone";
    private static final String FREECALL = "FreeCall";
    private static final String PAYMENT_TYPE = "fee_project";

    private static final int mIndexofId = 0;
    private static final int mIndexofCorporation = 1;
    private static final int mIndexofPayment = 2;
    private static final int mIndexofExtraFree = 4;
    private static final int mIndexofIntraFree = 3;
    private static final int mIndexofLocalCallFree = 5;
    private static final int mIndexofOverIntraFree = 6;
    private static final int mIndexofOverExtraFree = 7;
    private static final int mIndexofOverLocalCallFree = 8;

    private static final String[] CHT_PHONE_PREFIX = {
            "0910", "0921", "0933", "0939", "0937", "0972", "0975"
    };
    private static final String[] TWM_PHONE_PREFIX = {
            "0918", "0920", "0922", "0935", "0939", "0956", "0987"
    };
    private static final String[] FET_PHONE_PREFIX = {
            "0916", "0917", "0926", "0930", "0953", "0986"
    };
    private static final String[] LOCAL_PHONE_PREFIX = {
            "02", "03", "04", "07", "08"
    };
    private static final String[] FREE_CALL_PREFIX = {
        "0800"
    };

    @SuppressLint("UseSparseArrays")
    private Map<Integer, Payment> mtelecomPaymentMap = new HashMap<Integer, Payment>();
    private Map<String, String> mTelecomPhoneNumMap = new HashMap<String, String>();

    public TelecomInfo(Context context) {

        int TELECOM_LIST_COUNT = 0;
        try {
            XmlResourceParser xrp = context.getResources().getXml(R.xml.telecom_list);
            int eventType = xrp.getEventType();

            while (eventType != XmlResourceParser.END_DOCUMENT) {

                if (eventType == XmlResourceParser.START_TAG && xrp.getName().equals(PAYMENT_TYPE)) {
                    // Log.d("abc","tagName="+tagName);

                    Payment payment = new Payment(xrp.getAttributeValue(mIndexofId),
                            xrp.getAttributeValue(mIndexofCorporation),
                            xrp.getAttributeValue(mIndexofPayment),
                            xrp.getAttributeValue(mIndexofIntraFree),
                            xrp.getAttributeValue(mIndexofExtraFree),
                            xrp.getAttributeValue(mIndexofLocalCallFree),
                            xrp.getAttributeValue(mIndexofOverIntraFree),
                            xrp.getAttributeValue(mIndexofOverExtraFree),
                            xrp.getAttributeValue(mIndexofOverLocalCallFree));

                    mtelecomPaymentMap.put(TELECOM_LIST_COUNT, payment);
                    TELECOM_LIST_COUNT++;
                    // Log.d("abc","xrp.getColumnNumber() =" +
                }
                eventType = xrp.next();
            }

        } catch (XmlPullParserException e) {

            // TODO: handle exception
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < CHT_PHONE_PREFIX.length; i++)
            mTelecomPhoneNumMap.put(CHT_PHONE_PREFIX[i], CHT);

        for (int i = 0; i < TWM_PHONE_PREFIX.length; i++)
            mTelecomPhoneNumMap.put(TWM_PHONE_PREFIX[i], TWM);

        for (int i = 0; i < FET_PHONE_PREFIX.length; i++)
            mTelecomPhoneNumMap.put(FET_PHONE_PREFIX[i], FET);

        for (int i = 0; i < LOCAL_PHONE_PREFIX.length; i++)
            mTelecomPhoneNumMap.put(LOCAL_PHONE_PREFIX[i], LOCAL);

        for (int i = 0; i < FREE_CALL_PREFIX.length; i++)
            mTelecomPhoneNumMap.put(FREE_CALL_PREFIX[i], FREECALL);

    }

    public class Payment {

        private String mId;
        private String mCorporation;
        private String mPayment;
        private String mExtraFree;
        private String mIntraFree;
        private String mLocalCallFree;
        private String mOverExtraFree;
        private String mOverIntraFree;
        private String mOverLocalCallFree;

        public Payment(String id, String corporation, String payment, String intraFree,
                String extraFree, String localCallFree, String overIntraFree, String OverExtraFree,
                String OverLocalCallFree) {
            mId = id;
            mCorporation = corporation;
            mPayment = payment;
            mIntraFree = intraFree;
            mExtraFree = extraFree;
            mLocalCallFree = localCallFree;
            mOverIntraFree = overIntraFree;
            mOverExtraFree = OverExtraFree;
            mOverLocalCallFree = OverLocalCallFree;
        }
    }

    public String getId(int indexId) {
        return mtelecomPaymentMap.get(indexId).mId;
    }

    public String getPayment(int indexId) {
        return mtelecomPaymentMap.get(indexId).mPayment;
    }

    public String getIntraFree(int indexId) {
        return mtelecomPaymentMap.get(indexId).mIntraFree;
    }

    public String getOutraFree(int indexId) {
        return mtelecomPaymentMap.get(indexId).mExtraFree;
    }

    public String getLocalCallFree(int indexId) {
        return mtelecomPaymentMap.get(indexId).mLocalCallFree;
    }

    public int getTelecomInfoMapCount() {
        return mtelecomPaymentMap.size();
    }

    public String getInOutLocalNework(String number) {
        return mTelecomPhoneNumMap.get(number);

    }

    public String getCorporation(int indexId) {
        // TODO Auto-generated method stub
        return mtelecomPaymentMap.get(indexId).mCorporation;
    }

    public String getOverIntraFree(int indexId) {
        // TODO Auto-generated method stub
        return mtelecomPaymentMap.get(indexId).mOverIntraFree;
    }

    public String getOverExtraFree(int indexId) {
        // TODO Auto-generated method stub
        return mtelecomPaymentMap.get(indexId).mOverExtraFree;
    }

    public String getOverLocalCallFree(int indexId) {
        // TODO Auto-generated method stub
        return mtelecomPaymentMap.get(indexId).mOverLocalCallFree;
    }

}
