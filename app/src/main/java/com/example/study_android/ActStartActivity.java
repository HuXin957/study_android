package com.example.study_android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ActStartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_nav;
    private Button btn_nav_hidden;
    private Button btn_nav_my;
    private Button btn_next_data;
    private Button btn_data_res;
    private TextView tv_res;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_start);

        btn_nav = findViewById(R.id.btn_nav);
        btn_nav_hidden = findViewById(R.id.btn_nav_hidden);
        btn_nav_my = findViewById(R.id.btn_nav_my);
        btn_next_data = findViewById(R.id.btn_next_data);
        btn_data_res = findViewById(R.id.btn_data_res);
        tv_res = findViewById(R.id.tv_res);

        btn_nav.setOnClickListener(this);
        btn_nav_hidden.setOnClickListener(this);
        btn_nav_my.setOnClickListener(this);
        btn_next_data.setOnClickListener(this);
        btn_data_res.setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();

                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    String response_time = bundle.getString("response_time");
                    String response_content = bundle.getString("response_content");
                    String desc = String.format("返回的消息是：%s\n,时间：%s", response_content, response_time);
                    tv_res.setText(desc);
                }
            }
        });

    }

    //    显示
    private void handleNav() {
        //        第一种
//        Intent intent = new Intent(this, ActFinishActivity.class);

//        第二种
//        Intent intent=new Intent();
//        intent.setClass(this, ActFinishActivity.class);

//        第三种
        Intent intent = new Intent();
//        ComponentName componentName=new ComponentName(this, ActFinishActivity.class);
        ComponentName componentName = new ComponentName("com.example.study_android", "com.example.study_android.ActFinishActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }


    //    隐式
    private void handleNavHidden() {
        String phoneNo = "12345";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);

        Uri uri = Uri.parse("tel:" + phoneNo);
        intent.setData(uri);

        startActivity(intent);
    }

    private void handleNavMy() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MYAPP");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    private void handleNextData() {
        Intent intent = new Intent(this, ActFinishActivity.class);
        Bundle bundle = new Bundle();

        long currentTimeMillis = System.currentTimeMillis();

        bundle.putString("request_time", Long.toString(currentTimeMillis));
        bundle.putString("request_content", "hello,world");
        intent.putExtras(bundle);
        intent.putExtra("name", "张三");

        startActivity(intent);
    }

    private void handleDataRes() {
        Intent intent = new Intent(this, ActFinishActivity.class);
        Bundle bundle = new Bundle();

        long currentTimeMillis = System.currentTimeMillis();
        bundle.putString("request_time", Long.toString(currentTimeMillis));
        bundle.putString("request_content", "hello,world");
        bundle.putString("name", "张三");

        intent.putExtras(bundle);
        register.launch(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_nav:
                handleNav();
                break;
            case R.id.btn_nav_hidden:
                handleNavHidden();
                break;
            case R.id.btn_nav_my:
                handleNavMy();
                break;
            case R.id.btn_next_data:
                handleNextData();
                break;
            case R.id.btn_data_res:
                handleDataRes();
                break;
        }
    }
}
