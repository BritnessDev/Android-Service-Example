package com.example.louis383.servicetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button coolLogServiceButton, classicServiceButton;
    private Button openBinderServicesButton, messageBinderServiceButton;
    private Button aidlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("ServiceTester", "pid: " + Process.myPid());
        Log.i("ServiceTester", "tid: " + Process.myTid());
        coolLogServiceButton = (Button) findViewById(R.id.start_intent_service);
        coolLogServiceButton.setOnClickListener(this);
        classicServiceButton = (Button) findViewById(R.id.start_classic_service);
        classicServiceButton.setOnClickListener(this);
        openBinderServicesButton = (Button) findViewById(R.id.open_binder_services_activity_btn);
        openBinderServicesButton.setOnClickListener(this);
        messageBinderServiceButton = (Button) findViewById(R.id.message_binder_service_button);
        messageBinderServiceButton.setOnClickListener(this);
        aidlButton = (Button) findViewById(R.id.aidl_button);
        aidlButton.setOnClickListener(this);
    }

    //region View.OnClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_intent_service:
                startService(new Intent(this, LogSenderService.class));
                break;
            case R.id.start_classic_service: {
                Intent intent = new Intent(this, ClassicService.class);
                intent.putExtra("Message", "From outer space.");
                startService(intent);
            }
                break;
            case R.id.open_binder_services_activity_btn: {
                Intent intent = new Intent(this, BinderServiceActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.message_binder_service_button: {
                Intent intent = new Intent(this, MessageBinderActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.aidl_button: {
                Intent intent = new Intent(this, RemoteControlAIDLActivity.class);
                startActivity(intent);
            }
                break;
        }
    }
    //endregion
}
