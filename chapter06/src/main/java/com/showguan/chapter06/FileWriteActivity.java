package com.showguan.chapter06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter06.util.FileUtil;

import java.io.File;

public class FileWriteActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_isMarried;
    private String path;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_isMarried = findViewById(R.id.ck_isMarried);
        tv_content = findViewById(R.id.tv_content);

        findViewById(R.id.btn_write).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_write) {
            String name = et_name.getText().toString();
            String age = et_age.getText().toString();
            String height = et_height.getText().toString();
            String weight = et_weight.getText().toString();

            StringBuilder sb = new StringBuilder();
            sb.append("姓名:").append(name);
            sb.append("\n年龄:").append(age);
            sb.append("\n身高:").append(height);
            sb.append("\n体重:").append(weight);
            sb.append("\n婚否:").append(ck_isMarried.isChecked());

            String directory = null;
            String fileName = System.currentTimeMillis() + ".txt";
            // 外部存储空间，卸载之后还有
//            directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
            // 内部存储空间，卸载之后就没有了
            directory = getFilesDir().toString();
            path = directory + File.separatorChar + fileName;
            Log.d("Kennem", path);
            FileUtil.saveText(path, sb.toString());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_read) {
            tv_content.setText(FileUtil.openText(path));
        }
    }
}