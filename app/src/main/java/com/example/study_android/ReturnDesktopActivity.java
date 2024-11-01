package com.example.study_android;

import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Rational;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReturnDesktopActivity extends AppCompatActivity {
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_desktop);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    String reason = intent.getStringExtra("reason");
                    if (!reason.isEmpty() && (reason.equals("homekey") || reason.equals("recentapps"))) {
                        // Android8.0开始才提供画中画模式
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isInPictureInPictureMode()) {
                            PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
                            // 设置宽高比例值，第一个参数表示分子，第二个参数表示分母
                            // 下面的10/5=2，表示画中画窗口的宽度是高度的两倍
                            Rational ratio = new Rational(10, 5);
                            builder.setAspectRatio(ratio);
                            // 进入画中画模式
                            enterPictureInPictureMode(builder.build());

                        }
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        registerReceiver(receiver, filter);
    }

    // 在进入画中画模式或退出画中画模式时触发


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, @NonNull Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        if (isInPictureInPictureMode) {
            Log.d("AAAA","进入画中画模式");
        } else {
            Log.d("AAAA","退出画中画模式");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}