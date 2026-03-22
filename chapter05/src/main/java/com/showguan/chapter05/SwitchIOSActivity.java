package com.showguan.chapter05;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SwitchIOSActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_iosactivity);
        CheckBox ck_status = findViewById(R.id.ck_status);
        tv_result = findViewById(R.id.tv_result);
        ck_status.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String desc = String.format("开关按钮的状态是%s", isChecked ? "开" : "关");
        tv_result.setText(desc);
    }
}