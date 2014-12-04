
package com.android.benedict.rookie;

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

public class StarTelecomInfoSrevice extends Service {

    private static ContentResolver mResolver;
    private Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            mResolver = getContentResolver();
            Cursor cursor = mResolver.query(CallLog.Calls.CONTENT_URI, null, null, null,
                    android.provider.CallLog.Calls.DATE + " DESC");
            String lastCallNumber = "";
            String lastCallDate = "";
            String lastCallDuration = "";

            if (cursor == null)
                return;
            cursor.moveToPosition(-1);

            if (cursor.moveToFirst()) {
                lastCallDuration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                lastCallNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                lastCallDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                // Log.d("abc", "last date = " + lastCallDate + "duration =" +
                // lastCallDuration + ", lastCallNumber="
                // + lastCallNumber);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(StarTelecomInfoSrevice.this);
                Intent notifyIntent = new Intent(StarTelecomInfoSrevice.this, CallLogInfoActivity.class);
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
            cursor.close();
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
        if (intent.getAction().equals("StarTelecomInfoSrevice.Start")) {
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
