package com.showguan.chapter04;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);
        TextView tv_meta = findViewById(R.id.tv_meta);
        PackageManager pm = getPackageManager(); // 1
        try {
            ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA); // 2
            Bundle bundle = info.metaData; // 3
            String weather = bundle.getString("weather"); // 4
            tv_meta.setText(weather);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}