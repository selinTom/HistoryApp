package com.example.devov.historyapp.fragment.audioRecord;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by devov on 2016/12/14.
 */

public interface VoiceApi  {
    @Multipart
    @POST("voice_words/getWords")
    Observable<VoiceInfo>getWords(@Part("key") RequestBody key, @Part MultipartBody.Part file, @Part("rate")RequestBody rate);
    @GET("robot/index")
    Observable<AnswerInfo>getAnswerFromRobot(@Query("info")String info,@Query("key")String key);
    @GET("/")
    Call<ResponseBody>heheda(@QueryMap Map<String,String>map);
}
