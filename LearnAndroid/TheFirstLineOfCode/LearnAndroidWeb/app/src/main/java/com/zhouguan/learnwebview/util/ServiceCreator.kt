package com.zhouguan.learnwebview.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://10.0.2.2/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // reified关键字允许你在运行时获取泛型类型的信息，而inline关键字允许编译器在调用函数时内联函数体，这样可以更高效地使用这些信息
    // inline 和 reified 是泛型实例化的两大条件
    inline fun <reified T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}