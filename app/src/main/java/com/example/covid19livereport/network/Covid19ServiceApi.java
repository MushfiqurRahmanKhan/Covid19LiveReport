package com.example.covid19livereport.network;

import com.example.covid19livereport.models.Covid19ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Covid19ServiceApi {
    @GET
    Call<Covid19ResponseModel> getCurrentData(@Url String endUrl);
}
