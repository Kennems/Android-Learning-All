package com.show.carhome.init

import android.annotation.SuppressLint
import android.content.Context

/**
 * 常量 SERVER_URL 存储了用于 API 请求的基础 URL。
 */
const val SERVER_URL = "https://apifoxmock.com/m1/4944163-4601795-default/"

/**
 * AppHelper 是一个单例对象，用于存储和管理全局的应用上下文。
 * 它提供了一个静态的上下文引用，以便在整个应用中使用。
 */
@SuppressLint("StaticFieldLeak")
object AppHelper {
    // 使用 lateinit 声明的变量 mContext 用于存储全局的 Context 对象。
    // lateinit 表示这个变量会在稍后初始化，但在使用之前必须被赋值。
    lateinit var mContext: Context

    /**
     * 初始化 AppHelper，设置全局的 Context 对象。
     *
     * @param context 应用程序的上下文对象，用于初始化 AppHelper。
     */
    fun init(context: Context) {
        // 将传入的上下文对象赋值给 mContext。
        this.mContext = context
    }
}
