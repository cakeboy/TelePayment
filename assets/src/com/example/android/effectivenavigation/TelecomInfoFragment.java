package com.example.android.effectivenavigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TelecomInfoFragment extends Fragment{
    
    

    
    private static int TELECOM_NAME_INDEX = 0;
    private Spinner mSpinner;
    private SimpleAdapter mSimpleAdapter;
    public List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
    private TextView mTelecom;
    private TextView mTelecomCalculate;
    //String[] abc = {"1","2","3","4","5"};
    private String[] mSpinnerArray;
    private String[] mPaymentArray;
    private ArrayList<String[]> mPaymentArrayList ;
    TelecomInfo telecomInfo;
    TelecomPaymentCalculate telecomPaymentCalculate;
    
    //private ArrayAdapter<T>
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Resources res = getResources(); 
        //mPaymentArrayList = new ArrayList<String[]>();
        XmlResourceParser xrp = res.getXml(R.xml.telecom_list);
        try {
            int eventType = xrp.getEventType();
            
            while  (eventType != XmlResourceParser.END_DOCUMENT){
                //xrp.nextTag();
                if  (eventType == XmlResourceParser.START_TAG){
                    Log.d("abc","getAttributeNum = "+xrp.getAttributeCount());
                    String tagname = xrp.getName();
                    if  (tagname.equals( "corperation" )){
                        mPaymentArray = new String[xrp.getAttributeCount()];
                        Log.d("abc","getAttributeNum = "+mPaymentArray.length);
                        for (int i = 0; i < mPaymentArray.length; i++) {
                            mPaymentArray[i] = xrp.getAttributeValue(i);
                        }
                        
                        
                    }
                    //break;
                }
                eventType = xrp.next();
            }
            
        } catch (XmlPullParserException e) {
            
            // TODO: handle exception
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
    }*/
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
        final View rootView = inflater.inflate(R.layout.telecom_spinner, container, false);
        
        
        telecomInfo = new TelecomInfo(this.getActivity());
        mTelecomCalculate = (TextView) rootView.findViewById(R.id.telecom_payment_calculation);
        mTelecom = (TextView) rootView.findViewById(R.id.telecom_payment);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        
        
        ArrayAdapter<String> adapter = this.createArrayAdapter();
        adapter.setDropDownViewResource(
               android.R.layout.simple_spinner_dropdown_item);

        
        SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
        int position = payMentPreferences.getInt("position", 0);
        telecomPaymentCalculate = new TelecomPaymentCalculate(telecomInfo, position, this);
        mSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);
        mSpinner.setAdapter(adapter);
        
        adapter.notifyDataSetChanged();
        mSpinner.setSelection(position);
        
        mTelecomCalculate.setText("INTRAPAY : " + telecomPaymentCalculate.getIntraPay() + "\n"
                + "OUTRAPAY : " + telecomPaymentCalculate.getOutraPay() + "\n"
                + "LOCALCALLPAY :" + telecomPaymentCalculate.getLocalCallPay());
        
   
        //mTelecom.setText(abc[position]);

        /*Resources res = getResources();  
        XmlResourceParser xrp = res.getXml(R.xml.telecom_list);
        
        try {
            while  (xrp.getEventType() != XmlResourceParser.END_DOCUMENT){
                //xrp.next();
                if  (xrp.getEventType() == XmlResourceParser.START_TAG){
                    
                    String tagname = xrp.getName();
                    if  (tagname.equals( "corperation" )){
                        item.put("name", xrp.getAttributeValue(0));
                        item.put("payment", xrp.getAttributeValue(1));
                        item.put("intra", xrp.getAttributeValue(2));
                        item.put("outra", xrp.getAttributeValue(3));
                        
                    }
                    
                }
                xrp.next();
            }
            
        } catch (XmlPullParserException e) {
            
            // TODO: handle exception
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */ 
        
        /*String[] from = {
                "name", "payment", "intra","outra"
        };
        int[] to = {
                R.id.name, R.id.mobile, R.id.date, R.id.outra
        };
        mSimpleAdapter = new SimpleAdapter(getActivity(), items, R.layout.call_log, from, to);
        setListAdapter(mSimpleAdapter);*/
        return rootView;
        
    }
    private ArrayAdapter<String> createArrayAdapter() {
        mSpinnerArray = new String[telecomInfo.getTelecomInfoMapCount()];
        for(int i=0; i<telecomInfo.getTelecomInfoMapCount();i++)
            mSpinnerArray[i] = telecomInfo.getId(i);
        return new ArrayAdapter<String>(this.getActivity(),
               android.R.layout.simple_spinner_item, mSpinnerArray);
    }
    
    private Spinner.OnItemSelectedListener mSpinnerItemSelLis = new Spinner.OnItemSelectedListener(){

       
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            // TODO Auto-generated method stub

            //String selected = parent.getItemAtPosition(position).toString();

            SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
            payMentPreferences.edit().putInt("position", position).commit();
            
            //String abcString = payMentPreferences.getString("paymentresult", selected);

            //Log.d("abc","postionNum = "+postion);
            //Log.d("abc",abcString);
            //mSpinner.setSelection(postion);
            /*HashMap<Integer, String> hMap = new HashMap<Integer, String>();
            hMap.put(position,mArray[position]);
            Resources res = getResources(); 
            //mPaymentArrayList = new ArrayList<String[]>();
            XmlResourceParser xrp = res.getXml(R.xml.telecom_list);
            try {
                int eventType = xrp.getEventType();

                while  (eventType != XmlResourceParser.END_DOCUMENT){
                    //xrp.nextTag();
                    if  (eventType == XmlResourceParser.START_TAG){
                        Log.d("abc","getAttributeNum = "+xrp.getAttributeCount());
                        String tagname = xrp.getName();
                        if  (tagname.equals( hMap.get(position))){
                            mPaymentArray = new String[xrp.getAttributeCount()];
                            Log.d("abc","getAttributeNum = "+mPaymentArray.length);
                            for (int i = 0; i < mPaymentArray.length; i++) {
                                mPaymentArray[i] = xrp.getAttributeValue(i);
                            }
                            
                     
                            break;
                        }
                        

                    }
                    eventType = xrp.next();
                }
                
            } catch (XmlPullParserException e) {
                
                // TODO: handle exception
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

            mTelecom.setText(telecomInfo.getCorporation(position) + "\n"
                    + telecomInfo.getPayment(position) + "\n"
                    + telecomInfo.getIntraNework(position) + "\n"
                    + telecomInfo.getOutraNework(position) + "\n"
                    + telecomInfo.getLocalCall(position));
            
            telecomPaymentCalculate = new TelecomPaymentCalculate(telecomInfo, position, TelecomInfoFragment.this);

            mTelecomCalculate.setText("INTRAPAY : " + telecomPaymentCalculate.getIntraPay() + "\n"
                    + "OUTRAPAY : " + telecomPaymentCalculate.getOutraPay() + "\n"
                    + "LOCALCALLPAY :" + telecomPaymentCalculate.getLocalCallPay());
            
            
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            
        }
        
     };
   

    
    

}
