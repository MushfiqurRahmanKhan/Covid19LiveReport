package com.example.covid19livereport.viewmodels;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19livereport.models.Covid19ResponseModel;
import com.example.covid19livereport.network.Covid19Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Covid19ViewModel extends ViewModel {
    private Location location;
    private MutableLiveData<Covid19ResponseModel> responseInfoLiveData = new MutableLiveData<>();

    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private String city = "bangladesh";
    public void setCity(String city){
        this.city = city;
    }

    public void loadData(){
        fetchResponseData();
    }


    private void fetchResponseData(){
        final String endUrl = String.format("%s?yesterday=true&strict=true&query", city);
        Covid19Service.getService().getCurrentData(endUrl).enqueue(new Callback<Covid19ResponseModel>() {
            @Override
            public void onResponse(Call<Covid19ResponseModel> call, Response<Covid19ResponseModel> response) {
                if (response.code()==200){
                    Log.e("weather_test", ""+response.code() );
                    responseInfoLiveData.postValue(response.body());
                } else if (response.code() == 404){
                    errorMessageLiveData.postValue(response.message());
                    Log.e("weather_test", ""+response.code() );
                }
            }

            @Override
            public void onFailure(Call<Covid19ResponseModel> call, Throwable t) {
                Log.e("weather_test", ""+t );
            }
        });

    }



    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public MutableLiveData<Covid19ResponseModel> getResponseInfoLiveData() {
        return responseInfoLiveData;
    }



}
