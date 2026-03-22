package com.showguan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.myapplication.util.DateUtil;

import java.sql.Date;

public class ButtonClickActivity extends AppCompatActivity implements View.OnClickListener{

    private View btn_click_sigle;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        tv_result = findViewById(R.id.tv_result);
        btn_click_sigle = findViewById(R.id.btn_click_sigle);
        btn_click_sigle.setOnClickListener(new MyOnClickListener(tv_result));

        Button btn_click_public = findViewById(R.id.btn_click_public);
        btn_click_public.setOnClickListener(this);

    }

    // 用当前类实现View.OnClickListener， 重写onClick方法
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_click_public){
            String desc = String.format("%s 您点击了按钮：%s", DateUtil.getNowTime(), ((Button)v).getText());
            tv_result.setText(desc);
        }
    }

    // static修饰防止内存泄露， 敬畏之心
    static class MyOnClickListener implements View.OnClickListener{
        private final TextView tv_result;
        public MyOnClickListener(TextView tv_result) {
            this.tv_result = tv_result;
        }

        @Override
        public void onClick(View v) {
            String desc = String.format("%s 您点击了按钮：%s", DateUtil.getNowTime(), ((Button)v).getText());
            tv_result.setText(desc);
        }
    }
}