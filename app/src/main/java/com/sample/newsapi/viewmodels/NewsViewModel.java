package com.sample.newsapi.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sample.newsapi.datarepository.offlinerepo.database.DBRepo;
import com.sample.newsapi.datarepository.onlinerepo.CloudRepo;
import com.sample.newsapi.models.HeadLine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsViewModel extends ViewModel {

    private LiveData<List<HeadLine>> arrayListLiveData;
    private ArrayList<String> categoriesList;
    private MutableLiveData<Integer> headLineClickMutableLiveData;
    private MutableLiveData<Boolean> isResetOnNewSync;
    public ObservableBoolean isWebUrlLoadedObservble;
    public ObservableBoolean isHeadLinesLoadedObservable;
    private CloudRepo cloudRepo;
    private DBRepo dbRepo;
    private int categorySelected;


    @Inject
    public NewsViewModel(CloudRepo cloudRepo, DBRepo dbRepo) {
        this.cloudRepo = cloudRepo;
        this.dbRepo = dbRepo;
        categorySelected=0;
        this.headLineClickMutableLiveData = new MutableLiveData<>();
        this.isResetOnNewSync = new MutableLiveData<>();
        this.isWebUrlLoadedObservble = new ObservableBoolean(false);
        this.isHeadLinesLoadedObservable = new ObservableBoolean(false);
        this.isResetOnNewSync.postValue(false);
        this.categoriesList = new ArrayList<>();
        categoriesList.add("business");
        categoriesList.add("technology");
        categoriesList.add("entertainment");
        categoriesList.add("sports");
        categoriesList.add("politics");
    }

    public LiveData<List<HeadLine>> fetchCloudHeadLines() {
        isHeadLinesLoadedObservable.set(false);
        return cloudRepo.fetchHeadLines(categoriesList.get(categorySelected));
    }

    public LiveData<List<HeadLine>> fetchLocalHeadLines() {
        if (arrayListLiveData == null)
            arrayListLiveData = dbRepo.fetchHeadLines();

        return arrayListLiveData;
    }

    public void insertIntoDB(List<HeadLine> headLineList) {

        dbRepo.deleteAllPlaces();
        dbRepo.insert(headLineList);
    }

    public MutableLiveData<Integer> getHeadLineClickMutableLiveData() {
        return headLineClickMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsResetOnNewSync() {
        return isResetOnNewSync;
    }

    public void postClickEventFor(int position) {
        headLineClickMutableLiveData.postValue(position);
    }

    public void setCategorySelected(int categorySelected) {
        this.categorySelected = categorySelected;
    }

    public int getCategorySelected() {
        return categorySelected;
    }

    public ArrayList<String> getCategoriesList() {
        return categoriesList;
    }

    public ObservableBoolean getIsHeadLinesLoadedObservable() {
        return isHeadLinesLoadedObservable;
    }

    public MutableLiveData<Boolean> getIsApiFailure() {
        return cloudRepo.getIsApiFailure();
    }

    private class Client extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {
            super.onReceivedError(view, request, error);
            setIsWebUrlLoadedObservble(false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setIsWebUrlLoadedObservble(true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setIsWebUrlLoadedObservble(false);
        }
    }

    public WebViewClient getWebViewClient() {
        return new Client();
    }

    public void setIsWebUrlLoadedObservble(Boolean isWebUrlLoadedObservble) {
        this.isWebUrlLoadedObservble.set(isWebUrlLoadedObservble);
    }
}
