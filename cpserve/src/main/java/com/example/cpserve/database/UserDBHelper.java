package com.example.cpserve.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "user.db";
    public static final String TABLE_NAME = "user_info";
    public static final int DB_VERSION = 1;

    private static UserDBHelper mHelper = null;


    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //    利用单例模式获取数据库帮助唯一实例
    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    //    创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "name VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
