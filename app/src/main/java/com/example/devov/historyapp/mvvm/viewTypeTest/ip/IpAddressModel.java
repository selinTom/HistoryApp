package com.example.devov.historyapp.mvvm.viewTypeTest.ip;

import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.mvvm.viewTypeTest.ViewTypeTestApi;
import com.example.devov.historyapp.utils.xUtilsHelper;

import rx.Observable;

/**
 * Created by devov on 2016/10/25.
 */

public class IpAddressModel implements Constant{
    public Observable<IpAddressInfo>getIpAddressLocation(String url){
        return xUtilsHelper.RetrofitJson(ViewTypeTestApi.class,IP_URL).getIpLocation(url,IP_KEY);
    }
}
