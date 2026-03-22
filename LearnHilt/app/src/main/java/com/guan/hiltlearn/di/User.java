package com.guan.hiltlearn.di;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

public class User {
    // 1. 使用@Inject注解在构造方法上，告知Dagger可以通过构造方法获取实例
    @Inject
    public User() {
    }
}
