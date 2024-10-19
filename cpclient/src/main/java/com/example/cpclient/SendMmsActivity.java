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
import android.widget.EditText;
import android.widget.ImageView;

public class SendMmsActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> mResultLauncher;
    private Button btn_jump;
    private Button send;
    private ImageView iv_appendix;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);
        iv_appendix = findViewById(R.id.iv_appendix);
        btn_jump = findViewById(R.id.btn_jump);
        send = findViewById(R.id.send);

        et_phone = findViewById(R.id.phone);
        et_title = findViewById(R.id.title);
        et_message = findViewById(R.id.message);

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    // 获得选中图片的路径对象
                    // content://com.android.providers.downloads.docu
                    picUri = intent.getData();
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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_message.getText().toString());
            }
        });
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message) {
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
        intent.putExtra(Intent.EXTRA_STREAM, picUri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 因为未指定要打开哪个页面，所以系统底部会弹出选择窗口
        startActivity(intent);
    }
}