package com.example.devov.historyapp.test.child1;

import android.util.Log;

import com.example.devov.historyapp.utils.xUtilsHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by devov on 2017/7/11.
 */

public class TClass {
    public List<String>infos=new ArrayList<>();
    static ExecutorService executor = Executors.newSingleThreadExecutor();
    public TClass(){
        infos.add("a");
        infos.add("b");
        infos.add("c");
        infos.add("d");
        infos.add("e");
        infos.add("f");
        infos.add("g");
    }
    public static void test(){
//        Log.i("aaa",)

        try {
            FutureTask<BigInteger>futureTask=new FutureTask<BigInteger>(new Callable<BigInteger>() {
                @Override
                public BigInteger call() throws Exception {
                    Log.i("aaa","futureTask: current thread is:"+Thread.currentThread().getName());
                    return fibc(BigInteger.valueOf(10));
                }
            });
            executor.submit(futureTask);
//            executor.shutdown();
        } catch (Exception e) {
            xUtilsHelper.XLogE(e);
        }
    }
    static BigInteger fibc(BigInteger num) {
        if (num .equals( BigInteger.ZERO)) {
            return BigInteger.ZERO;
        }
        if (num .equals( BigInteger.ONE)) {
            return BigInteger.ONE;
        }
        return fibc(num.subtract(BigInteger.ONE)).add(fibc(num.subtract(BigInteger.valueOf(2))));
    }

}
