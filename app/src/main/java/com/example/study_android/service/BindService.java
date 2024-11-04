package com.example.study_android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BindService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    public void test() {
        Log.d("AAAA", "test");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("AAAA", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("AAAA", "onUnbind");
        return super.onUnbind(intent);
    }

    // 链接Service和Activity之间的桥梁，有了它，Activity就可以调用service里面的方法
    public class MyBinder extends Binder {
        private BindService myBinder;

        public MyBinder(BindService bindService) {
            myBinder = bindService;
        }

        public void test() {
            Log.d("AAAA", "MyBinder");
            myBinder.test();
        }
    }
}
