package com.guan.hiltlearn

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    @Singleton
    abstract fun bindsGreetingService(
        impl: GreetingServiceImpl
    ): GreetingService
}
