package com.sample.newsapi.core.dagger.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.sample.newsapi.adapters.CategoriesListAdapter;
import com.sample.newsapi.adapters.HeadLinesListAdapter;
import com.sample.newsapi.datarepository.offlinerepo.database.DBRepo;
import com.sample.newsapi.datarepository.onlinerepo.CloudRepo;
import com.sample.newsapi.utils.ViewModelProviderFactory;
import com.sample.newsapi.viewmodels.NewsViewModel;
import com.sample.newsapi.views.MainActivity;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {


//    @Provides
//    NewsViewModel mainActivityViewModel(CloudRepo repository, DBRepo dbRepo) {
//        return new NewsViewModel(repository,dbRepo);
//    }




    @Provides
    ViewModelProvider.Factory provideViewModelProvider(CloudRepo repository, DBRepo dbRepo){
        return new ViewModelProviderFactory(new NewsViewModel(repository,dbRepo));
    }

    @Provides
    NewsViewModel provideViewmodel(MainActivity mainActivity,ViewModelProvider.Factory viewModelFactory){
       return ViewModelProviders.of(mainActivity, viewModelFactory ).get(NewsViewModel.class);
    }

    @Provides
    HeadLinesListAdapter providePlacesListAdapter(NewsViewModel viewModel){
        return new HeadLinesListAdapter(viewModel);
    }

    @Provides
    CategoriesListAdapter provideCatListAdapter(NewsViewModel viewModel){
        return new CategoriesListAdapter(viewModel);
    }


    @Provides
    @Named("vertical")
    LinearLayoutManager provideLinearLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }

    @Provides
    @Named("horizontal")
    LinearLayoutManager provideHorizLinearLayoutManager(Context context){
        return new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
    }
}
