package com.showguan.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_color);

        TextView textView = findViewById(R.id.tv_TextSize_code_system);
        textView.setTextColor(Color.GREEN);

        TextView tv_code_eight = findViewById(R.id.tv_code_eight);
        tv_code_eight.setTextColor(0xff00ff00);

        TextView tv_code_six = findViewById(R.id.tv_code_six);
        tv_code_six.setTextColor(0x0000ff00);


        TextView tv_java_bgc = findViewById(R.id.tv_java_bgc);
        tv_java_bgc.setBackgroundColor(Color.RED);
    }
}