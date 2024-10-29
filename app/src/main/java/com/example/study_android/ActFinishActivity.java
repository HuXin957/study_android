package com.example.study_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ActFinishActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_desc;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_finish);

        tv_desc = findViewById(R.id.tv_desc);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String request_time = bundle.getString("request_time");
            String request_content = bundle.getString("request_content");

            String name = getIntent().getStringExtra("name");

            String desc = String.format("姓名是：%s,\n消息是：%s,\n时间是：%s", name, request_content, request_time);
            tv_desc.setText(desc);
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        long currentTimeMillis = System.currentTimeMillis();

        bundle.putString("response_time", Long.toString(currentTimeMillis));
        bundle.putString("response_content", "你好啊");

        intent.putExtras(bundle);
//        携带意图返回上一个页面，RESULT_OK 表示处理成功
        setResult(Activity.RESULT_OK, intent);
//        结束当前活动页
        finish();
    }
}