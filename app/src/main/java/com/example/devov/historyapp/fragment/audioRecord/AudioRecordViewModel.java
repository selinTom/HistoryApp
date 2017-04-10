package com.example.devov.historyapp.fragment.audioRecord;

import android.util.Log;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by devov on 2016/12/14.
 */

public class AudioRecordViewModel {
    private AudioRecordModel audioRecordModel;
    public PublishSubject<VoiceInfo>voiceWordObs;
    public PublishSubject<Throwable>throwableObs;
    public PublishSubject<AnswerInfo>answerInfoObs;
    public AudioRecordViewModel(){
        audioRecordModel=new AudioRecordModel();
        voiceWordObs=PublishSubject.create();
        throwableObs=PublishSubject.create();
        answerInfoObs=PublishSubject.create();
    }
    public void getVoiceWord(String fileName){
        audioRecordModel.getVoiceWords(fileName)
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        data->{
                            voiceWordObs.onNext(data);
                        },
                        e->{
                            Log.i("aaa",e.toString());
                            throwableObs.onNext(e);
                        }
                );
    }
    public void getAnswerFromRobot(String word){
        audioRecordModel.getAnswerFromRobot(word)
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        data->{
                            answerInfoObs.onNext(data);
                        },
                        e->{
                            Log.i("aaa",e.toString());
                            throwableObs.onNext(e);
                        }
                );
    }
}
