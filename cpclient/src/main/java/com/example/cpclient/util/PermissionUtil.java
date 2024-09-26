package com.example.cpclient.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    // 多个权限返回true表示有权限，false没有开启权限
    public static boolean checkPermission(Activity act, String[] permissions, int requestCode) {
        // Android6.0之后开始采用动态权限管理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCode = PackageManager.PERMISSION_GRANTED;
            for (String permission : permissions) {
                checkCode = ContextCompat.checkSelfPermission(act, permission);
                if (checkCode != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }

            // 未开启权限，请求系统弹窗，好让用户选择是否开启权限
            if (checkCode != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, permissions, requestCode);
                return false;
            }
        }

        return true;
    }

    public static boolean checkGrant(int[] grantResults) {
        if (grantResults != null) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
