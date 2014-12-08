
package com.android.benedict.rookie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReciever extends BroadcastReceiver {

    StartTelecomInfoSrevice startTelecomInfoSrevice;

    public static final String MY_START_SERVICE = "StartTelecomInfoSrevice.Start";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("abc",
                "TelephonyManager State1 = " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_IDLE)
                && !intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        intent.getStringExtra(TelephonyManager.EXTRA_STATE_RINGING))) {

            Intent it = new Intent(context, StartTelecomInfoSrevice.class);
            it.setAction(MY_START_SERVICE);
            context.startService(it);
        }

        Log.d("abc",
                "TelephonyManager Stat2 = " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));
    }
}
