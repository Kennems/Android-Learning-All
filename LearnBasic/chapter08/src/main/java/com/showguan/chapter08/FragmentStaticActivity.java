package com.showguan.chapter08;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.adapter.PlanetBaseAdapter;
import com.showguan.chapter08.entity.Planet;

public class FragmentStaticActivity extends AppCompatActivity {

    private static final String TAG = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement_static);
    }
}