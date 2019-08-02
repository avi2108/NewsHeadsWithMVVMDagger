package com.sample.newsapi.core.dagger.components;

import com.sample.newsapi.MyApplication;
import com.sample.newsapi.core.dagger.modules.ActivityBuilder;
import com.sample.newsapi.core.dagger.modules.DBModule;
import com.sample.newsapi.core.dagger.modules.MyAppModule;
import com.sample.newsapi.core.dagger.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, MyAppModule.class,
        NetworkModule.class, DBModule.class,
        ActivityBuilder.class})
public interface MyAppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {
    }

}
