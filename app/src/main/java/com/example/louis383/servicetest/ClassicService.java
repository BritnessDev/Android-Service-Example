package com.example.louis383.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by louis383 on 2017/8/28.
 */

public class ClassicService extends Service implements Handler.Callback {
    private static final int SIMPLE_TASK = 0;
    private Looper threadLooper;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread handlerThread = new HandlerThread("Classic Services", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();

        threadLooper = handlerThread.getLooper();
        handler = new Handler(threadLooper, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ClassicService", "onStartCommand Called.");
        Log.i("ServiceTester", "pid: " + Process.myPid());
        Log.i("ServiceTester", "tid: " + Process.myTid());
        String logMessage = "Default Message";
        if (intent != null) {
            logMessage = intent.getStringExtra("Message");
        }

        Message message = new Message();
        message.what = SIMPLE_TASK;
        message.arg1 = startId;
        Bundle bundle = new Bundle();
        bundle.putString("Say", logMessage);
        message.setData(bundle);

        handler.sendMessage(message);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("ClassicService", "onDestroy");
        if (threadLooper != null) {
            threadLooper.quit();
        }

        super.onDestroy();
    }

    //region Handler.Callback
    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case SIMPLE_TASK:
                if (message.getData() != null) {
                    Log.i("ClassicService", "Say: " + message.getData().getString("Say"));
                }

                stopSelf(message.arg1);
                break;
        }
        return false;
    }
    //endregion
}
