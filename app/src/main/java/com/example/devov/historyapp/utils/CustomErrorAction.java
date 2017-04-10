package com.example.devov.historyapp.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import rx.functions.Action1;

/**
 * Created by pianrong on 16/9/18.
 */
public class CustomErrorAction<T> implements Action1<T> {
    private Action1<T> onNext;
    private Action1<Throwable> onError;

    public CustomErrorAction(Action1<T> onNext, Action1<Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void call(T t) {
        Log.i("aaa","call function:"+t.toString());
        try {
            onNext.call(t);
        } catch (Exception e) {
            Log.i("aaa","catch exception");
            onError.call(e);
        }
    }

    public static final Action1<Throwable> recordError(Action1<Throwable> custom) {
        return throwable -> {
            Log.i("aaa","=================>>"+getErrorInfoFromException((Exception) throwable));
            if (custom != null) {

            }
        };
    }
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }
//    i->{
//        Log.i("aaa","I is :"+i);
//    }, e->{
//        Log.i("aaa","Exception!");
//    }

}