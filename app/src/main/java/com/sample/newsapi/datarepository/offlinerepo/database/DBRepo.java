package com.sample.newsapi.datarepository.offlinerepo.database;


import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.sample.newsapi.models.HeadLine;

import java.util.List;

import javax.inject.Inject;


public class DBRepo {

    private HeadLinesListDAO headLinesListDAO;
    private LiveData<List<HeadLine>> listLiveData;

    @Inject
    public DBRepo(HeadLinesListDAO headLinesListDAO) {
        this.headLinesListDAO = headLinesListDAO;
        listLiveData = headLinesListDAO.fetchPlacesFromDB();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<HeadLine>> fetchHeadLines() {
        return listLiveData;
    }

    public void insert(List<HeadLine> headLineList) {
        new InsertAsyncTask(headLinesListDAO).execute(headLineList);
    }

    public void deleteAllPlaces(){
          new DeleteAsycnTask(headLinesListDAO).execute();
    }

    /**
     * asynctask that handles delete operation in background
     */
    private class DeleteAsycnTask extends AsyncTask<Void,Void,String>{

        private HeadLinesListDAO mAsyncTaskDao;

        DeleteAsycnTask(HeadLinesListDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     * asynctask that inserts batch records in background
     */
    private class InsertAsyncTask extends AsyncTask<List<HeadLine>, Void, List<HeadLine>> {

        private HeadLinesListDAO mAsyncTaskDao;

        InsertAsyncTask(HeadLinesListDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<HeadLine> doInBackground(final List<HeadLine>... params) {
            List<HeadLine> headLineList = params[0];
            HeadLine[] headLines = new HeadLine[headLineList.size()];
            for (int i = 0; i< headLineList.size(); i++)
                headLines[i]= headLineList.get(i);
                mAsyncTaskDao.batchInsert(headLines);
            return params[0];
        }

    }
}
