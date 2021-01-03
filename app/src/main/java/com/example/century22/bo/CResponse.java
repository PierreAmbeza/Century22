package com.example.century22.bo;

import com.squareup.moshi.Json;


/* Class to manage API response JSON*/
public class CResponse {

    @Json(name = "rates")
    private Rates rates;
    @Json(name = "base")
    private String base;
    @Json(name = "date")
    private String date;

    public Rates getRates() {
        return rates;
    }

}
