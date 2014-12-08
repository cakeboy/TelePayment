
package com.android.benedict.rookie;

import com.android.benedict.rookie.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class CallLogInfoActivity extends FragmentActivity implements ActionBar.TabListener {

    public static final String Out_Going_PageTitle = "OutGoing";
    public static final String In_Coming_PageTitle = "InComing";
    public static final String Missed_Call_PageTitle = "Missed";
    public static final String Telecom_Info_PageTitle = "Information";

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Set up the ViewPager, attaching the adapter and setting up a listener
        // for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(actionBar.newTab().setText(Telecom_Info_PageTitle)
                .setIcon(android.R.drawable.sym_call_outgoing).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(Out_Going_PageTitle)
                .setIcon(android.R.drawable.sym_call_outgoing).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(In_Coming_PageTitle)
                .setIcon(android.R.drawable.sym_call_incoming).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(Missed_Call_PageTitle)
                .setIcon(android.R.drawable.sym_call_missed).setTabListener(this));
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public static final int CALL_INFO_PAGE_NUM = 0;
        public static final int OUT_GOING_PAGE_NUM = 1;
        public static final int IN_COMING_PAGE_NUM = 2;
        public static final int MISS_PAGE_NUM = 3;
        public static final int NUM_OF_FRAGMENT = 4;

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case CALL_INFO_PAGE_NUM:

                    return new TelecomInfoFragment();

                case OUT_GOING_PAGE_NUM:

                    return CallLogFragment.newInstance(CallLog.Calls.OUTGOING_TYPE);

                case IN_COMING_PAGE_NUM:

                    return CallLogFragment.newInstance(CallLog.Calls.INCOMING_TYPE);

                case MISS_PAGE_NUM:

                    return CallLogFragment.newInstance(CallLog.Calls.MISSED_TYPE);

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return NUM_OF_FRAGMENT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
