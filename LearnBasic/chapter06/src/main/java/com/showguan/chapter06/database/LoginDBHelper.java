package com.showguan.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.showguan.chapter06.enity.LoginInfo;
import com.showguan.chapter06.enity.User;

import java.util.ArrayList;
import java.util.List;

public class LoginDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "login_info";
    private static final int DB_VERSION = 1;
    private static LoginDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private LoginDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static LoginDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new LoginDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getReadableDatabase();
        }
        return mWDB;
    }


    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " phone VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " remember INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库版本更新时使用
        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN phone VARCHAR;";
        db.execSQL(sql);
        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN password VARCHAR;";
        db.execSQL(sql);
    }

    public void save(LoginInfo info){
        try {
            mWDB.beginTransaction();
            delete(info);
            insert(info);
            mWDB.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }

    public long delete(LoginInfo info){
        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{
                info.getPhone()
        });
    }

    public long insert(LoginInfo info) {
        ContentValues values = new ContentValues();
        values.put("phone", info.getPhone());
        values.put("password", info.getPassword());
        values.put("remember", info.isRemember());

        // 返回行号
        return mWDB.insert(TABLE_NAME, null, values);
    }

    public long deleteByName(String name) {
        return mWDB.delete(TABLE_NAME, "name = ?", new String[]{
                name
        });
    }

    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setAge(cursor.getInt(2));
            user.setHeight(cursor.getFloat(3));
            user.setWeight(cursor.getFloat(4));
            user.setMarried(cursor.getInt(5) == 1 ? true : false);
            list.add(user);
        }
        return list;
    }

    public LoginInfo queryTop() {
        LoginInfo info = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE remember = 1 ORDER BY _id DESC LIMIT 1";
        Cursor cursor = null;

        try {
            cursor = mRDB.rawQuery(sql, null);
            if (cursor != null && cursor.moveToNext()) {
                info = new LoginInfo();
                int phoneIndex = cursor.getColumnIndex("phone");
                int passwordIndex = cursor.getColumnIndex("password");
                int rememberIndex = cursor.getColumnIndex("remember");

                if (phoneIndex != -1) {
                    info.setPhone(cursor.getString(phoneIndex));
                }
                if (passwordIndex != -1) {
                    info.setPassword(cursor.getString(passwordIndex));
                }
                if (rememberIndex != -1) {
                    info.setRemember(cursor.getInt(rememberIndex) == 1);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return info;
    }

    public LoginInfo queryByPhone(String phone) {
        LoginInfo info = null;
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = mRDB.query(TABLE_NAME, null, "phone=? and remember=1", new String[]{phone}, null, null, null);

        try {
            if (cursor != null && cursor.moveToNext()) {
                info = new LoginInfo();
                int phoneIndex = cursor.getColumnIndex("phone");
                int passwordIndex = cursor.getColumnIndex("password");
                int rememberIndex = cursor.getColumnIndex("remember");

                if (phoneIndex != -1) {
                    info.setPhone(cursor.getString(phoneIndex));
                }
                if (passwordIndex != -1) {
                    info.setPassword(cursor.getString(passwordIndex));
                }
                if (rememberIndex != -1) {
                    info.setRemember(cursor.getInt(rememberIndex) == 1);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return info;
    }

}
