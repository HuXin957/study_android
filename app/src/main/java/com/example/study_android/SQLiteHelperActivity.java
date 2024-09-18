package com.example.study_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.database.UserDBHelper;
import com.example.study_android.entity.User;
import com.example.study_android.util.ToastUtil;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity {
    private UserDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);

        Button save = findViewById(R.id.save);

        save.setOnClickListener(view -> {
            User user = new User("张三");
//            插入
//            long res = mHelper.insert(user);

//            删除
//            long res = mHelper.deleteByName(user);

//            修改
//            long res = mHelper.update(user);

//            查询
            List<User> list=mHelper.queryAll();
            for(User u: list){
                Log.d("AAA",u.toString());
            }
//            if (res > 0) {
//                ToastUtil.show(this, "success");
//            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this);
//        打开数据库读写连接
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }
}