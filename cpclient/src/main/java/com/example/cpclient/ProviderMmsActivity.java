package com.example.cpclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.cpclient.entity.ImageInfo;
import com.example.cpclient.util.FileUtil;
import com.example.cpclient.util.PermissionUtil;
import com.example.cpclient.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProviderMmsActivity extends AppCompatActivity {
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    private List<ImageInfo> mImageList = new ArrayList<>();
    GridLayout gl_appendix;

    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_mms);

        gl_appendix = findViewById(R.id.gl_appendix);

        // 手动让MediaStore扫描入库
        MediaScannerConnection.scanFile(this,
                new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        Log.d("A", "扫描完成");
                    }
                });
        if (PermissionUtil.checkPermission(this, PERMISSIONS, PERMISSION_REQUEST_CODE)) {
            load();
        }
    }

    private void load() {
        // 加载图片列表
        loadImageList();
        // 显示图片网格
        showImageGrid();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && PermissionUtil.checkGrant(grantResults)) {
            load();
        }
    }


    private void showImageGrid() {
        gl_appendix.removeAllViews();

        for (ImageInfo image : mImageList) {
            // 1、image -> ImageView
            ImageView iv = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            iv.setImageBitmap(bitmap);
            // 设置图像缩放类型
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int size = Utils.dip2px(this, 110);
            // 设置宽高
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(size, size);
            iv.setLayoutParams(params);

            // 设置内边距
            int padding = Utils.dip2px(this, 5);
            iv.setPadding(padding, padding, padding, padding);

            iv.setOnClickListener(v -> {
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_message.getText().toString(),
                        image.path
                );
            });
            // 2、把图像视图添加到网格
            gl_appendix.addView(iv);
        }
    }

    @SuppressLint("Range")
    private void loadImageList() {
        String[] columns = new String[]{
                MediaStore.Images.Media._ID, // 编号
                MediaStore.Images.Media.TITLE, // 标题
                MediaStore.Images.Media.SIZE, // 文件大小
                MediaStore.Images.Media.DATA, // 文件路径
        };
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 307200",
                null,
                "_size Desc"
        );
        int count = 0;
        if (cursor != null) {
            while (cursor.moveToNext() && count < 6) {
                ImageInfo image = new ImageInfo();
                image.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (FileUtil.checkFileUri(this, image.path)) {
                    count++;
                    mImageList.add(image);
                }
            }
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message, String path) {
        // 根据指定路径创建一个Uri对象
        Uri uri = Uri.parse(path);
        // 兼容Android7.0，把访问文件的Uri方式改为FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider获得文件的Uri访问方式
            FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d("AAAA", String.format("new uri:%s", uri.toString()));
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 因为这次跳转带了Uri，Intent的接收者将被准许读取Intent携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // 彩信发送的目标号码
        intent.putExtra("address", phone);
        // 彩信的标题
        intent.putExtra("subject", title);
        // 彩信的内容
        intent.putExtra("sms_body", message);
        // 彩信的图片附件
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 因为未指定要打开哪个页面，所以系统底部会弹出选择窗口
        startActivity(intent);
    }
}