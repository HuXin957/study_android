package com.example.cpclient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SendMmsActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> mResultLauncher;
    private Button btn_jump;
    private ImageView iv_appendix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);
        iv_appendix = findViewById(R.id.iv_appendix);
        btn_jump = findViewById(R.id.btn_jump);

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    Uri picUri = intent.getData();
                    if (picUri != null) {
                        iv_appendix.setImageURI(picUri);
                    }
                }
            }
        });

        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到系统相册选择图片并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置图片类型为图片类型
                intent.setType("image/*");
                // 打开系统相册，并等待图片选择结果
                mResultLauncher.launch(intent);
            }
        });
    }
}