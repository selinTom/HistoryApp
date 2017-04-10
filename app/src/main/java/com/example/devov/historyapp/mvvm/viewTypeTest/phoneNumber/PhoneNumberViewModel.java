package com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber;

import android.util.Log;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by devov on 2016/10/25.
 */

public class PhoneNumberViewModel {
    private PhoneNumberModel phoneNumberModel;
    public PublishSubject<PhoneNumberInfo>phoneNumberObs;
    public PublishSubject<Throwable>excObs;
    public PhoneNumberViewModel(){
        phoneNumberModel=new PhoneNumberModel();
        phoneNumberObs=PublishSubject.create();
        excObs=PublishSubject.create();
    }
    public void getPhoneNumberInfo(long number){
       Observable<PhoneNumberInfo> observable=phoneNumberModel.getPhoneNumberInfo(number)
               .subscribeOn(Schedulers.newThread());
               observable.subscribe(
                       new Action1<PhoneNumberInfo>() {
                           @Override
                           public void call(PhoneNumberInfo phoneNumberInfo) {
                               Log.i("aaa","hashCode: "+observable.hashCode());
                               phoneNumberObs.onNext(phoneNumberInfo);
                           }
                       },
                       new Action1<Throwable>() {
                           @Override
                           public void call(Throwable throwable) {
                               Log.i("aaa","hashCode: "+observable.hashCode());
                               Log.i("aaa", "EEEEEEEEEEEEEEEEEEEEEE " + throwable.toString());
                               excObs.onNext(throwable);
                           }
                       }
               );
    }
}
