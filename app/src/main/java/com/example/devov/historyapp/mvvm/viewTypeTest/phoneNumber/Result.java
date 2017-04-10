package com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devov on 2016/10/25.
 */

public class Result implements Parcelable{
    private String province;
    private String city;
    private String areacode;
    private String zip;
    private String company;
    private String card;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.province);
        parcel.writeString(this.city);
        parcel.writeString(this.areacode);
        parcel.writeString(this.zip);
        parcel.writeString(this.company);
        parcel.writeString(this.card);
    }
    public static final Parcelable.Creator<Result>CREATOR=new Parcelable.Creator<Result>(){
        @Override
        public Result createFromParcel(Parcel parcel) {
            Result result=new Result();
            result.province=parcel.readString();
            result.city=parcel.readString();
            result.areacode=parcel.readString();
            result.zip=parcel.readString();
            result.company=parcel.readString();
            result.card=parcel.readString();
            return result;
        }

        @Override
        public Result[] newArray(int i) {
            return new Result[i];
        }
    };
}
