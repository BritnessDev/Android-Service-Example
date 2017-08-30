package com.example.louis383.servicetest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by louis383 on 2017/8/30.
 */

public class BasicBinderService extends Service {

    private static final String CHANNEL_ID = "basic_binder_channel";
    private static final int NOTIFICATION_ID = 1254;

    private NotificationManager notificationManager;
    private IBinder binder = new BasicBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Channel Name";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("BasicBinderService", "onBind Called.");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("BasicBinderService", "onUnbind Called.");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("BasicBinderService", "onDestroy Called.");
    }

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void fireMessage(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Test Notification");
        builder.setContentText(message);
        getNotificationManager().notify(NOTIFICATION_ID, builder.build());
    }

    public class BasicBinder extends Binder {
        BasicBinderService getService() {
            return BasicBinderService.this;
        }
    }
}
