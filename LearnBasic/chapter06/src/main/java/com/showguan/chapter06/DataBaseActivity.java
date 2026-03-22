package com.showguan.chapter06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DataBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private String mDataBaseName;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        tv_info = findViewById(R.id.tv_info);
        mDataBaseName = getFilesDir() + "/test.db";

        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String desc;
        if (v.getId() == R.id.btn_create) {
            SQLiteDatabase db = openOrCreateDatabase(mDataBaseName, Context.MODE_PRIVATE, null);
            desc = String.format("数据库%s创建%s", db.getPath(), (db != null) ? "成功" : "失败");
            tv_info.setText(desc);
        } else if (v.getId() == R.id.btn_delete) {
            boolean result = deleteDatabase(mDataBaseName);
            desc = String.format("数据库%s删除%s", mDataBaseName, result ? "成功" : "失败");
            tv_info.setText(desc);
        }
    }
}