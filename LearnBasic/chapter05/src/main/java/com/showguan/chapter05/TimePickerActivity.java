package com.showguan.chapter05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private TimePicker tp_time;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_time).setOnClickListener(this);
        tp_time = findViewById(R.id.tp_time);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            String desc = String.format("您选择的时间为%s时 %s分", tp_time.getHour(), tp_time.getMinute());
            tv_result.setText(desc);
        } else if (v.getId() == R.id.btn_time) {
            Log.d("Kennem", "onClick: R.id.btn_time");
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK,this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc = String.format("您选择的时间为%s时 %s分", hourOfDay, minute);
        tv_result.setText(desc);
    }
}