package com.showguan.chapter05;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DatePicker dp_date;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_calendar).setOnClickListener(this);
        dp_date = findViewById(R.id.dp_date);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_confirm){
            String desc = String.format("您选择的日期为%s年 %s月 %s日", dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
            tv_result.setText(desc);
        } else if (v.getId() == R.id.btn_calendar) {
            DatePickerDialog dialog = new DatePickerDialog(this, this, 2027, 4, 13);
            dialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("您选择的日期为%s年 %s月 %s日", year, month, dayOfMonth);
        tv_result.setText(desc);
    }
}