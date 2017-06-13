package com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber;

import rx.Observable;
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
                       data-> phoneNumberObs.onNext(data),
                       exc->  excObs.onNext(exc)
               );
    }
}
