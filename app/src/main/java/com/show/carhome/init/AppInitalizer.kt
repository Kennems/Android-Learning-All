package com.show.carhome.init

import android.content.Context
import androidx.startup.Initializer

/**
 * AppInitializer 是一个自定义的应用初始化类，用于在应用启动时进行必要的初始化操作。
 * 它实现了 androidx.startup.Initializer 接口。
 */
class AppInitializer : Initializer<Unit> {

    /**
     * create 方法在应用启动时被调用，用于执行初始化逻辑。
     *
     * @param context 应用程序的上下文，用于初始化操作
     */
    override fun create(context: Context) {
        // 调用 AppHelper 的 init 方法进行初始化操作
        AppHelper.init(context)
    }

    /**
     * dependencies 方法用于声明当前 Initializer 所依赖的其他 Initializer。
     * 这里返回一个空的列表，表示没有依赖项。
     *
     * @return MutableList<Class<out Initializer<*>>> 当前初始化器的依赖项列表
     */
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}