package com.gautam.socialx.api;

import com.gautam.socialx.model.NewsModel;
import com.gautam.socialx.model.NewsResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService
{
    @GET("everything")
    Call<NewsResponse> getNews(
            @Query("q") String q,
            @Query("apiKey") String apiKey,
            @Query("sortBy") String sortBy,
            @Query("language") String lang

    );
}
