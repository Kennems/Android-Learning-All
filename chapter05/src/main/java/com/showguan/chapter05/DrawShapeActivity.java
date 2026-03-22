package com.showguan.chapter05;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrawShapeActivity extends AppCompatActivity implements View.OnClickListener {
    private View v_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_shape);
        v_content = findViewById(R.id.v_content);
        findViewById(R.id.btn_rect).setOnClickListener(this);
        findViewById(R.id.btn_oval).setOnClickListener(this);
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_rect){
            v_content.setBackgroundResource(R.drawable.shape_rect_gold);
        }else if (v.getId()==R.id.btn_oval){
            v_content.setBackgroundResource(R.drawable.shape_oval_rose);
        }
    }
}