package com.example.study_android;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class MyApplication extends Application {
    private static final String TAG = "MARK";

    private static MyApplication mApp;
    public HashMap<String, String> infoMap = new HashMap<>();

    public static MyApplication getInstance() {
        return mApp;
    }

    // App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d(TAG, "onCreate");
    }

    // App终止时调用（不一定）,在应用开发中没什么意义
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    // 配置改变时调用，例如：从竖屏变为横屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }
}
