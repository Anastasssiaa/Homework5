package com.example.myapplication;

public class Soc {
    private String city; // описание
    private int degrees;        // изображение
    private int degreesForWeek;       // флажок

    public Soc(String city, int degrees, int degreesForWeek) {
        this.city = city;
        this.degrees = degrees;
        this.degreesForWeek = degreesForWeek;
    }

    public String getCity() {
        return city;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getDegreesForWeek() {
        return degreesForWeek;
    }
}
