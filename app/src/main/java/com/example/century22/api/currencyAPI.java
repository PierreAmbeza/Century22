package com.example.century22.api;

import com.example.century22.bo.CResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface currencyAPI {

        //Search the currency in addition with the base url
        @GET("latest")//We look for the current exchange rate between € and US$
        Call<CResponse> getCurrency(@Query("symbols") String base);
}
