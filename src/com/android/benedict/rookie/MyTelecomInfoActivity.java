/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class MyTelecomInfoActivity extends FragmentActivity implements ActionBar.TabListener {

    public static final String Out_Going_PageTitle = "OutGoing";
    public static final String In_Coming_PageTitle = "InComing";
    public static final String Missed_Call_PageTitle = "Missed";
    public static final String Telecom_Info_PageTitle = "Information";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the three primary sections of the app. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the
     * app, one at a time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is
        // no hierarchical
        // parent.
        // actionBar.setHomeButtonEnabled(false);
        // actionBar.setDisplayShowCustomEnabled(true);
        // mViewPager.set

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.set

        // Set up the ViewPager, attaching the adapter and setting up a listener
        // for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the
                // corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have
                // a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        // for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
        // Create a tab with text corresponding to the page title defined by the
        // adapter.
        // Also specify this Activity object, which implements the TabListener
        // interface, as the
        // listener for when this tab is selected.
        actionBar.addTab(actionBar.newTab().setText(Telecom_Info_PageTitle)
                .setIcon(android.R.drawable.sym_call_outgoing).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(Out_Going_PageTitle)
                .setIcon(android.R.drawable.sym_call_outgoing).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(In_Coming_PageTitle)
                .setIcon(android.R.drawable.sym_call_incoming).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(Missed_Call_PageTitle)
                .setIcon(android.R.drawable.sym_call_missed).setTabListener(this));
        // }
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the primary sections of the app.
     */

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public static final int callInfoPageNum = 0;
        public static final int outGoingPageNum = 1;
        public static final int inComingPageNum = 2;
        public static final int missedPageNum = 3;
        public static final int numOfFragment = 4;

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
            /*
             * case 0: // The first section of the app is the most interesting
             * -- // it offers // a launchpad into the other demonstrations in
             * this example // application. return new
             * LaunchpadSectionFragment(); default: // The other sections of the
             * app are dummy placeholders. Fragment fragment = new
             * DummySectionFragment(); Bundle args = new Bundle();
             * args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
             * fragment.setArguments(args); return fragment;
             */

                case callInfoPageNum:

                    return new TelecomInfoFragment();

                case outGoingPageNum:

                    return CallLogCursorLoaderFragment.newInstance(CallLog.Calls.OUTGOING_TYPE);

                case inComingPageNum:

                    return CallLogCursorLoaderFragment.newInstance(CallLog.Calls.INCOMING_TYPE);

                case missedPageNum:

                    return CallLogCursorLoaderFragment.newInstance(CallLog.Calls.MISSED_TYPE);

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return numOfFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return " ";
        }
    }
}
