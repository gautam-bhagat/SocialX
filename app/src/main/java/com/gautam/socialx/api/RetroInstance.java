package com.gautam.socialx.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance
{
    public static String BASE_URL = "https://newsapi.org/v2/";
    public static String API_KEY = "3c60bd3bfda74e4a96f8933ed4ca0639";
    private static Retrofit retrofit;

    public static Retrofit getRetroClient(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
