package com.example.devov.historyapp.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.devov.historyapp.R

/**
 * Created by devov on 2017/6/2.
 */
class KotlinTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t3)
//        var t1=Test("1","1",1)
//        t1.a='S'
//        t1.b='S'
//        Log.i("aaa","a:${t1.a}   b:${t1.b}")
//        t1.c='b'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='J'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='j'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='B'
//        Log.i("aaa","c:${t1.c}")
//        var t2=Test(5,10);
//        var t3=Test(0,0);
//        var t4=t2;
//        Log.i("aaa","t2==t3:${t2==t3}     t2===t3:${t2===t3}")
//        Log.i("aaa","t2==t4:${t2==t4}     t2===t4:${t2===t4}")
//        when{
//            t2.i1 in 1..10->Log.i("aaa","when 1")
//            t2.i1 in 1..3->Log.i("aaa","when 2")
//            t2.i2 in 5..20->Log.i("aaa","when 3")
//        }
        Test.num=10;
//        t.random=5;
        Test.returnStr();
        var u:String="aaaa";
        u+"aaa"

    }
    fun test():String{
        var t=Test("a","a","a",1)
        var u=t.let {
            var a=it.fun2();
            Log.i("aaa","let method");
            return "aaa";
        }
    }
}