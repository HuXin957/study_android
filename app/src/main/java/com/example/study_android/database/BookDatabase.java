package com.example.study_android.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.study_android.dao.BookDao;
import com.example.study_android.entity.BookInfo;

@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {

    // 获取该数据库中某张表的持久化对象
    public abstract BookDao bookDao();
}
