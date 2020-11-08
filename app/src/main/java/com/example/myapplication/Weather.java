package com.example.myapplication;

public class Weather {

    private String main;
    private String description;

    public Weather(String main, String description) {
        this.main = main;
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }
}
