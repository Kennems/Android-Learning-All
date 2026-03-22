package com.show.carhome.di

import com.show.carhome.R
import com.show.carhome.db.AppDatabase
import com.show.carhome.mapper.Entity2ItemModelMapper
import com.show.carhome.remote.CarBrandService
import com.show.carhome.repository.CarBrandRepositoryImpl
import com.show.carhome.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RepositoryModule 提供了 Hilt 依赖注入模块，包含了对 Repository 的提供方法。
 * 使用 @InstallIn 注解将此模块安装到 SingletonComponent 中，确保提供的依赖在整个应用生命周期内唯一。
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * 提供 Repository 实例。
     * @param api 提供 CarBrandService 实例，用于与远程服务器交互。
     * @param database 提供 AppDatabase 实例，用于与本地数据库交互。
     * @return Repository 实例，具体实现为 CarBrandRepositoryImpl。
     */
    @Singleton // 确保在应用级别只创建一个 Repository 实例
    @Provides // Hilt 用于将此方法标记为提供依赖的工厂方法
    fun provideRepository(
        api: CarBrandService, // 依赖注入提供的 CarBrandService 实例
        database: AppDatabase // 依赖注入提供的 AppDatabase 实例
    ): Repository {
        return CarBrandRepositoryImpl(api, database, Entity2ItemModelMapper())
        // 创建并返回 CarBrandRepositoryImpl 实例，传入 api、database 和 Entity2ItemModelMapper
    }
}
