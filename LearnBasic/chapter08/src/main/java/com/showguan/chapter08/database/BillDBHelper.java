package com.showguan.chapter08.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.showguan.chapter08.entity.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {

    private static String TAG = "Kennem";

    private static final String DB_NAME = "bill.db";
    private static final String TABLE_BILL_INFO = "bill_info";
    private static final int DB_VERSION = 2;
    private static BillDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private BillDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
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
        //_id 保证id不是不是关键字
        String sql = null;
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILL_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type VARCHAR NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long save(BillInfo billInfo) {
        ContentValues cv = new ContentValues();
        cv.put("date", billInfo.date);
        cv.put("type", billInfo.type);
        cv.put("amount", billInfo.amount);
        cv.put("remark", billInfo.remark);
        return mWDB.insert(TABLE_BILL_INFO, null, cv);
    }

    @SuppressLint("Range")
    public List<BillInfo> queryByMonth(String yearMonth){
        List<BillInfo> list = new ArrayList<>();
        String sql = "select * from " + TABLE_BILL_INFO + " where date like '" + yearMonth + "%';";
        Log.d(TAG, sql);
        Cursor cursor = mRDB.rawQuery(sql, null);
        while(cursor.moveToNext()){
            BillInfo bill = new BillInfo();
            bill.id = cursor.getInt(cursor.getColumnIndex("_id"));
            bill.date = cursor.getString(cursor.getColumnIndex("date"));
            bill.type = cursor.getInt(cursor.getColumnIndex("type"));
            bill.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            bill.remark = cursor.getString(cursor.getColumnIndex("remark"));
            Log.d(TAG, bill.toString());
            list.add(bill);
        }
        return list;

    }



}
