package com.example.devov.historyapp.test.package1

import android.util.Log

/**
 * Created by devov on 2017/6/13.
 */

class Test1 {
    fun t() {
        t0(Test(0, 0))
    }
    companion object{
        public inline fun fun3(char:Char, action:(i:Int)->String):String=action(char.toInt())

    }
    fun func()=ArrayList<Any>()
    fun t0(t: Test) {
        t.fun2();
        this.t;
        Test.fun4()
        Test(0, 0).fun4()
        for(a in 0..100 step 2) {}
        }
    var t= Test(0, 0).fun3()
    fun Test.Companion.fun4(){
        Log.i("aaa","Test.fun4");
        a ='p';
//        Test1().fun3("AA"){
//            var s=it+1;
//            s.toString();
//        }
    }

    fun tt(){
        fun innerFunc(i:Int){
            for (a in 0..1000){
                if(a==i)return
                else
                    Log.i("aaa","a is $a")
            }
        }
        innerFunc(20)
        Log.i("aaa","all right")
    }
    /*
    da,lkdsldkmasddd;ldsa;dsa
     */
}
fun Test.fun4(){
    Log.i("aaa","Test.fun4");
    b='p';
}
fun Test.Companion.fun4(){
    var xx=10;
    Log.i("aaa","Test.fun4");
    a ='p';
//    Test1().fun3("AA"){
//        xx->(xx+1).toString()
//    }
}