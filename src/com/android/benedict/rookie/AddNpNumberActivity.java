
package com.android.benedict.rookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
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

public class AddNpNumberActivity extends ListActivity {
    
    private Button mBtnAddNewIntraNum;
    private EditText mEdtNpNum;
    private int mNpNumCorpSpinnerPosition;
    private static ContentResolver mContentResolver;
    private Spinner mNpNumCorpSpinner;
    public List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private String[] mNpNumCorpSpinnerArray = {"IntraNetwork","ExtraNetwork","Others"};
    
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
        mEdtNpNum = (EditText) findViewById(R.id.edt_np_number);
        mBtnAddNewIntraNum = (Button) findViewById(R.id.btn_add_intra_number);
        mBtnAddNewIntraNum.setOnClickListener(BtnAddNewIntraNumOnClickLis);
        mContentResolver = getContentResolver();
        setAdapter();
        addNpNumList = getListView();
        addNpNumList.setOnItemLongClickListener(longClickItemListener);
        mNpNumCorpSpinner = (Spinner) findViewById(R.id.npNumCorpspinner);
        ArrayAdapter<String> adapter = createArrayAdapter();
        
        
        
        mNpNumCorpSpinner.setOnItemSelectedListener(mSpinnerItemSelLis);
        mNpNumCorpSpinner.setAdapter(adapter);
        
    }

    private Button.OnClickListener BtnAddNewIntraNumOnClickLis = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            SharedPreferences payMentPreferences = getSharedPreferences("telecomcorp", 0);

            mNpNumCorpSpinnerPosition = payMentPreferences.getInt("position", 0);
            
            ContentValues newNpNum = new ContentValues();
            if(!mEdtNpNum.getText().toString().equals("")){
            newNpNum.put("number", mEdtNpNum.getText().toString());
            newNpNum.put("coporation",mNpNumCorpSpinnerArray[mNpNumCorpSpinnerPosition] );
            mContentResolver.insert(NpPhoneNumberContentProvider.CONTENT_URI, newNpNum);
            setAdapter();
            //madapter.notifyDataSetChanged();
            }

        }
    };
    
    AdapterView.OnItemLongClickListener longClickItemListener =  new  AdapterView.OnItemLongClickListener() {
        public  boolean  onItemLongClick(AdapterView<?> parent, View view,  
                int  position,  long  id){
            Log.d( "abc" , "...............string==" +cursor.getString( 0 ));
            Uri uri = Uri.parse(NpPhoneNumberContentProvider.CONTENT_URI+ "/"+ cursor.getString(0));                                     
            getContentResolver().delete(uri,  null ,  null );                                             
           // getContentResolver().update(uri, null, null,null);   
            setAdapter();  
                    return false;
            
            
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
        Uri uri = NpPhoneNumberContentProvider.CONTENT_URI; //getIntent().getData();  
        //Log.i( "abc" , ".................uri.........." +uri);  
          
        cursor = getContentResolver().query(uri,  null ,  null ,  null ,  null );  
        //cursor.moveToFirst();  
        adapter =  new  SimpleCursorAdapter(this ,R.layout.add_delete_np_num, cursor,  
                new  String[] {"number","coporation"},   
                new  int [] {R.id.npNum,R.id.npNumCorporation},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);  
        setListAdapter(adapter);  
    }
    private ArrayAdapter<String> createArrayAdapter(){
        
        
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                mNpNumCorpSpinnerArray);
    }
}
