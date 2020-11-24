package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface openWeather {

    @GET("data/2.5/weather");

    Call<Weather> loadWeather(@Query("q") String city, @Query("appid") String keyApi);



}
