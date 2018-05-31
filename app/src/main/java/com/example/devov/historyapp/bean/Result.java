package com.example.devov.historyapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devov on 2016/12/15.
 */

public class Result implements Parcelable {
    public String code;
    public String text;
    public String url;

    public Result() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.text);
        dest.writeString(this.url);
    }

    protected Result(Parcel in) {
        this.code = in.readString();
        this.text = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
