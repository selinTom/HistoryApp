package com.example.devov.historyapp.mvvm.viewTypeTest.ip;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by devov on 2016/10/25.
 */

public class IpAddressInfo implements Parcelable {
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

    IpAddressInfo() {
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
    public static final Parcelable.Creator<IpAddressInfo> CREATOR=new Parcelable.Creator<IpAddressInfo>(){
        @Override
        public IpAddressInfo createFromParcel(Parcel parcel) {
            IpAddressInfo ipAddressInfo=new IpAddressInfo();
            ipAddressInfo.resultcode=parcel.readString();
            ipAddressInfo.reason=parcel.readString();
            try {
                ipAddressInfo.result = parcel.readParcelable(Class.forName(new Result().getClass().getName()).getClassLoader());
            }catch(ClassNotFoundException e){
                Log.i("aaa","++++"+e);
            }
            ipAddressInfo.error_code=parcel.readInt();
            return ipAddressInfo;
        }

        @Override
        public IpAddressInfo[] newArray(int i) {
            return new IpAddressInfo[i];
        }
    };

}
