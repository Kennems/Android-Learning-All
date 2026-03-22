package com.guan.hiltlearn.di;

import com.guan.hiltlearn.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
