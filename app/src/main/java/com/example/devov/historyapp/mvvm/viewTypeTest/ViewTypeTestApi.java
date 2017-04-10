package com.example.devov.historyapp.mvvm.viewTypeTest;

import com.example.devov.historyapp.mvvm.viewTypeTest.ip.IpAddressInfo;
import com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber.PhoneNumberInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by devov on 2016/10/25.
 */

public interface ViewTypeTestApi {
    @GET("ip/ip2addr")
    Observable<IpAddressInfo>getIpLocation(@Query("ip") String ip, @Query("key") String key);
    @GET("mobile/get")
    Observable<PhoneNumberInfo>getPhoneNumberInfo(@Query("phone") long phone,@Query("key") String key);
}
