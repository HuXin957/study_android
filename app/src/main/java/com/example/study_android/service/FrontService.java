package com.example.study_android.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.study_android.R;

public class FrontService extends Service {

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "通知", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        // 创建通知
        Notification notification = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.huoxing)
                .setContentTitle("这是通知标题")
                .setContentText("这是通知内容")
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String value = intent.getStringExtra("key_stop");
        if (TextUtils.equals(value, "stop")) {
            stopSelf();
        } else if (TextUtils.equals(value, "stop_foreground")) {
            stopForeground(true);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
