
package com.example.android.effectivenavigation;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.effectivenavigation.R;
public class callogFragemt extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        String[] arr = new String[]{
           "H","I","J","K","L","M","N","O","P","Q","R","S"
        };
        ArrayAdapter<String> adapter 
           = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,arr);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.callogfragment, container, false);
    }
}