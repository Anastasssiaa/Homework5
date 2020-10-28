package com.example.myapplication;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class SocSource implements SocialDataSource {

    private List<Soc> dataSource;   // строим этот источник данных
    private Resources resources;    // ресурсы приложения

    public SocSource(Resources resources) {
        dataSource = new ArrayList<>(6);
        this.resources = resources;
    }

    public SocSource init(){
        // строки описаний из ресурсов
        String[] cities = resources.getStringArray(R.array.cities);
        int[] degrees = getDegreesArray();
        int[] degreesForWeek = resources.getIntArray(R.array.degreesForWeek);
        // заполнение источника данных
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < degreesForWeek.length; j++) {
                dataSource.add(new Soc(cities[i], degrees[i], degreesForWeek[j]));

            }
        }
        return this;
    }

    public Soc getSoc(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    // Механизм вытаскивания идентификаторов картинок (к сожалению просто массив не работает)
    private int[] getDegreesArray(){
        TypedArray degrees = resources.obtainTypedArray(R.array.degrees);
        int length = degrees.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = degrees.getResourceId(i, 0);
        }
        return answer;
    }
}
