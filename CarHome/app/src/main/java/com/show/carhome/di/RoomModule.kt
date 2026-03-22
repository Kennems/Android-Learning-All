package com.show.carhome.di

import android.app.Application
import androidx.room.Room
import com.show.carhome.db.AppDatabase
import com.show.carhome.db.CarBrandDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RoomModule 提供了 Hilt 依赖注入模块，包含了对 Room 数据库及其 DAO 的提供方法。
 * 使用 @InstallIn 注解将此模块安装到 SingletonComponent 中，确保提供的依赖在整个应用生命周期内唯一。
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    /**
     * 提供应用级别的 Room 数据库实例。
     * @param application 提供应用上下文用于创建 Room 数据库。
     * @return AppDatabase Room 数据库实例。
     */
    @Singleton // 确保在应用级别只创建一个 AppDatabase 实例
    @Provides // Hilt 用于将此方法标记为提供依赖的工厂方法
    fun provideAppDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, // 使用应用上下文创建数据库
            AppDatabase::class.java, // Room 数据库的具体实现类
            "car_home.db" // 数据库名称
        )
            .build() // 构建并返回 AppDatabase 实例
    }

    /**
     * 提供 CarBrandDao 数据访问对象的实例。
     * @param database 提供 Room 数据库实例，用于创建 DAO。
     * @return CarBrandDao 数据访问对象实例。
     */
    @Singleton // 确保在应用级别只创建一个 CarBrandDao 实例
    @Provides // Hilt 用于将此方法标记为提供依赖的工厂方法
    fun provideCarBrandDao(database: AppDatabase): CarBrandDao {
        return database.carBrandDao() // 从 Room 数据库实例中获取 CarBrandDao 实例
    }
}
