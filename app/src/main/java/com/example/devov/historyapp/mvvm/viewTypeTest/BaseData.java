package com.example.devov.historyapp.mvvm.viewTypeTest;

import com.example.devov.historyapp.mvvm.viewTypeTest.ip.IpAddressInfo;
import com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber.PhoneNumberInfo;

/**
 * Created by devov on 2016/10/26.
 */

public class BaseData {
    public static final int IP_DATA=0;
    public static final int NUMBER_DATA=1;
    private int tag;
    private IpAddressInfo ipAddressInfo;
    private PhoneNumberInfo phoneNumberInfo;
    private String inputData;
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public IpAddressInfo getIpAddressInfo() {
        return ipAddressInfo;
    }

    public void setIpAddressInfo(IpAddressInfo ipAddressInfo) {
        this.ipAddressInfo = ipAddressInfo;
    }

    public PhoneNumberInfo getPhoneNumberInfo() {
        return phoneNumberInfo;
    }

    public void setPhoneNumberInfo(PhoneNumberInfo phoneNumberInfo) {
        this.phoneNumberInfo = phoneNumberInfo;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }
}
