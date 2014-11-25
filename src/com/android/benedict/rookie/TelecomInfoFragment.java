
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.android.effectivenavigation.R;

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

public class TelecomInfoFragment extends Fragment {

    private static int TELECOM_NAME_INDEX = 0;
    private Spinner mSpinner;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private TextView mTelecom;
    private TextView mTelecomCalculate;
    private String[] mSpinnerArray;
    TelecomInfo telecomInfo;
    TelecomPaymentCalculate telecomPaymentCalculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        final View rootView = inflater.inflate(R.layout.telecom_spinner, container, false);

        telecomInfo = new TelecomInfo(this.getActivity());
        
        mTelecomCalculate = (TextView) rootView.findViewById(R.id.telecom_payment_calculation);
        mTelecom = (TextView) rootView.findViewById(R.id.telecom_payment);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = this.createArrayAdapter();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
        
        int position = payMentPreferences.getInt("position", 0);
        
        telecomPaymentCalculate = new TelecomPaymentCalculate(telecomInfo, position, this);
        mSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);
        mSpinner.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        mSpinner.setSelection(position);

        mTelecomCalculate.setText("INTRAPAY : " + telecomPaymentCalculate.getIntraPay() + "\n"
                + "OUTRAPAY : " + telecomPaymentCalculate.getOutraPay() + "\n" + "LOCALCALLPAY :"
                + telecomPaymentCalculate.getLocalCallPay());

        return rootView;

    }

    private ArrayAdapter<String> createArrayAdapter() {
        mSpinnerArray = new String[telecomInfo.getTelecomInfoMapCount()];
        for (int i = 0; i < telecomInfo.getTelecomInfoMapCount(); i++)
            mSpinnerArray[i] = telecomInfo.getId(i);
        return new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,
                mSpinnerArray);
    }

    private Spinner.OnItemSelectedListener mSpinnerItemSelLis = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            // TODO Auto-generated method stub

            SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
            payMentPreferences.edit().putInt("position", position).commit();

            mTelecom.setText(telecomInfo.getCorporation(position) + "\n"
                    + telecomInfo.getPayment(position) + "\n"
                    + telecomInfo.getIntraNework(position) + "\n"
                    + telecomInfo.getOutraNework(position) + "\n"
                    + telecomInfo.getLocalCall(position));

            telecomPaymentCalculate = new TelecomPaymentCalculate(telecomInfo, position,
                    TelecomInfoFragment.this);

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
