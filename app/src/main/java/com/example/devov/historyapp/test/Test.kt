package com.example.devov.historyapp.test

import android.util.Log
import kotlin.properties.Delegates

/**
 * Created by devov on 2017/6/2.
 */
class Test(  i1:Int,var i2:Int) :AA,BB{
    companion object{
        @JvmField
        public var num=5
        @JvmStatic
        fun returnStr()="Hello Kotlin";
    }
    private var random=10;
    var string:String? = null
    override fun fun2(): Int {
        Log.i("aaa","fun2");
        return 0;
    }
    fun fun3(){
        Log.i("aaa","fun3");
    }


    constructor(  str1:String,str2:String,i:Int) : this(i,i) {
            Log.i("aaa","3 params");
    }
    constructor(str1:String,str2:String,str3:String,i:Int):this(str1,str2,i){
        Log.i("aaa","4 params")
    }
    init {
        Log.i("aaa","init  $i1")
    }

    override var str="";
    var a:Char='a'

    var b:Char='a'
        get()=field+1
        set(value){
            field=value;
        }
    var c by Delegates.vetoable('A'){
        d,old,new->
        new >='G'
    }
}
interface  AA{
    var str:String;
    fun fun1():Int=1;
}
interface BB{
    fun fun2(): Int
}