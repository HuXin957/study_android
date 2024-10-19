package com.example.cpclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.cpclient.util.PermissionUtil;

import java.io.File;

public class ProviderApkActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_apk);

        findViewById(R.id.btn_install).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Android 11 之后获取 MANAGE_EXTERNAL_STORAGE 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkAndInstall();
        } else {
            // 如果有权限，直接安装，没有权限则获取权限
            if (PermissionUtil.checkPermission(this, PERMISSIONS, PERMISSION_REQUEST_CODE)) {
                installApk();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkAndInstall() {
        // 检查是否拥有MANAGE_EXTERNAL_STORAGE 权限，没有则跳转到设置页面
        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivity(intent);
        } else {
            installApk();
        }
    }

    private void installApk() {
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/study_android-release.apk";
        // 获取包应用管理器
        PackageManager pm = getPackageManager();
        // 获取apk文件的包信息
        PackageInfo pi = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pi == null) {
            Log.d("AAAA", "包文件已损坏");
            return;
        }

        // installer
        Uri uri = Uri.parse(apkPath);
        // 兼容Android7，把访问文件的Uri方式改为FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider获得文件的Uri访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(apkPath));
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置Uri的数据类型为APK文件
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        // 启动系统自带的应用安装程序
        startActivity(intent);
    }

}