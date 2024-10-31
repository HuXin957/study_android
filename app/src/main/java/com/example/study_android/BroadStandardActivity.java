package com.example.study_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BroadStandardActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BROAD_ACTION_COME = "COME";
    private Button btn_send;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_standard);

        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(BroadStandardActivity.BROAD_ACTION_COME);
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(BroadStandardActivity.BROAD_ACTION_COME)) {
                    btn_send.setText("来了");
                }
            }
        };
        // 创建一个意图过滤器，只处理 BROAD_ACTION_COME 的广播
        IntentFilter filter = new IntentFilter(BroadStandardActivity.BROAD_ACTION_COME);
        //注册接收器
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}