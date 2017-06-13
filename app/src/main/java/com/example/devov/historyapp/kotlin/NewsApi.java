package com.example.devov.historyapp.kotlin;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by devov on 2017/5/24.
 */

public class NewsApi {
    public interface NEWS{
        @GET("toutiao/index")
        Observable<NewsPOJO> getNews(@Query("key") String ip, @Query("type") String key);
    }
}
