
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TelecomInfoFragment extends Fragment {

    private static int TELECOM_NAME_INDEX = 0;
    private Spinner mSpinner;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private TextView mTelecom;
    private TextView mTelecomCalculate;
    private String[] mSpinnerArray;
    private Button mBtnToAddNpNumActivity;
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
        mBtnToAddNpNumActivity = (Button) rootView.findViewById(R.id.btn_to_AddNpNumActivity);

        mBtnToAddNpNumActivity.setOnClickListener(btnAddIntraNumberOnClickLis);
        mSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);

        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("abc", "TelecomInfo onResume");

        ArrayAdapter<String> mTelecomPaymentAdapter = createArrayAdapter();
        mTelecomPaymentAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mTelecomPaymentAdapter);

        SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
        int position = payMentPreferences.getInt("position", 0);
        mSpinner.setSelection(position);
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

            Log.d("abc", "onItemSelectd");
            SharedPreferences payMentPreferences = getActivity().getSharedPreferences("payment", 0);
            payMentPreferences.edit().putInt("position", position).commit();

            mTelecom.setText(telecomInfo.getCorporation(position) + "\n"
                    + telecomInfo.getPayment(position) + "\n" + telecomInfo.getIntraFree(position)
                    + "Min" + "\n" + telecomInfo.getOutraFree(position) + "Min" + "\n"
                    + telecomInfo.getLocalCallFree(position) + "Min" + "\n"
                    + telecomInfo.getOverIntraFree(position) + "second" + "\n"
                    + telecomInfo.getOverExtraFree(position) + "second" + "\n"
                    + telecomInfo.getOverLocalCallFree(position) + "second");

            telecomPaymentCalculate = new TelecomPaymentCalculate(telecomInfo, position,
                    TelecomInfoFragment.this);

            mTelecomCalculate
                    .setText("INTRAPAY : "
                            + String.format("%.2f",
                                    Float.parseFloat(telecomPaymentCalculate.getIntraPay())) + "\n"
                            + "OUTRAPAY : " + telecomPaymentCalculate.getOutraPay() + "\n"
                            + "LOCALCALLPAY :" + telecomPaymentCalculate.getLocalCallPay());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    };

    private Button.OnClickListener btnAddIntraNumberOnClickLis = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            intent.setClass(getActivity(), AddNpNumberActivity.class);
            startActivity(intent);
        }

    };

}
