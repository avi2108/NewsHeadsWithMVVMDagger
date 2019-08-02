package com.sample.newsapi.core.dagger.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.sample.newsapi.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class MyAppModule {

    @Singleton
    @Provides
    Context provideContext(MyApplication application){
        return application;
    }

    @Singleton
    @Provides
    Gson provideGson(){
        return new Gson();
    }


}
