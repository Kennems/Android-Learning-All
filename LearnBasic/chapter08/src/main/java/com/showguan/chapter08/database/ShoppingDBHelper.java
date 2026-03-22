package com.showguan.chapter08.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.showguan.chapter08.entity.CartInfo;
import com.showguan.chapter08.entity.CartItemInfo;
import com.showguan.chapter08.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {

    private static String TAG = "Kennem";

    private static final String DB_NAME = "shop.db";
    private static final String TABLE_GOODS_INFO = "shop_info";
    private static final String TABLE_CART_INFO = "cart_info";
    private static final int DB_VERSION = 2;
    private static ShoppingDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private ShoppingDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
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
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description INTEGER NOT NULL," +
                " price FLOAT NOT NULL," +
                " pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertGoodsInfos(List<GoodsInfo> list) {
        try {
            mWDB.beginTransaction();
            for (GoodsInfo goodsInfo : list) {
                ContentValues values = new ContentValues();
                values.put("name", goodsInfo.name);
                values.put("description", goodsInfo.description);
                values.put("price", goodsInfo.price);
                values.put("pic_path", goodsInfo.picPath);
                mWDB.insert(TABLE_GOODS_INFO, null, values);
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }


    public long deleteByName(String name) {
        return mWDB.delete(TABLE_GOODS_INFO, "name = ?", new String[]{
                name
        });
    }

    public long update(GoodsInfo goodsInfo) {
        ContentValues values = new ContentValues();
        values.put("name", goodsInfo.name);
        values.put("description", goodsInfo.description);
        values.put("price", goodsInfo.price);
        values.put("pic_path", goodsInfo.picPath);
        return mWDB.update(TABLE_GOODS_INFO, values, "name=?", new String[]{
                goodsInfo.name
        });
    }

    public List<GoodsInfo> queryAllGoods() {
        List<GoodsInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.id = (cursor.getInt(0));
            goodsInfo.name = (cursor.getString(1));
            goodsInfo.description = (cursor.getString(2));
            goodsInfo.price = (cursor.getFloat(3));
            goodsInfo.picPath = (cursor.getString(4));
            list.add(goodsInfo);
        }
        cursor.close();
        return list;
    }

    // 根据商品ID查询商品信息
    public GoodsInfo queryGoodsInfoByGoodsId(int goodsId) {
        GoodsInfo info = null;
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, "_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        if (cursor.moveToNext()) {
            info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
        }
        return info;
    }

    public CartInfo queryCartInfoByGoodsId(int goodsId) {
        CartInfo info = null;
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, "goods_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        if (cursor.moveToNext()) {
            info = new CartInfo();
            info.id = cursor.getInt(0);
            info.goodsId = cursor.getInt(1);
            info.count = cursor.getInt(2);
        }
        return info;
    }


    public void insertCartInfo(int goodsId) {
        CartInfo cartInfo = queryCartInfoByGoodsId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);
        if (cartInfo == null) {
            values.put("count", 1);
            mWDB.insert(TABLE_CART_INFO, null, values);
        } else {
            values.put("_id", cartInfo.id);
            values.put("count", cartInfo.count + 1);
            mWDB.update(TABLE_CART_INFO, values, "_id = ?", new String[]{String.valueOf(cartInfo.id)});
        }
    }

    public int countCartInfo() {
        int count = 0;
        String sql = "select sum(count) from " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CartInfo cartInfo = new CartInfo();
            cartInfo.id = (cursor.getInt(0));
            cartInfo.goodsId = (cursor.getInt(1));
            cartInfo.count = (cursor.getInt(2));
            list.add(cartInfo);
        }
        cursor.close();
        return list;
    }

    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO, "goods_id = ?", new String[]{String.valueOf(goodsId)});
    }

    public void deleteAllCartInfo() {
        mWDB.delete(TABLE_CART_INFO, "1 = 1", null);
    }

    public List<CartItemInfo> queryCartAndGoodsInfo() {
        // 查询所有的
        String sql = "SELECT shop.*, cart.* FROM " + TABLE_CART_INFO + " cart LEFT JOIN " + TABLE_GOODS_INFO + " shop ON shop._id = cart.goods_id;";
        Log.d(TAG, sql);
        Cursor cursor = mRDB.rawQuery(sql, null);
        List<CartItemInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.id = cursor.getInt(0);
            goodsInfo.name = cursor.getString(1);
            goodsInfo.description = cursor.getString(2);
            goodsInfo.price = cursor.getFloat(3);
            goodsInfo.picPath = cursor.getString(4);
            CartInfo cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(5);
            cartInfo.goodsId = cursor.getInt(6);
            cartInfo.count = cursor.getInt(7);

            CartItemInfo cartItemInfo = new CartItemInfo(cartInfo, goodsInfo);
            list.add(cartItemInfo);
        }
        cursor.close();
        return list;
    }

}
