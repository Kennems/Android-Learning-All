package com.zhouguan.learnarouter;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import android.app.Application;
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate();
        Log.d("Kennem", "Initialized: ")
//        if (BuildConfig.DEBUG) {           // 开发模式下开启日志
        ARouter.openLog();
        ARouter.openDebug();
//        }
        ARouter.init(this); // 一定要调用初始化
    }
}