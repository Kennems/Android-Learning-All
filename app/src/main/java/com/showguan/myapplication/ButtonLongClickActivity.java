package com.showguan.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.myapplication.util.DateUtil;

public class ButtonLongClickActivity extends AppCompatActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_long_click);
        tv_result = findViewById(R.id.tv_result);
        Button btn_long_click = findViewById(R.id.btn_long_click);
        btn_long_click.setOnLongClickListener(v -> {
            String desc = String.format("%s 您点击了按钮：%s", DateUtil.getNowTime(), ((Button) v).getText());
            tv_result.setText(desc);
            return true;
        });
    }
}