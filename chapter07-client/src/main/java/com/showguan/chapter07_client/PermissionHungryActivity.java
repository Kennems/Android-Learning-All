package com.showguan.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter07_client.util.PermissionUtil;
import com.showguan.chapter07_client.util.ToastUtil;


public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "Kennem";

    private static final String[] PERMISSIONS_ALL = new String[]{
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS
    };

    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);
        findViewById(R.id.btn_contacts).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        PermissionUtil.checkPermission(this, PERMISSIONS_ALL, REQUEST_CODE_ALL);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_contacts) {
            PermissionUtil.checkPermission(this, new String[]{PERMISSIONS_ALL[0], PERMISSIONS_ALL[1]}, REQUEST_CODE_CONTACTS);
        } else if (v.getId() == R.id.btn_sms) {
            PermissionUtil.checkPermission(this, new String[]{PERMISSIONS_ALL[2], PERMISSIONS_ALL[3]}, REQUEST_CODE_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:
                if (PermissionUtil.checkGranted(grantResults)) {
                    Log.d(TAG, "所有权限获取成功！");
                } else {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            switch (permissions[i]) {
                                case Manifest.permission.READ_CONTACTS:
                                case Manifest.permission.WRITE_CONTACTS:
                                    ToastUtil.show(this, "获取通讯录读写权限失败");
                                    jumpToSettings();
                                    return;
                                case Manifest.permission.SEND_SMS:
                                case Manifest.permission.READ_SMS:
                                    ToastUtil.show(this, "获取短信读写权限失败");
                                    jumpToSettings();
                                    return;
                            }
                        }

                    }
                }
                break;
            case REQUEST_CODE_CONTACTS:
                for (int grantResult : grantResults) {
                    Log.d(TAG, String.valueOf(grantResult));
                }
                if (PermissionUtil.checkGranted(grantResults)) {
                    Log.d(TAG, "通讯录权限获取成功");
                } else {
                    ToastUtil.show(this, "获取权限失败!");
                    jumpToSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGranted(grantResults)) {
                    Log.d(TAG, "短信收发权限获取成功");
                } else {
                    ToastUtil.show(this, "获取权限失败!");
                    jumpToSettings();
                }
                break;
        }
    }

    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}