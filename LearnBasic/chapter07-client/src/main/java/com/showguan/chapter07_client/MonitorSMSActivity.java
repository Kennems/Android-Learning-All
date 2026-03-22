package com.showguan.chapter07_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MonitorSMSActivity extends AppCompatActivity {

    private static String TAG = "Kennem";

    private SmsGetObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_smsactivity);
        Uri uri = Uri.parse("content://sms");
        mObserver = new SmsGetObserver(this);
        getContentResolver().registerContentObserver(uri, true, mObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static class SmsGetObserver extends ContentObserver {

        private final Context mContext;

        public SmsGetObserver(Context context) {
            super(new Handler(Looper.getMainLooper()));
            this.mContext = context;
        }

        @Override
        @SuppressLint("Range")
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            if (uri == null) {
                return;
            }
            if (uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
                return;
            }
            Cursor cursor = mContext.getContentResolver().query(uri, new String[]{"address", "body", "date",}, null, null, "date DESC");
            if (cursor.moveToNext()) {
                String sender = cursor.getString(cursor.getColumnIndex("address"));
                String content = cursor.getString(cursor.getColumnIndex("body"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                Log.d(TAG, String.format("sender : %s, content: %s, date: %s", sender, content, date));
            }
            cursor.close();
        }
    }
}