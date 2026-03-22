package com.showguan.chapter06.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.showguan.chapter06.enity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(BookInfo... bookInfos);

    @Delete
    void delete(BookInfo... bookInfos);

    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update
    int update(BookInfo... bookInfos);

    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    @Query("SELECT * FROM BookInfo WHERE name = :name ORDER BY id DESC LIMIT 1")
    BookInfo queryByName(String name);
}
