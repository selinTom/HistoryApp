package com.example.devov.historyapp.mvvm.weather;

import android.util.Log;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by devov on 2016/10/20.
 */

public class WeatherViewModel {
    private WeatherModel weatherModel;
    PublishSubject<WeatherInfo>weatherObs;
    public WeatherViewModel(){
        weatherModel=new WeatherModel();
        weatherObs=PublishSubject.create();
    }
    public void getWeatherInfo(String city){
        Observable<WeatherInfo>observable=weatherModel.getWeatherInfo(city)
                .subscribeOn(Schedulers.newThread());
              observable.subscribe(
                        new Action1<WeatherInfo>() {
                            @Override
                            public void call(WeatherInfo weatherInfo) {
                                Log.i("aaa","Hashcode:"+observable.hashCode());
                                weatherObs.onNext(weatherInfo);
                            }
                        },
                        new Action1<Throwable>(){
                            @Override
                            public void call(Throwable throwable) {
                                Log.i("aaa","[][][]"+throwable.toString());
                                Log.i("aaa","Hashcode:"+observable.hashCode());
                            }
                        }
                );
    }
}
