package com.example.study_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.example.study_android.util.FileUtil;
import com.example.study_android.util.ToastUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {
    String path;
    ImageView iv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        iv_content = findViewById(R.id.iv_content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String fileName = System.currentTimeMillis() + ".png";
                path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + fileName;
                // 从指定的资源文件中获取位图对象
                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.meilv);
                // 把位图对象保存为图片文件
                FileUtil.saveImage(path, b1);
                ToastUtil.show(this, "保存成功");
                break;
            case R.id.btn_read:
                // 第一种方式
                // Bitmap bitmap = FileUtil.readImage(path);
                // iv_content.setImageBitmap(bitmap);
                // 第二种方式
                // Bitmap bitmap = BitmapFactory.decodeFile(path);
                // iv_content.setImageBitmap(bitmap);
                // 直接调用setImageUri方法，设置图像的路径对象
                iv_content.setImageURI(Uri.parse(path));
                break;
        }
    }
}