package com.example.study_android;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangeDirectionActivity extends AppCompatActivity {

    TextView tv_monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_direction);

        tv_monitor = findViewById(R.id.tv_monitor);
    }

    // 在配置项变更时触发。比如屏幕方向发生变更等
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                tv_monitor.setText("当前屏幕方向为：竖屏");
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                tv_monitor.setText("当前屏幕方向为：横屏");
                break;
            default:
                break;
        }
    }
}