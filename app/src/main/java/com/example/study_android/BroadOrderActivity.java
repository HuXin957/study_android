package com.example.study_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ORDER_ACTION = "ORDER_ACTION";
    private Button btn_send_order;
    BroadcastReceiver receiverA;
    BroadcastReceiver receiverB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_order);

        btn_send_order = findViewById(R.id.btn_send_order);
        btn_send_order.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        * 多个接收器处理有序广播的规则：
        * 1、优先级越大的接收器，越早收到有序广播
        * 2、优先级相同的时候，越早注册的接收器越早收到有序广播
        * */

        /*
        * 创建接收器A
        * */
        receiverA = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(ORDER_ACTION)) {
                    btn_send_order.setText("来了A");
                }
            }
        };
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);
        filterA.setPriority(8);
        registerReceiver(receiverA, filterA);


        /*
         * 创建接收器B
         * */
        receiverB = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(ORDER_ACTION)) {
                    btn_send_order.setText("来了");
                }
            }
        };

        IntentFilter filterB = new IntentFilter(ORDER_ACTION);
        filterB.setPriority(10);
        registerReceiver(receiverB, filterB);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiverA);
        unregisterReceiver(receiverB);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ORDER_ACTION);
        /*
         * 发送有序广播
         * 第二个参数：广播接收者所需要的权限（有些广播的是需要权限的）
         * */
        sendOrderedBroadcast(intent, null);
    }
}