package com.showguan.chapter09;

import android.annotation.SuppressLint;
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

import com.showguan.chapter09.receiver.OrderAReceiver;
import com.showguan.chapter09.receiver.OrderBReceiver;

public class BoardOrderActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ORDER_ACTION = "com.showguan.chapter09.order";
    private OrderAReceiver orderAReceiver;
    private OrderBReceiver orderBReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_order);
        Button btn_send_broadcast = findViewById(R.id.btn_send_broadcast);
        btn_send_broadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ORDER_ACTION);
        sendOrderedBroadcast(intent, null);
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onStart() {
        super.onStart();
        orderAReceiver = new OrderAReceiver();
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);
        filterA.setPriority(8);
        registerReceiver(orderAReceiver, filterA);

        orderBReceiver = new OrderBReceiver();
        IntentFilter filterB = new IntentFilter(ORDER_ACTION);
        filterB.setPriority(10);
        registerReceiver(orderBReceiver, filterB);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(orderAReceiver);
        unregisterReceiver(orderBReceiver);
    }
}