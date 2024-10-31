package com.example.study_android;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.receiver.ShockReceiver;

public class BroadStaticActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_static);

        findViewById(R.id.btn_send_shock).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Android8.0之后删除了大部分静态注册，防止退出App后仍在接收广播，
        // 为了让应用能够继续接收静态广播，需要给静态注册的广播指定包名。

        String fullName = "com.example.study_android.receiver.ShockReceiver";
        Intent intent = new Intent(ShockReceiver.SHOCK_ACTION);
        // 发送静态广播时，需要通过setComponent方法指定接收器的完整路径
        ComponentName componentName = new ComponentName(this, fullName);
        // 设置意图的组件信息
        intent.setComponent(componentName);
        sendBroadcast(intent);
    }
}