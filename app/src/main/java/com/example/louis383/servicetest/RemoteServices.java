package com.example.louis383.servicetest;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class RemoteServices extends Service {

    private static final String CHANNEL_ID = "sample_remote_notification";
    private static final String CHANNEL_GROUP = "sample_remote_group";
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Channel Remote Service";
            NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(CHANNEL_GROUP, channelName);
            getNotificationManager().createNotificationChannelGroup(notificationChannelGroup);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setGroup(notificationChannelGroup.getId());
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("RemoteServices", "onBind Called.");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("RemoteServices", "onUnbind Called.");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("RemoteServices", "onDestroy Called.");
    }

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public void fireNotification(String message) throws RemoteException {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(RemoteServices.this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Message From Remote");
            builder.setContentText(message);
            builder.setGroup(CHANNEL_GROUP);
            getNotificationManager().notify((int) System.currentTimeMillis(), builder.build());
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {}
    };
}
