package com.example.study_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.study_android.util.NetUtil;
import com.example.study_android.util.ToastUtil;

public class NetActivity extends AppCompatActivity {
    private TextView tv_content;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                tv_content.setText(strData);

                ToastUtil.show(NetActivity.this, "请求完成");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        tv_content = findViewById(R.id.tv_content);

    }

    public void handleSend(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetUtil.doGeg();

                Message message = new Message();
                message.what = 0;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        });
        thread.start();
    }
}