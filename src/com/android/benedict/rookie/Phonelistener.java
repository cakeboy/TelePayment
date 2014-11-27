
package com.android.benedict.rookie;

import com.android.benedict.rookie.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;

@SuppressLint("NewApi")
public class Phonelistener extends Service {

    private static ContentResolver mContRes;
    private Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            mContRes = getContentResolver();
            Cursor c = mContRes.query(CallLog.Calls.CONTENT_URI, null, null, null,
                    android.provider.CallLog.Calls.DATE + " DESC");
            String lastCallNumber = "";
            String lastCallDate = "";
            String lastCallDuration = "";
            c.moveToPosition(-1);
            // while(c.moveToFirst()){
            // duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));
            // lastCallNumber =
            // c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));;
            // date = c.getString(c.getColumnIndex(CallLog.Calls.DATE));
            // Log.d("abc","date = "+date+"duration =" + duration +
            // ", lastCallNumber="+lastCallNumber);
            //
            // }
            if (c.moveToFirst()) {
                lastCallDuration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));
                lastCallNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                lastCallDate = c.getString(c.getColumnIndex(CallLog.Calls.DATE));
                // Log.d("abc", "last date = " + lastCallDate + "duration =" +
                // lastCallDuration + ", lastCallNumber="
                // + lastCallNumber);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(Phonelistener.this);
                Intent notifyIntent = new Intent(Phonelistener.this, MyTelecomInfoActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                        notifyIntent, 0);

                builder.setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(lastCallNumber + "Duration: " + lastCallDuration)
                        .setContentText(lastCallNumber + "Duration: " + lastCallDate)
                        .setContentInfo(lastCallNumber).setTicker(lastCallNumber)
                        .setLights(0xFFFFFFFF, 1000, 1000).setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(R.drawable.ic_launcher, notification);

            }
            c.close();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        if (intent.getAction().equals("PhoneListener.Start")) {
            handler.postDelayed(runnable, 1000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("abc", "success");

        super.onDestroy();
    }

}
