package com.example.devov.historyapp;

import android.app.Application;
import android.util.Log;

import com.example.devov.historyapp.utils.common.LocalImageHelper;

import org.xutils.x;

/**
 * Created by devov on 2016/10/13.
 */

public class BaseApplication extends Application {
    public  static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        LocalImageHelper.getInstance().init(this);
        x.Ext.init(this);
        instance=this;
        Log.i("aaa","H1");
    }
    public static synchronized Application getInstance() {
        return instance;
    }
}
