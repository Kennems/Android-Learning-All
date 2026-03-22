package com.showguan.chapter05;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        findViewById(R.id.btn_alert).setOnClickListener(this);
        tv_alert = findViewById(R.id.tv_alert);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("尊敬的用户朋友");
        builder.setMessage("你真的确定没点错吗？");
        builder.setPositiveButton("残忍卸载", (dialog, which) -> {
            tv_alert.setText("虽然依依不舍，但是希望能再见面");
        });
        builder.setNegativeButton("点错了哦", (dialog, which) -> {
            tv_alert.setText("我还要再陪你365*n个日夜");
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}