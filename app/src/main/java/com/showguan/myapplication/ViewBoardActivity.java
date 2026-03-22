package com.showguan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.myapplication.util.Utils;

public class ViewBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_board);
        TextView tv_code = findViewById(R.id.tv_code);
        ViewGroup.LayoutParams params = tv_code.getLayoutParams();
        params.width = Utils.dip2px(this, 300);
        tv_code.setLayoutParams(params);

    }
}