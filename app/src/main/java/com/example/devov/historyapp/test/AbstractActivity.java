package com.example.devov.historyapp.test;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Field;

import static com.example.devov.historyapp.utils.xUtilsHelper.XLogE;

/**
 * Created by devov on 2017/3/20.
 */

public abstract class AbstractActivity extends Activity {
    public String str="hahahahaha";
    public void getName(){
        try {
            Field field=this.getClass().getDeclaredField("str");
            field.setAccessible(true);
//            Log.i("aaa",field.get(this).toString()+"  "+this.getClass().getName());
//            Log.i("aaa", "IsEqual:"+String.valueOf(field.get(this)==this.str));
//            Log.i("aaa",this.getClass().getField("str").get(this).toString()+"  "+this.getClass().getName());
            Log.i("aaa","Super"+String.valueOf(this.hashCode()));
            Log.i("aaa","Super str:"+String.valueOf(this.str.hashCode()));
        } catch (Exception e) {
           XLogE(e);
        }
    }
}
