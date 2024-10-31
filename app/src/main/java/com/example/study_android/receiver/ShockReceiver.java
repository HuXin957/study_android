package com.example.study_android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class ShockReceiver extends BroadcastReceiver {
    public static final String SHOCK_ACTION = "com.example.study_android.shock";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(SHOCK_ACTION)) {
            // 从系统服务中获取震动管理器
            Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // 命令震动器震动若干秒，单位：毫秒
            vb.vibrate(500);
        }
    }
}