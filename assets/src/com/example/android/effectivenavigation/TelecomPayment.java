package com.example.android.effectivenavigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class TelecomPayment extends ListFragment {
    
    public List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
    private SimpleAdapter mSimpleAdapter;
    

    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        Resources res = getResources();  
        XmlResourceParser xrp = res.getXml(R.xml.telecom_list);
        
        try {
            while  (xrp.getEventType() != XmlResourceParser.END_DOCUMENT){
                //xrp.next();
                if  (xrp.getEventType() == XmlResourceParser.START_TAG){
                    
                    String tagname = xrp.getName();
                    if  (tagname.equals( "corperation" )){
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("name", xrp.getAttributeValue(0));
                        item.put("payment", xrp.getAttributeValue(1));
                        item.put("intra", xrp.getAttributeValue(2));
                        item.put("outra", xrp.getAttributeValue(3));
                        items.add(item);
                    }
                    
                }
                xrp.next();
            }
            
        } catch (XmlPullParserException e) {
            
            // TODO: handle exception
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
        String[] from = {
                "name", "payment", "intra","outra"
        };
        int[] to = {
                R.id.name, R.id.mobile, R.id.date, R.id.outra
        };
        mSimpleAdapter = new SimpleAdapter(getActivity(), items, R.layout.call_log, from, to);
        setListAdapter(mSimpleAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.telecom_payment, container, false);
    }
    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Spinner spinner = (Spinner) this.getActivity().findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = this.createArrayAdapter();
        adapter.setDropDownViewResource(
               android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    
    private ArrayAdapter<String> createArrayAdapter() {
        String[] array = new String[] {
                "Â§«ô¤@", "Â§«ô¤G", "Â§«ô¤T", "Â§«ô¥|", "Â§«ô¤­"
        };
        return new ArrayAdapter<String>(this.getActivity(),
               android.R.layout.simple_spinner_item, array);
    }

}
