package com.example.devov.historyapp.mvvm.weather;

import android.util.Log;

/**
 * Created by devov on 2016/10/21.
 */

public class WeatherLocalInfo {
    private String phrase;
    private WeatherInfo weatherInfo;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeatherLocalInfo other = (WeatherLocalInfo) obj;
        if(this.getPhrase().equals(other.getPhrase())){
            Log.i("aaa","相等！");
            Log.i("aaa",this.getPhrase()+"   "+other.getPhrase());
            return true;
        }
        Log.i("aaa","不相等");
        return false;
    }
}