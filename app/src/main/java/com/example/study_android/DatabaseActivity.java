package com.example.study_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseActivity extends AppCompatActivity {
    private String mDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_acitivity);

        mDatabaseName = getFilesDir() + "/test.db";

        Button btn = findViewById(R.id.create);
        Button btn_del = findViewById(R.id.del);

        TextView tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = openOrCreateDatabase(mDatabaseName, Context.MODE_PRIVATE, null);
                String desc = String.format("数据库%s创建%s", db.getPath(), db != null ? "成功" : "失败");
                tv.setText(desc);
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = deleteDatabase(mDatabaseName);
                String desc = String.format("数据库%s删除%s", mDatabaseName, result ? "成功" : "失败");
                tv.setText(desc);
            }
        });
    }
}