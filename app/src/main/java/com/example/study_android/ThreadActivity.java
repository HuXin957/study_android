package com.example.study_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.study_android.util.ToastUtil;

public class ThreadActivity extends AppCompatActivity {
    private TextView tv_content;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String strData = (String) msg.obj;
                tv_content.setText(strData);
                ToastUtil.show(ThreadActivity.this,"任务完成");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thead);
        tv_content = findViewById(R.id.tv_content);

    }

    private String getStringForNet() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append("字符串" + i);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void handleStart(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = getStringForNet();

                Message message = new Message();
                message.what = 0;
                message.obj = str;
                mHandler.sendMessage(message);
            }
        });

        thread.start();
    }

    public void handleTest(View view) {
        ToastUtil.show(this,"hello,hello");
    }
}