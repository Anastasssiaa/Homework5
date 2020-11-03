package com.example.myapplication;

import java.io.Serializable;

public class Parcel implements Serializable {
    private int degrees;
    private String cityName;
   // private int[] degreesForWeek;
    private Weather [] weather;

    public int getDegrees() {
        return degrees;
    }

    public String getCityName() {
        return cityName;
    }

    /*public Weather[] getWeather() {
        return weather;
    }

    /* public int[] getDegreesForWeek() {
        return degreesForWeek;
    }*/

    /*public Parcel( String cityName, int degrees, int[] degreesForWeek) {
        this.degrees = degrees;
        this.cityName = cityName;
        this.degreesForWeek = degreesForWeek;


    }*/

    public Parcel(int degrees, String cityName/*, Weather[] weather*/) {
        this.degrees = degrees;
        this.cityName = cityName;
        //this.weather = weather;
    }
}
