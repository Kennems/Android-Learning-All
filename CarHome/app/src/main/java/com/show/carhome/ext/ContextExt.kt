package com.show.carhome.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 扩展函数，用于检查当前设备是否连接到网络。
 *
 * @return 如果设备已连接到网络，则返回 true，否则返回 false。
 */
fun Context.isConnectedNetWork(): Boolean = run {
    // 获取 ConnectivityManager 实例
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // 获取当前活跃的网络信息
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

    // 判断网络是否已连接或正在连接
    activeNetwork?.isConnectedOrConnecting == true
}