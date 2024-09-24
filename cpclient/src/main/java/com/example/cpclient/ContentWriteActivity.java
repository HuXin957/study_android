package com.example.cpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContentWriteActivity extends AppCompatActivity {
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);
        tv_content = findViewById(R.id.tv_content);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                // cpserve里的MyContentProvider在创建表时只写了一个name字段
                values.put("name", "张三");
                getContentResolver().insert(UserInfoContent.CONTENT_URI, values);
            }
        });

        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null, null);
                if (cursor != null) {
                    StringBuilder sb = new StringBuilder();

                    while (cursor.moveToNext()) {
                        sb.append(cursor.getString(cursor.getColumnIndex("name")));
                    }
                    tv_content.setText(sb);
                }
            }
        });
    }
}