package com.showguan.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StandardReceiver extends BroadcastReceiver {
    public static final String STANDARD_ACTION = "com.showguan.chapter09.standard";
    private static final String TAG = "Kennem";
    // 一旦收到广播，立刻触发onReceive
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(STANDARD_ACTION));
        Log.d(TAG, "收到一个标准广播！");
    }
}
