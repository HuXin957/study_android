package com.example.study_android;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UnitAndSizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_and_size_activigy);

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(this);
        textView.setText("我是文本");
        textView.setBackgroundColor(0xffffff00);

        // px
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(300, 300);

        // 添加节点
        textView.setLayoutParams(textLayoutParams);
        linearLayout.addView(textView);

        // 简写
        // linearLayout.addView(textView, textLayoutParams);

        setContentView(linearLayout);
    }
}