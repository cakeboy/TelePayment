
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class EditNpNumberActivity extends ListActivity {

    private final String NP_NUM_TYPE_INTRA = "IntraNetwork";
    private static final String NP_NUM_TYPE_EXTRA = "ExtraNetwork";
    private static final String NP_NUM_TYPE_HOTLINE = "HotLine";
    private Button mBtnAddNewIntraNum;
    private EditText mEdtNpNum;
    private int mNpNumCorpSpinnerPosition;
    private static ContentResolver mContentResolver;
    private Spinner mNpNumCorpSpinner;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private String[] mNpNumCorpSpinnerArray = {
            NP_NUM_TYPE_INTRA, NP_NUM_TYPE_EXTRA, NP_NUM_TYPE_HOTLINE
    };

    ListView addNpNumList;
    ListAdapter adapter;
    Cursor cursor;
    TelecomInfo telecomInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_np_number);

        telecomInfo = new TelecomInfo(this);

        setComponentView();

        mContentResolver = getContentResolver();

        ArrayAdapter<String> adapter = createArrayAdapter();
        setAdapter();
        mNpNumCorpSpinner.setAdapter(adapter);

    }

    private void setComponentView() {
        mEdtNpNum = (EditText) findViewById(R.id.edt_np_number);
        mBtnAddNewIntraNum = (Button) findViewById(R.id.btn_add_intra_number);
        mBtnAddNewIntraNum.setOnClickListener(BtnAddNewIntraNumOnClickLis);
        mNpNumCorpSpinner = (Spinner) findViewById(R.id.npNumCorpspinner);
        mNpNumCorpSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);
        addNpNumList = getListView();
        addNpNumList.setOnItemClickListener(ClickItemListener);
    }

    private Button.OnClickListener BtnAddNewIntraNumOnClickLis = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            SharedPreferences payMentPreferences = getSharedPreferences("telecomcorp", 0);
            mNpNumCorpSpinnerPosition = payMentPreferences.getInt("position", 0);

            Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;

            cursor = getContentResolver().query(uri, null, null, null, null);

            Log.d("abc", "CURSOR NUM =" + cursor.getCount());

            if (cursor == null)
                return;

            if (cursor.getCount() == 0) {
                ContentValues newNpNum = new ContentValues();
                if (!mEdtNpNum.getText().toString().isEmpty()) {
                    newNpNum.put("number", mEdtNpNum.getText().toString());
                    newNpNum.put("coporation", mNpNumCorpSpinnerArray[mNpNumCorpSpinnerPosition]);
                    mContentResolver.insert(NpPhoneNumberContentProvider.CONTENT_URI, newNpNum);
                    updateAdapter();
                }
            }
            if (cursor.getCount() != 0) {
                boolean haveTheSameData = false;

                String insertNum = mEdtNpNum.getText().toString();
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    String insertedNum = cursor.getString(1);
                    if (insertNum.equals(insertedNum)) {
                        haveTheSameData = true;
                        break;
                    }
                }
                if (!haveTheSameData) {
                    if (!mEdtNpNum.getText().toString().isEmpty()) {
                        ContentValues newNpNum = new ContentValues();
                        newNpNum.put("number", mEdtNpNum.getText().toString());
                        newNpNum.put("coporation",
                                mNpNumCorpSpinnerArray[mNpNumCorpSpinnerPosition]);
                        mContentResolver.insert(NpPhoneNumberContentProvider.CONTENT_URI, newNpNum);
                        updateAdapter();
                    }
                }
            }
        }
    };

    AdapterView.OnItemClickListener ClickItemListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("abc", "...............string==" + cursor.getString(0));
            myDialog();
        }
    };

    private Spinner.OnItemSelectedListener mSpinnerItemSelLis = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            // TODO Auto-generated method stub

            SharedPreferences payMentPreferences = getSharedPreferences("telecomcorp", 0);
            payMentPreferences.edit().putInt("position", position).commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    };

    public void setAdapter() {
        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;
        // Log.i( "abc" , ".................uri.........." +uri);

        cursor = getContentResolver().query(uri, null, null, null, null);
        // cursor.moveToFirst();
        adapter = new SimpleCursorAdapter(this, R.layout.eidt_npnumber_activity, cursor,
                new String[] {
                        "number", "coporation"
                }, new int[] {
                        R.id.npNum, R.id.npNumCorporation
                }, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
    }

    public void updateAdapter() {
        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI;
        // Log.i( "abc" , ".................uri.........." +uri);
        cursor = getContentResolver().query(uri, null, null, null, null);
        ((SimpleCursorAdapter) adapter).changeCursor(cursor);
    }

    private ArrayAdapter<String> createArrayAdapter() {

        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                mNpNumCorpSpinnerArray);
    }

    private void myDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_confirm))
                .setPositiveButton(getResources().getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse(NpPhoneNumberContentProvider.CONTENT_URI + "/"
                                        + cursor.getString(0));
                                getContentResolver().delete(uri, null, null);
                                updateAdapter();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.dialog_cancel),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
        AlertDialog ad = builder.create();
        ad.show();
    }
}
