package com.sample.newsapi.datarepository.onlinerepo;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("top-headlines")
    Call<Object> fetchHeadlines(@Query("country") String country, @Query("category") String category,
                                @Query("apiKey") String apikey);
}
