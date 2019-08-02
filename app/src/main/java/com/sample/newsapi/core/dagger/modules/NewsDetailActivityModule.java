package com.sample.newsapi.core.dagger.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.sample.newsapi.datarepository.offlinerepo.database.DBRepo;
import com.sample.newsapi.datarepository.onlinerepo.CloudRepo;
import com.sample.newsapi.utils.ViewModelProviderFactory;
import com.sample.newsapi.viewmodels.NewsViewModel;
import com.sample.newsapi.views.NewsDetailActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailActivityModule {


//    @Provides
//    NewsViewModel mainActivityViewModel(CloudRepo repository, DBRepo dbRepo) {
//        return new NewsViewModel(repository,dbRepo);
//    }




    @Provides
    ViewModelProvider.Factory provideViewModelProvider(CloudRepo repository, DBRepo dbRepo){
        return new ViewModelProviderFactory(new NewsViewModel(repository,dbRepo));
    }

    @Provides
    NewsViewModel provideViewmodel(NewsDetailActivity newsDetailActivity, ViewModelProvider.Factory viewModelFactory){
       return ViewModelProviders.of(newsDetailActivity, viewModelFactory ).get(NewsViewModel.class);
    }

}
