
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TelecomInfoFragment extends Fragment {

    private Spinner mSpinner;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private TextView mTelecomCorporation;
    private TextView mTelecomPayment;
    private TextView mTelecomIntraFree;
    private TextView mTelecomExtraFree;
    private TextView mTelecomLocalcallFree;
    private TextView mTelecomIntra;
    private TextView mTelecomExtra;
    private TextView mTelecomLocalcall;
    private TextView mTelecomIntraRemains;
    private TextView mTelecomExtraRemains;
    private TextView mTelecomLocalcallRemains;
    private TextView mTelecomIntraPay;
    private TextView mTelecomExtraPay;
    private TextView mTelecomLocalcallPay;
    private TextView mShowDateStart;
    private TextView mShowDateEnd;
    private LinearLayout mQueryDateStart;
    private LinearLayout mQueryDateEnd;
    private Button mBtnToAddNpNumActivity;
    private String[] mTelecomPaymentSpinnerArray;

    private static final String TELECOM_PAYMENT = "payment";
    private static final String SPINNER_SELECT_POSITION = "position";
    private static final String QUERY_DATE_START = "queryDateStart";
    private static final String QUERY_DATE_END = "queryDateEnd";
    private static final String START_YEAR = "startyear";
    private static final String START_Month = "startmonth";
    private static final String START_Day = "startday";
    private static final String END_YEAR = "endyear";
    private static final String END_MONTH = "endmonth";
    private static final String END_DAY = "endday";

    TelecomInfo telecomInfo;
    ComputeTelecomFare telecomPaymentCalculate;
    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final View rootView = inflater.inflate(R.layout.telecom_spinner, container, false);

        telecomInfo = new TelecomInfo(this.getActivity());
        calendar = Calendar.getInstance();
        setComponentView(rootView);

        SharedPreferences queryDateStartPreferences = getActivity().getSharedPreferences(
                QUERY_DATE_START, 0);
        int startYear = queryDateStartPreferences.getInt(START_YEAR, calendar.get(Calendar.YEAR));
        int startMonth = queryDateStartPreferences
                .getInt(START_Month, calendar.get(Calendar.MONTH)) + 1;
        int startDay = queryDateStartPreferences.getInt(START_Day,
                calendar.get(Calendar.DAY_OF_MONTH));
        mShowDateStart.setText(Integer.toString(startYear) + "/" + Integer.toString(startMonth)
                + "/" + Integer.toString(startDay));

        SharedPreferences queryDateEndPreferences = getActivity().getSharedPreferences(
                QUERY_DATE_END, 0);
        int endYear = queryDateEndPreferences.getInt(END_YEAR, calendar.get(Calendar.YEAR));
        int endMonth = queryDateEndPreferences.getInt(END_MONTH, calendar.get(Calendar.MONTH)) + 1;
        int endDay = queryDateEndPreferences.getInt(END_DAY, calendar.get(Calendar.DAY_OF_MONTH));

        mShowDateEnd.setText(Integer.toString(endYear) + "/" + Integer.toString(endMonth) + "/"
                + Integer.toString(endDay));

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

        SharedPreferences payMentPreferences = getActivity().getSharedPreferences(TELECOM_PAYMENT,
                0);
        int position = payMentPreferences.getInt(SPINNER_SELECT_POSITION, 0);

        mSpinner.setSelection(position);
    }

    private ArrayAdapter<String> createArrayAdapter() {
        mTelecomPaymentSpinnerArray = new String[telecomInfo.getTelecomInfoMapCount()];
        for (int i = 0; i < telecomInfo.getTelecomInfoMapCount(); i++)
            mTelecomPaymentSpinnerArray[i] = telecomInfo.getId(i);
        return new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,
                mTelecomPaymentSpinnerArray);
    }

    private void setComponentView(View rootView) {
        mTelecomCorporation = (TextView) rootView.findViewById(R.id.telecom_corporation);
        mTelecomPayment = (TextView) rootView.findViewById(R.id.telecom_payment);
        mTelecomIntraFree = (TextView) rootView.findViewById(R.id.telecom_intra_free);
        mTelecomExtraFree = (TextView) rootView.findViewById(R.id.telecom_extra_free);
        mTelecomLocalcallFree = (TextView) rootView.findViewById(R.id.telecom_localcall_free);
        mTelecomIntra = (TextView) rootView.findViewById(R.id.telecom_intra);
        mTelecomExtra = (TextView) rootView.findViewById(R.id.telecom_extra);
        mTelecomLocalcall = (TextView) rootView.findViewById(R.id.telecom_localcall);
        mTelecomIntraRemains = (TextView) rootView.findViewById(R.id.telecom_intra_remain);
        mTelecomExtraRemains = (TextView) rootView.findViewById(R.id.telecom_extra_remain);
        mTelecomLocalcallRemains = (TextView) rootView.findViewById(R.id.telecom_localcall_remain);
        mTelecomIntraPay = (TextView) rootView.findViewById(R.id.telecom_intra_pay);
        mTelecomExtraPay = (TextView) rootView.findViewById(R.id.telecom_extra_pay);
        mTelecomLocalcallPay = (TextView) rootView.findViewById(R.id.telecom_localcall_pay);
        mShowDateStart = (TextView) rootView.findViewById(R.id.textview_date_from);
        mShowDateEnd = (TextView) rootView.findViewById(R.id.textview_date_to);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mQueryDateStart = (LinearLayout) rootView.findViewById(R.id.query_date_from);
        mQueryDateEnd = (LinearLayout) rootView.findViewById(R.id.query_date_to);
        mBtnToAddNpNumActivity = (Button) rootView.findViewById(R.id.btn_to_AddNpNumActivity);

        mQueryDateStart.setOnClickListener(queryDateStartOnClickListener);
        mQueryDateEnd.setOnClickListener(queryDateEndOnClickListener);

        mBtnToAddNpNumActivity.setOnClickListener(btnAddIntraNumberOnClickLis);

        mSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);
    }

    private void setViewText(int position) {
        telecomPaymentCalculate = new ComputeTelecomFare(telecomInfo, position,
                TelecomInfoFragment.this);

        mTelecomCorporation.setText(telecomInfo.getCorporation(position));
        mTelecomPayment.setText(telecomInfo.getPayment(position));
        mTelecomIntraFree.setText(telecomInfo.getIntraFree(position));
        mTelecomExtraFree.setText(telecomInfo.getOutraFree(position));
        mTelecomLocalcallFree.setText(telecomInfo.getLocalCallFree(position));
        mTelecomIntra.setText(telecomInfo.getOverIntraFree(position));
        mTelecomExtra.setText(telecomInfo.getOverExtraFree(position));
        mTelecomLocalcall.setText(telecomInfo.getOverLocalCallFree(position));
        mTelecomIntraRemains.setText(telecomPaymentCalculate.getIntraPayRemain());
        mTelecomExtraRemains.setText(telecomPaymentCalculate.getOutraPayRemain());
        mTelecomLocalcallRemains.setText(telecomPaymentCalculate.getLocalCallPayRemain());
        mTelecomIntraPay.setText("$" + telecomPaymentCalculate.getIntraPay());
        mTelecomExtraPay.setText("$" + telecomPaymentCalculate.getOutraPay());
        mTelecomLocalcallPay.setText("$" + telecomPaymentCalculate.getLocalCallPay());
    }

    private Spinner.OnItemSelectedListener mSpinnerItemSelLis = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            // TODO Auto-generated method stub

            Log.d("abc", "onItemSelectd");
            SharedPreferences payMentPreferences = getActivity().getSharedPreferences(
                    TELECOM_PAYMENT, 0);
            payMentPreferences.edit().putInt(SPINNER_SELECT_POSITION, position).commit();

            setViewText(position);
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
            intent.setClass(getActivity(), EditNpNumberActivity.class);
            startActivity(intent);
        }
    };

    private LinearLayout.OnClickListener queryDateStartOnClickListener = new LinearLayout.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            SharedPreferences queryDateStartPreferences = getActivity().getSharedPreferences(
                    QUERY_DATE_START, 0);
            int startYear = queryDateStartPreferences.getInt(START_YEAR,
                    calendar.get(Calendar.YEAR));
            int startMonth = queryDateStartPreferences.getInt(START_Month,
                    calendar.get(Calendar.MONTH));
            int startDay = queryDateStartPreferences.getInt(START_Day,
                    calendar.get(Calendar.DAY_OF_MONTH));

            // mBtnToSelectDate.setText(Integer.toString(year) +
            // Integer.toString(month) + Integer.toString(day));

            DatePickerDialog datePicDlg = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                int dayOfMonth) {
                            // TODO Auto-generated method stub
                            SharedPreferences queryDateStartPreferences = getActivity()
                                    .getSharedPreferences(QUERY_DATE_START, 0);
                            queryDateStartPreferences.edit().putInt(START_YEAR, year).commit();
                            queryDateStartPreferences.edit().putInt(START_Month, monthOfYear)
                                    .commit();
                            queryDateStartPreferences.edit().putInt(START_Day, dayOfMonth).commit();

                            mShowDateStart.setText(Integer.toString(year) + "/"
                                    + Integer.toString(monthOfYear + 1) + "/"
                                    + Integer.toString(dayOfMonth));

                            SharedPreferences payMentPreferences = getActivity()
                                    .getSharedPreferences(TELECOM_PAYMENT, 0);
                            int position = payMentPreferences.getInt(SPINNER_SELECT_POSITION, 0);

                            setViewText(position);
                        }
                    }, startYear, startMonth, startDay);
            datePicDlg.setCancelable(false);
            datePicDlg.show();

        }
    };

    private LinearLayout.OnClickListener queryDateEndOnClickListener = new LinearLayout.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            // mBtnToSelectDate.setText(Integer.toString(year) +
            // Integer.toString(month) + Integer.toString(day));
            SharedPreferences queryDateEndPreferences = getActivity().getSharedPreferences(
                    QUERY_DATE_END, 0);
            int endYear = queryDateEndPreferences.getInt(END_YEAR, calendar.get(Calendar.YEAR));
            int endMonth = queryDateEndPreferences.getInt(END_MONTH, calendar.get(Calendar.MONTH));
            int endDay = queryDateEndPreferences.getInt(END_DAY,
                    calendar.get(Calendar.DAY_OF_MONTH));

            DatePickerDialog datePicDlg = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                int dayOfMonth) {
                            // TODO Auto-generated method stub
                            SharedPreferences queryDateEndPreferences = getActivity()
                                    .getSharedPreferences(QUERY_DATE_END, 0);
                            queryDateEndPreferences.edit().putInt(END_YEAR, year).commit();
                            queryDateEndPreferences.edit().putInt(END_MONTH, monthOfYear).commit();
                            queryDateEndPreferences.edit().putInt(END_DAY, dayOfMonth).commit();

                            mShowDateEnd.setText(Integer.toString(year) + "/"
                                    + Integer.toString(monthOfYear + 1) + "/"
                                    + Integer.toString(dayOfMonth));

                            SharedPreferences payMentPreferences = getActivity()
                                    .getSharedPreferences(TELECOM_PAYMENT, 0);
                            int position = payMentPreferences.getInt(SPINNER_SELECT_POSITION, 0);

                            setViewText(position);
                        }
                    }, endYear, endMonth, endDay);
            datePicDlg.setCancelable(false);
            datePicDlg.show();
        }
    };
}
