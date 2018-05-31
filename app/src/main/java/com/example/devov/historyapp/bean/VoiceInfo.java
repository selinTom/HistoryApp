package com.example.devov.historyapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devov on 2016/12/14.
 */

public class VoiceInfo implements Parcelable {
    public String reason;
    public String error_code;
    public String result;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reason);
        dest.writeString(this.error_code);
        dest.writeString(this.result);
    }

    public VoiceInfo() {
    }

    protected VoiceInfo(Parcel in) {
        this.reason = in.readString();
        this.error_code = in.readString();
        this.result = in.readString();
    }

    public static final Parcelable.Creator<VoiceInfo> CREATOR = new Parcelable.Creator<VoiceInfo>() {
        @Override
        public VoiceInfo createFromParcel(Parcel source) {
            return new VoiceInfo(source);
        }

        @Override
        public VoiceInfo[] newArray(int size) {
            return new VoiceInfo[size];
        }
    };
}
