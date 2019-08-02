package com.sample.newsapi.core.dagger.modules;

import com.sample.newsapi.views.MainActivity;
import com.sample.newsapi.views.NewsDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class})
    abstract MainActivity contributeMainActivity();


    @ContributesAndroidInjector(modules = {
            NewsDetailActivityModule.class})
    abstract NewsDetailActivity contributeNewsDetailActivity();

}
