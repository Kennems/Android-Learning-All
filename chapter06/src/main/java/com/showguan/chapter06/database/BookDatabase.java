package com.showguan.chapter06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.showguan.chapter06.dao.BookDao;
import com.showguan.chapter06.enity.BookInfo;

// 这个类的主要作用是连接 DAO（Data Access Object）和 Entity，并且管理数据库的创建和版本管理。
@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
