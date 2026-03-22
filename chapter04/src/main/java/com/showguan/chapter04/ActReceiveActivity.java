package com.showguan.chapter04;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter04.utils.DateUtil;

public class ActReceiveActivity extends AppCompatActivity {

    private TextView tv_send;
    private TextView tv_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_receive);
        tv_receive = findViewById(R.id.tv_receive);

        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = String.format("成功收到消息，收到时间为%s, 收到内容为%s", request_time, request_content);
        tv_receive.setText(desc);
    }


}