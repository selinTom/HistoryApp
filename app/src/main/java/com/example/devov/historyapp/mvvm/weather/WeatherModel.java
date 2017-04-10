package com.example.devov.historyapp.mvvm.weather;

import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.utils.xUtilsHelper;

import rx.Observable;

/**
 * Created by devov on 2016/10/20.
 */

public class WeatherModel implements Constant{
        public Observable<WeatherInfo>getWeatherInfo(String word){
            return xUtilsHelper.RetrofitJson(WeatherApi.class,WEATRER_URL).getWeather(WEATHER_KEY,word);
        }
}
