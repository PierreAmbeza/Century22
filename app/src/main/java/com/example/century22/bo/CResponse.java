package com.example.century22.bo;

import com.squareup.moshi.Json;

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

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
