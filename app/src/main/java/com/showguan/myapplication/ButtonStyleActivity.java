package com.showguan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.myapplication.util.DateUtil;

public class ButtonStyleActivity extends AppCompatActivity {

    private TextView button_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_style);
        button_result = findViewById(R.id.button_result);

    }

    public void doClick(View view){
        String desc = String.format("%s 您点击了按钮：%s", DateUtil.getNowTime(), ((Button)view).getText());
        button_result.setText(desc);
    }
}