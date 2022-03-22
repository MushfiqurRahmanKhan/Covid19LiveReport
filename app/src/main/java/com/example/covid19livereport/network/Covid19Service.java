package com.example.covid19livereport.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Covid19Service {
    public static Covid19ServiceApi getService(){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://corona.lmao.ninja/v2/countries/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(Covid19ServiceApi.class);
    }
}
