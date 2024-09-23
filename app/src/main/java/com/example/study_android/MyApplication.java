package com.example.study_android;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.study_android.database.BookDatabase;

import java.util.HashMap;

public class MyApplication extends Application {
    private static final String TAG = "MARK";

    private static MyApplication mApp;
    public HashMap<String, String> infoMap = new HashMap<>();

    // 声明一个书籍数据库对象
    private BookDatabase bookDatabase;

    public static MyApplication getInstance() {
        return mApp;
    }

    // App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        // 构建书籍数据库的实例
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "'book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库，如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();
    }

    // App终止时调用（不一定）,在应用开发中没什么意义
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    // 配置改变时调用，例如：从竖屏变为横屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    public BookDatabase getBookDB() {
        return bookDatabase;
    }
}
