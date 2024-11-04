package com.example.study_android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AAAA", "onStartCommand");
        String value = intent.getStringExtra("key_sotp");
        if (TextUtils.equals(value, "stop")) {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AAAA", "onCreate");

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopSelf();
                Log.d("AAAA","stopSelf");
            }
        },5000);
    }

    @Override
    public void onDestroy() {
        Log.d("AAAA", "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("AAAA", "onBind");
        return null;
    }
}
