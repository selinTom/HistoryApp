package com.example.devov.historyapp.mvvm.viewTypeTest.ip;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devov on 2016/10/25.
 */

public class Result implements Parcelable {
    private String area;
    private String location;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.area);
        parcel.writeString(this.location);
    }
    public static final Parcelable.Creator<Result> CREATOR=new Parcelable.Creator<Result>(){
        @Override
        public Result createFromParcel(Parcel parcel) {
            Result result=new Result();
            result.area=parcel.readString();
            result.location=parcel.readString();
            return result;
        }

        @Override
        public Result[] newArray(int i) {
            return new Result[i];
        }
    };
}
