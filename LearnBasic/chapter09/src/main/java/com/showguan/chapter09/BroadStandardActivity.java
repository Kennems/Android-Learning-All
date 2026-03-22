package com.showguan.chapter09;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter09.receiver.StandardReceiver;

public class BroadStandardActivity extends AppCompatActivity implements View.OnClickListener {

    private StandardReceiver standardReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_standard);
        Button btn_send_broadcast = findViewById(R.id.btn_send_broadcast);
        btn_send_broadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(StandardReceiver.STANDARD_ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        standardReceiver = new StandardReceiver();
        // 创建一个意图过滤器，只处理Standard_Action的广播
        IntentFilter filter = new IntentFilter(StandardReceiver.STANDARD_ACTION);
        // 注册接收器，注册之后才能接受广播
        registerReceiver(standardReceiver, filter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(standardReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}