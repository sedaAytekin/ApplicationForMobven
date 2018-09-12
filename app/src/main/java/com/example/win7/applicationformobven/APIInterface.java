package com.example.win7.applicationformobven;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  APIInterface {

    @GET("weather")
    Call<BasicResponse> session(@Query("q") String cityName, @Query("APPID") String appId, @Query("units") String metric, @Query("lang") String language);


}
