package com.showguan.chapter07_client;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter07_client.entity.User;
import com.showguan.chapter07_client.util.ToastUtil;

public class ContentWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "Kennem";

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_isMarried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_isMarried = findViewById(R.id.ck_isMarried);


        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_write).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_write){
            ContentValues values = new ContentValues();
            values.put(UserInfoContent.USER_NAME, et_name.getText().toString());
            values.put(UserInfoContent.USER_AGE, et_age.getText().toString());
            values.put(UserInfoContent.USER_HEIGHT, et_height.getText().toString());
            values.put(UserInfoContent.USER_WEIGHT, et_weight.getText().toString());
            values.put(UserInfoContent.USER_MARRIED, ck_isMarried.isChecked());
            getContentResolver().insert(UserInfoContent.CONTENT_URI, values);
            ToastUtil.show(this, "保存成功！");
        } else if (v.getId() == R.id.btn_read) {
            Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while(cursor.moveToNext()){
                    User info = new User();
                    info.setId(cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID)));
                    info.setAge(cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE)));
                    info.setName(cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME)));
                    info.setHeight(cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT)));
                    info.setWeight(cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT)));
                    info.setMarried(cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1);
                    Log.d(TAG, info.toString());
                }
                cursor.close();
            }
        } else if (v.getId() == R.id.btn_delete) {
            // 根据id删除单条
//            Uri uri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, 1);
//            int count = getContentResolver().delete(uri, null, null);

            //根据名称删除多条
            int count = getContentResolver().delete(UserInfoContent.CONTENT_URI, "name = ?", new String[]{et_name.getText().toString()});

            if(count > 0){
                ToastUtil.show(this, "删除成功");
            }else{
                ToastUtil.show(this, "删除失败，不存在名为" + et_name.getText().toString() + "的用户！");
            }

        }
    }
}