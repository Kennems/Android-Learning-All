package com.showguan.chapter04;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActStartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Kennem";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ActStartActivity onCreate");
        setContentView(R.layout.activity_act_start);
        findViewById(R.id.btn_act_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        startActivity(new Intent(this, ActFinishActivity.class));
//        Intent intent = new Intent();
//        intent.setClass(this, ActFinishActivity.class)
//        startActivity(intent);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(this, ActFinishActivity.class);
        intent.setComponent(componentName);
        Log.d(TAG, "ActStartActivity onClick");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "ActStartActivity onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ActStartActivity onResume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "ActStartActivity onRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ActStartActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ActStartActivity onPause");

    }
}