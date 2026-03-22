package com.showguan.myapplication;

import static com.showguan.myapplication.R.id.btn_enable;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ButtonEnableActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_content;
    private Button btn_test;
    private Button btn_enable;
    private Button btn_disenable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_enable);
        btn_enable = findViewById(R.id.btn_enable);
        btn_disenable = findViewById(R.id.btn_disenable);
        btn_test = findViewById(R.id.btn_test);
        tv_content = findViewById(R.id.tv_content);

        btn_enable.setOnClickListener(this);
        btn_disenable.setOnClickListener(this);
        btn_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_enable) {
            btn_test.setEnabled(true);
            btn_test.setTextColor(Color.BLACK);
        } else if (id == R.id.btn_disenable) {
            btn_test.setEnabled(false);
            btn_test.setTextColor(Color.GRAY);
        } else if (id == R.id.btn_test) {
            tv_content.setText("测试按钮被点击了");
        }
    }
}