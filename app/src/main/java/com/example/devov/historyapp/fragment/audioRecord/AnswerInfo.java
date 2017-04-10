package com.example.devov.historyapp.fragment.audioRecord;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devov on 2016/12/15.
 */

public class AnswerInfo implements Parcelable {
    public String reason;
    public Result result;
    public String error_code;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reason);
        dest.writeParcelable(this.result, flags);
        dest.writeString(this.error_code);
    }

    public AnswerInfo() {
    }

    protected AnswerInfo(Parcel in) {
        this.reason = in.readString();
        this.result = in.readParcelable(Result.class.getClassLoader());
        this.error_code = in.readString();
    }

    public static final Parcelable.Creator<AnswerInfo> CREATOR = new Parcelable.Creator<AnswerInfo>() {
        @Override
        public AnswerInfo createFromParcel(Parcel source) {
            return new AnswerInfo(source);
        }

        @Override
        public AnswerInfo[] newArray(int size) {
            return new AnswerInfo[size];
        }
    };
}
