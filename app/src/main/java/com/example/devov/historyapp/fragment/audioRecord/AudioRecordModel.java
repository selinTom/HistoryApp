package com.example.devov.historyapp.fragment.audioRecord;

import com.example.devov.historyapp.interfaces.Constant;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

import static com.example.devov.historyapp.utils.xUtilsHelper.RetrofitJson;

/**
 * Created by devov on 2016/12/14.
 */

public class AudioRecordModel implements Constant {
    public Observable<VoiceInfo>getVoiceWords(String fileName){
        File f=new File(fileName);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/octet-stream"),f);
        MultipartBody.Part part=MultipartBody.Part.createFormData("file",f.getName(),requestBody);
        RequestBody key =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),VOICE_WORD_KEY);

        RequestBody rate =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),"16000");
        return RetrofitJson(VoiceApi.class,VOICE_WORD_URL).getWords(key,part,rate);
    }
    public Observable<AnswerInfo>getAnswerFromRobot(String word){
            return RetrofitJson(VoiceApi.class,ROBOT_URL).getAnswerFromRobot(word,ROBOT_KEY);
    }
}
