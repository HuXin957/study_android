package com.example.study_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.receiver.AlarmReceiver;

public class SystemMinuteActivity extends AppCompatActivity implements View.OnClickListener {
    BroadcastReceiver receiver;
    BroadcastReceiver receiverNet;
    TextView tv_tip;
    Button btn_alarm;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_minute);

        tv_tip = findViewById(R.id.tv_tip);
        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(this);
    }

    private void timeTickReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    tv_tip.setText("2");
                }
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, filter);
    }

    private void netWorkReceiver() {
        receiverNet = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    NetworkInfo networkInfo = intent.getParcelableExtra("networkInfo");
                    String text = String.format(
                            "网络大类为%s，网络小类为%s，网络状态为%s",
                            networkInfo.getTypeName(),
                            networkInfo.getSubtypeName(),
                            networkInfo.getState().toString());

                    tv_tip.setText(text);
                }
            }
        };
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiverNet, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // timeTickReceiver();
        // netWorkReceiver();

        // 上下文传 getApplicationContext() ，而非 this（当前Activity）,为了避免内存泄漏
        alarmReceiver = new AlarmReceiver(getApplicationContext());
        IntentFilter filter = new IntentFilter(AlarmReceiver.ALARM_ACTION);
        registerReceiver(alarmReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregisterReceiver(receiver);
        // unregisterReceiver(receiverNet);
        unregisterReceiver(alarmReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alarm:
                alarmReceiver.sendAlarm();
                break;
        }
    }
}