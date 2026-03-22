package com.showguan.chapter06;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.showguan.chapter06.database.BookDatabase;
import com.showguan.chapter06.database.ShoppingDBHelper;
import com.showguan.chapter06.enity.GoodsInfo;
import com.showguan.chapter06.util.FileUtil;
import com.showguan.chapter06.util.SharedUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication mApp;

    public HashMap<String, String> infoMap = new HashMap<>();

    private static BookDatabase bookDatabase;

    public int goodsCount;

    private static String TAG = "Kennem";

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

//        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();

        initGoodsInfo();
    }

    public static MyApplication getInstance() {
        return mApp;
    }

    public BookDatabase getBookDatabase(){
        return bookDatabase;
    }
    private void initGoodsInfo() {
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if(isFirst){
            SharedUtil.getInstance(this).writeBoolean("first", false);

            List<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info : list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
                String path = directory + info.id + ".jpg";
                FileUtil.saveImage(path, bitmap);
                bitmap.recycle();
                info.picPath = path;
                Log.d(TAG, info.toString());
            }
            ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
            dbHelper.openWriteLink();

            dbHelper.insertGoodsInfos(list);

            dbHelper.closeLink();

            SharedUtil.getInstance(this).writeBoolean("first", false);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Kennem", "MyApplication onTerminate: ");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Kennem", "MyApplication onConfigurationChanged: ");
    }
}



