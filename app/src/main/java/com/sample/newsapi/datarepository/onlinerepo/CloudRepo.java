package com.sample.newsapi.datarepository.onlinerepo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.sample.newsapi.models.HeadLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloudRepo {


    public Context context;
    private ApiService apiService;
    private MutableLiveData<List<HeadLine>> headLinesLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isApiFailure = new MutableLiveData<>();

    @Inject
    public CloudRepo(Context context, ApiService apiService) {
        this.apiService = apiService;
        this.context = context;
        isApiFailure.postValue(false);
    }

    /**
     * fetches headlines from api category wise
     * @param category
     * @return
     */
    public LiveData<List<HeadLine>> fetchHeadLines(String category) {
        if (!isNetWorkConnected()) {
            return headLinesLiveData;
        }
        apiService.fetchHeadlines("us",category,"ec92132443684f808ee1a805ff13f433").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    List<HeadLine> headLineList = new ArrayList<>();
                    try {
                        JSONObject base = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray = base.getJSONArray("articles");

                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject articleObj = jsonArray.getJSONObject(index);
                            headLineList.add(new HeadLine(
                                    articleObj.getJSONObject("source").getString("name"),
                                    articleObj.isNull("title") ? "" : articleObj.getString("title"),
                                    articleObj.isNull("description") ? "" : articleObj.getString("description"),
                                    articleObj.isNull("url") ? "" : articleObj.getString("url"),
                                    articleObj.isNull("urlToImage") ? "" : articleObj.getString("urlToImage")
                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    headLinesLiveData.postValue(headLineList);
                }
                isApiFailure.postValue(response.isSuccessful() ? false : true);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                call.cancel();
                isApiFailure.postValue(true);
            }
        });
        return headLinesLiveData;
    }


    public MutableLiveData<Boolean> getIsApiFailure() {
        return isApiFailure;
    }


    boolean isNetWorkConnected() {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
