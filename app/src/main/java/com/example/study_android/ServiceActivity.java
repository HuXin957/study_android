package com.example.study_android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.service.BindService;
import com.example.study_android.service.FrontService;
import com.example.study_android.service.MyService;

public class ServiceActivity extends AppCompatActivity {
    private BindService.MyBinder mBinder = null;

    private ServiceConnection connect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBinder = (BindService.MyBinder) service;
            mBinder.test();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

    }

    // 非绑定式
    public void startService(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    // 绑定式
    public void bindStartService(View view) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, connect, BIND_AUTO_CREATE);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    public void stopBindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("key_stop","stop");
        startService(intent);
    }

    public void handleUnBindService(View view) {
        unbindService(connect);
    }

    public void createFrontService(View view) {
        Intent intent = new Intent(this, FrontService.class);
        startService(intent);
    }


    public void stopFrontService(View view) {
        Intent intent=new Intent(this,FrontService.class);
        intent.putExtra("key_stop","stop_foreground");
        startService(intent);
    }
}