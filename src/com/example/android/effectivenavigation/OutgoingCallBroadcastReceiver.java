
package com.example.android.effectivenavigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OutgoingCallBroadcastReceiver extends BroadcastReceiver {

    Phonelistener phonelistener;

    public static final String MY_START_SERVICE = "PhoneListener.Start";

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("abc",
                "TelephonyManager State1 = " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_IDLE)
                && !intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        intent.getStringExtra(TelephonyManager.EXTRA_STATE_RINGING))) {

            Intent it = new Intent(context, Phonelistener.class);
            it.setAction(MY_START_SERVICE);
            context.startService(it);
        }

        Log.d("abc",
                "TelephonyManager Stat2 = " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));
    }

}
