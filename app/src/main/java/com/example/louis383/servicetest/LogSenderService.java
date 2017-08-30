package com.example.louis383.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by louis383 on 2017/8/28.
 */

public class LogSenderService extends IntentService {

    public LogSenderService() {
        super("LogSenderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("ServiceTester", "pid: " + Process.myPid());
        Log.i("ServiceTester", "tid: " + Process.myTid());
        for (int i = 0; i < 10; i++) {
            Log.i("LogSenderService", "Send " + (i + 1) +" Log Message");
        }
    }
}
