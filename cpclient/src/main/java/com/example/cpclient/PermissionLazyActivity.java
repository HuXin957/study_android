package com.example.cpclient;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cpclient.util.PermissionUtil;


public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSION_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    };

    private static final String[] PERMISSION_SMS = new String[]{
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
    };

    private static final int REQUEST_CONTACTS_CODE = 1;
    private static final int REQUEST_SMS_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.contacts).setOnClickListener(this);
        findViewById(R.id.sms).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contacts:
                PermissionUtil.checkPermission(this, PERMISSION_CONTACTS, REQUEST_CONTACTS_CODE);
                break;
            case R.id.sms:
                PermissionUtil.checkPermission(this, PERMISSION_SMS, REQUEST_SMS_CODE);
                break;
        }
    }


    // 给用户打开请求权限系统弹窗后，用户操作的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("AAAA",Integer.toString(requestCode));
        switch (requestCode) {
            case REQUEST_CONTACTS_CODE:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("AAAA", "通讯录权限获取成功");
                } else {
                    jumpToSettings();
                }
                break;
            case REQUEST_SMS_CODE:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("AAAA", "短信权限获取成功");
                } else {
                    jumpToSettings();
                }
                break;
        }
    }

    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}