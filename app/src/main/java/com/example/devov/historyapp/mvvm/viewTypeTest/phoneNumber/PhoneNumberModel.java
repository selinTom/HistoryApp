package com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber;

import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.mvvm.viewTypeTest.ViewTypeTestApi;
import com.example.devov.historyapp.utils.xUtilsHelper;

import rx.Observable;

/**
 * Created by devov on 2016/10/25.
 */

public class PhoneNumberModel implements Constant {
    public Observable<PhoneNumberInfo>getPhoneNumberInfo(long number){
        return xUtilsHelper.RetrofitJson(ViewTypeTestApi.class,IP_URL).getPhoneNumberInfo(number,PHONE_NUMBER_KEY);
    }
}
