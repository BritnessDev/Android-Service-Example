package com.example.louis383.servicetest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by louis383 on 2017/8/30.
 */

public class MessageBinderService extends Service implements Handler.Callback {

    public static final int PUSH_NOTIFICATION = 0;
    public static final String NOTIFICATION_STRING = "NotificationString";

    private static final String CHANNEL_ID = "MessageChannel";
    private static final int NOTIFICATION_ID = 736;

    private NotificationManager notificationManager;
    private final Messenger messenger = new Messenger(new Handler(this));

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "MessageBindingChannel";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MessageBinderService", "onBind Called.");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MessageBinderService", "onUnbind Called.");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("MessageBinderService", "onDestroy Called.");
        super.onDestroy();
    }

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    //region Handler Callback
    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case PUSH_NOTIFICATION: {
                if (message.getData() != null) {
                    String firedMessage = message.getData().getString(NOTIFICATION_STRING, "");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("Message Service Title");
                    builder.setContentText(firedMessage);
                    getNotificationManager().notify(NOTIFICATION_ID, builder.build());
                }
            }
                break;
        }
        return false;
    }
    //endregion
}
