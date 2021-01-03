package com.example.century22.bo;
import com.squareup.moshi.Json;

/* Class from the JSON response from API call */

public class Rates {

    @Json(name = "USD")
    private Double uSD;

    public Double getUSD() {
        return uSD;
    }

}
