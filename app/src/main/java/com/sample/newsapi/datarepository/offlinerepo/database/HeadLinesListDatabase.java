package com.sample.newsapi.datarepository.offlinerepo.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sample.newsapi.models.HeadLine;


@Database(entities = {HeadLine.class}, version = 1, exportSchema = false)
public abstract class HeadLinesListDatabase extends RoomDatabase {

    public abstract HeadLinesListDAO placesListDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile HeadLinesListDatabase INSTANCE;

    public static HeadLinesListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HeadLinesListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HeadLinesListDatabase.class, "headlines list db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
