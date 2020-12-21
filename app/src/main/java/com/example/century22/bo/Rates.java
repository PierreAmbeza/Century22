package com.example.century22.bo;
import com.squareup.moshi.Json;

public class Rates {

    @Json(name = "USD")
    private Double uSD;

    public Double getUSD() {
        return uSD;
    }

    public void setUSD(Double uSD) {
        this.uSD = uSD;
    }

}
