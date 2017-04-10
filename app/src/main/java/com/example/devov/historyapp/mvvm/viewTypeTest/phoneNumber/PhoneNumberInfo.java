package com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by devov on 2016/10/25.
 */

public class PhoneNumberInfo implements Parcelable {
    private String resultcode;
    private String reason;
    private Result result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    PhoneNumberInfo() {
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(resultcode);
        parcel.writeString(reason);
        parcel.writeInt(error_code);
        parcel.writeParcelable((Parcelable) this.result,i);
    }
    public static final Parcelable.Creator<PhoneNumberInfo> CREATOR=new Parcelable.Creator<PhoneNumberInfo>(){
        @Override
        public PhoneNumberInfo createFromParcel(Parcel parcel) {
            PhoneNumberInfo phoneNumberInfo=new PhoneNumberInfo();
            phoneNumberInfo.resultcode=parcel.readString();
            phoneNumberInfo.reason=parcel.readString();
            try {
                phoneNumberInfo.result = parcel.readParcelable(Class.forName(new Result().getClass().getName()).getClassLoader());
            }catch(ClassNotFoundException e){
                Log.i("aaa","*****"+e);
            }
            phoneNumberInfo.error_code=parcel.readInt();
            return phoneNumberInfo;
        }

        @Override
        public PhoneNumberInfo[] newArray(int i) {
            return new PhoneNumberInfo[i];
        }
    };

}
