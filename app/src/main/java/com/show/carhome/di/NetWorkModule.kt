package com.show.carhome.di

import android.util.Log
import com.show.carhome.init.SERVER_URL
import com.show.carhome.remote.CarBrandService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // 该模块将在整个应用的生命周期内提供单例实例
object NetWorkModule {
    /**
     * 提供 OkHttpClient 实例的方法。
     *
     * @return OkHttpClient 实例
     */
    @Singleton // 表示该方法提供的实例在整个应用中是唯一的
    @Provides // Hilt 使用此注解来提供依赖注入
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build() // 构建一个 OkHttpClient 实例
    }

    /**
     * 提供 Retrofit 实例的方法。
     *
     * @param okHttpClient 通过依赖注入提供的 OkHttpClient 实例
     * @return Retrofit 实例
     */
    @Singleton // 表示该方法提供的实例在整个应用中是唯一的
    @Provides // Hilt 使用此注解来提供依赖注入
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        // 创建 HttpLoggingInterceptor 实例，用于记录 HTTP 请求和响应日志
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d("Kennem", message) // 记录日志
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY // 记录请求和响应的完整内容

        // 添加拦截器到 OkHttpClient 实例中
        val newOkHttpClient = okHttpClient.newBuilder()
            .addInterceptor(interceptor) // 添加拦截器
            .build() // 构建新的 OkHttpClient 实例

        return Retrofit.Builder()
            .client(newOkHttpClient) // 设置自定义的 OkHttpClient
            .baseUrl(SERVER_URL) // 设置 API 基础 URL
            .addConverterFactory(GsonConverterFactory.create()) // 添加 Gson 转换器，用于将 JSON 转换为对象
            .build() // 构建 Retrofit 实例
    }

    /**
     * 提供 CarBrandService 实例的方法。
     *
     * @param retrofit 通过依赖注入提供的 Retrofit 实例
     * @return CarBrandService 实例
     */
    @Singleton // 表示该方法提供的实例在整个应用中是唯一的
    @Provides // Hilt 使用此注解来提供依赖注入
    fun provideCarBrandService(retrofit: Retrofit): CarBrandService {
        return retrofit.create(CarBrandService::class.java) // 创建 CarBrandService 实例
    }
}
