package com.example.devov.historyapp.test.tPackage

/**
 * Created by devov on 2017/8/1.
 */
class Test4 {
    fun t1(lambda:(i:Int)->String){
        lambda(1);
    }
    init {
        t1 {
            i->
            return@t1 "aaa"
        }
    }
}