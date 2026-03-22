package com.showguan.chapter07_client.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    /**
     * 检查并请求权限
     *
     * @param act         当前的Activity
     * @param permissions 需要检查的权限列表
     * @param requestCode 请求码，用于回调标识
     * @return 如果所有权限都已被授予，返回true；否则返回false
     */

    public static boolean checkPermission(Activity act, String[] permissions, int requestCode) {
        // 检查当前系统版本是否是Android 6.0（API 23）或更高版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = PackageManager.PERMISSION_GRANTED;
            // 遍历所有权限，逐一检查是否已被授予
            for (String permission : permissions) {
                check = ContextCompat.checkSelfPermission(act, permission);
                // 如果有任何一个权限未被授予，则退出循环
                if (check != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            // 如果有任何一个权限未被授予，请求权限并返回false
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, permissions, requestCode);
                return false;
            }
        }
        // 如果所有权限都已被授予，返回true
        return true;
    }

    /**
     * 检查所有权限请求结果是否都已被授予
     *
     * @param grantResults 权限请求结果数组
     * @return 如果所有权限都已被授予，返回true；否则返回false
     */
    public static boolean checkGranted(int[] grantResults) {
        // 遍历所有权限请求结果
        for (int grantResult : grantResults) {
            // 如果有任何一个权限未被授予，则返回false
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        // 如果所有权限都已被授予，返回true
        return true;
    }
}
