package com.example.devov.historyapp.mvvm.weather;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by devov on 2016/10/20.
 */

public interface WeatherApi {
    @FormUrlEncoded
    @POST("chengyu/query")
    Observable<WeatherInfo>getWeather(@Field("key")String key,@Field("word")String word);
}
