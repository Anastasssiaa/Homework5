package com.example.myapplication;

import java.io.Serializable;

public class Parcel implements Serializable {
    private int degrees;
    private String cityName;
    private int[] degreesForWeek;

    public int getDegrees() {
        return degrees;
    }

    public String getCityName() {
        return cityName;
    }

    public int[] getDegreesForWeek() {
        return degreesForWeek;
    }

    public Parcel(int degrees, String cityName, int[] degreesForWeek) {
        this.degrees = degrees;
        this.cityName = cityName;
        this.degreesForWeek = degreesForWeek;


    }
}
