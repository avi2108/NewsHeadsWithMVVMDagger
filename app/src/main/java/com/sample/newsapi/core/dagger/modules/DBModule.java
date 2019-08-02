package com.sample.newsapi.core.dagger.modules;

import android.content.Context;

import com.sample.newsapi.datarepository.offlinerepo.database.DBRepo;
import com.sample.newsapi.datarepository.offlinerepo.database.HeadLinesListDAO;
import com.sample.newsapi.datarepository.offlinerepo.database.HeadLinesListDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Singleton
    @Provides
    HeadLinesListDatabase providerPlaceListDataBase(Context context){
        return HeadLinesListDatabase.getDatabase(context);
    }

    @Singleton
    @Provides
    HeadLinesListDAO providesPlacesListDao(HeadLinesListDatabase headLinesListDatabase){
        return headLinesListDatabase.placesListDao();
    }

    @Singleton
    @Provides
    DBRepo providesDBRepo(HeadLinesListDAO headLinesListDAO){
        return new DBRepo(headLinesListDAO);
    }
}
