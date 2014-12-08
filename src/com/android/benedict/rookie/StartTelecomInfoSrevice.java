
package com.android.benedict.rookie;

import java.text.SimpleDateFormat;

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

public class StartTelecomInfoSrevice extends Service {

    private static ContentResolver mResolver;
    private Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @SuppressLint({
                "NewApi", "SimpleDateFormat"
        })
        public void run() {
            // TODO Auto-generated method stub
            mResolver = getContentResolver();
            Cursor cursor = mResolver.query(CallLog.Calls.CONTENT_URI, null, null, null,
                    android.provider.CallLog.Calls.DATE + " DESC");
            String lastCallNumber = "";
            String lastCallDuration = "";

            if (cursor == null)
                return;
            cursor.moveToPosition(-1);

            if (cursor.moveToFirst()) {
                lastCallDuration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                lastCallNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                long dateTimeMillis = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = format.format(dateTimeMillis);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(
                        StartTelecomInfoSrevice.this);
                Intent notifyIntent = new Intent(StartTelecomInfoSrevice.this,
                        CallLogInfoActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                        notifyIntent, 0);

                builder.setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(
                                lastCallNumber + " "
                                        + getResources().getString(R.string.notify_text_duration)
                                        + ":" + lastCallDuration)
                        .setContentText(
                                getResources().getString(R.string.notify_text_date) + ":"
                                        + dateString).setContentInfo(lastCallNumber)
                        .setTicker(lastCallNumber).setLights(0xFFFFFFFF, 1000, 1000)
                        .setContentIntent(pendingIntent).setAutoCancel(true);
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
            handler.postDelayed(runnable, 500);
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
