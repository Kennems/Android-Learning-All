package com.showguan.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.showguan.chapter09.BoardOrderActivity;

public class OrderBReceiver extends BroadcastReceiver {
    private static final String TAG = "Kennem";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(BoardOrderActivity.ORDER_ACTION)) {
            Log.d(TAG, "接收器B收到一个有序广播");
        }
    }
}