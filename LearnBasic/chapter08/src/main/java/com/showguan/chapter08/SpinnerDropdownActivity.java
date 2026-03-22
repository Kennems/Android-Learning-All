package com.showguan.chapter08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter08.util.ToastUtil;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp_dropdown;
    private static final String[]  starArray = new String[]{
            "水星", "金星", "地球", "火星", "木星", "土星", "天王星", "海王星"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dropdown);
        sp_dropdown = findViewById(R.id.sp_dropdown);
        ArrayAdapter<String> starAdapter = new ArrayAdapter<>(this, R.layout.item_select,  starArray);

        sp_dropdown.setAdapter(starAdapter);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择了： " + starArray[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}