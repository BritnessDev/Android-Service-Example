package com.example.louis383.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by louis383 on 2017/8/30.
 */

public class MessageBinderActivity extends AppCompatActivity implements View.OnClickListener {

    private Messenger serviceMessenger;
    private boolean isBound;

    private EditText messageEditText;
    private Button fireButton;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessageBinderService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_service);
        messageEditText = (EditText) findViewById(R.id.fired_edit_text);
        fireButton = (Button) findViewById(R.id.fire_button);
        fireButton.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    //region OnClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fire_button: {
                if (isBound) {
                    Message message = Message.obtain(null, MessageBinderService.PUSH_NOTIFICATION);
                    String fireMessage = messageEditText.getText().toString().trim();
                    Bundle bundle = new Bundle();
                    bundle.putString(MessageBinderService.NOTIFICATION_STRING, fireMessage);
                    message.setData(bundle);

                    try {
                        serviceMessenger.send(message);
                    } catch (RemoteException e) {
                        Log.e("MessageBinderActivity", Log.getStackTraceString(e));
                    }
                }
            }
                break;
        }
    }
    //endregion

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceMessenger = new Messenger(iBinder);
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };
}
