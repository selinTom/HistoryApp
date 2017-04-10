package com.example.devov.historyapp.mvvm.viewTypeTest.ip;

import android.util.Log;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by devov on 2016/10/25.
 */

public class IpAddressViewModel {
    private IpAddressModel ipAddressModel;
    public PublishSubject<IpAddressInfo> ipAddressObs;
    public IpAddressViewModel(){
        ipAddressModel=new IpAddressModel();
        ipAddressObs=PublishSubject.create();
    }
    public void getIpAddressLocation(String url){
        ipAddressModel.getIpAddressLocation(url)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<IpAddressInfo>() {
                    @Override
                    public void call(IpAddressInfo ipAddressInfo) {
                        ipAddressObs.onNext(ipAddressInfo);
                    }
                },
                        new Action1<Throwable>(){
                            @Override
                            public void call(Throwable throwable) {
                                Log.e("aaa", "][][][" + throwable.toString());
                            }
                        });
    }
}
