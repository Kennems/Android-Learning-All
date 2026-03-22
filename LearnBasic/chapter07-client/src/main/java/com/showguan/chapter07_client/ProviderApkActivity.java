package com.showguan.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.showguan.chapter07_client.util.PermissionUtil;
import com.showguan.chapter07_client.util.ToastUtil;

import java.io.File;

public class ProviderApkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Kennem";

    // 所需权限列表
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // 请求码，用于权限请求回调标识
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_apk);
        // 设置按钮点击监听
        findViewById(R.id.btn_install).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 检查权限并安装APK
        checkAndInstall();
    }

    /**
     * 检查权限并安装APK
     */
    private void checkAndInstall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.d(TAG, "android 11 + ");
            // 检查是否具有管理所有文件的权限
            if (!Environment.isExternalStorageManager()) {
                Log.d(TAG, "checkisExternalStorageManager");
                // 跳转到设置页面请求权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            } else {
                // 权限已授予，
                // 安装APK
                installApk();
            }
        } else {
            // 检查所需权限是否已全部授予
            if (PermissionUtil.checkPermission(this, PERMISSIONS, REQUEST_CODE)) {
                // 权限已授予，安装APK
                installApk();
            }
        }
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        // 获取APK文件的路径
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/chapter06-release.apk";
        Log.d(TAG, "apkPath:" + apkPath);
        // 获取应用包管理器
        PackageManager pm = getPackageManager();
        // 获取APK文件的包信息
        PackageInfo pi = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pi == null) {
            // 如果包信息为空，显示文件损坏提示
            ToastUtil.show(this, "安装文件已经损坏!");
            return;
        }
        // 获取APK文件的Uri
        Uri uri = Uri.parse(apkPath);
        // 兼容Android 7.0及以上版本，通过FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider获取文件的Uri
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(apkPath));
            Log.d("Kennem", String.format("new uri:%s", uri.toString()));
        }
        // 创建Intent以启动APK安装程序
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置Uri的数据类型为APK文件
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        // 启动系统自带的应用安装程序
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 检查权限请求结果
        if (requestCode == REQUEST_CODE && PermissionUtil.checkGranted(grantResults)) {
            // 权限已授予，安装APK
            installApk();
        }
    }
}
