package com.example.myapplication;

import java.io.Serializable;

public class Parcel implements Serializable {
    private int degrees;
    private String cityName;

    public int getDegrees() {
        return degrees;
    }

    public String getCityName() {
        return cityName;
    }

    public Parcel(int degrees, String cityName) {
        this.degrees = degrees;
        this.cityName = cityName;


    }
}
