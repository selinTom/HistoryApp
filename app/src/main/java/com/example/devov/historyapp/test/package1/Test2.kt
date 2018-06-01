package com.example.devov.historyapp.test.package1

import java.io.IOException

/**
 * Created by devov on 2017/7/12.
 */
class Test2 {
    internal interface Source<out T> {
        fun nextT(): T
    }

    internal fun demo(strs: Source<String>) {
        val objects = strs // ！！！在 Java 中不允许
    }
    fun copy(from: Array< Any>, to: Array<out Any>) {
        assert(from.size == to.size)
//        for (i in from.indices)
//            to[i] = from[i]
        }
    fun r(body:(Int,String,Char)->Int){}
    fun test(){
        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any = Array<Any>(3) { "" }
        any.set(0,"a")
        any.set(1,"a")
        any.set(2,"a")
        copy(any, ints) // 错误：期望 (Array<Any>, Array<Any>)

    }
    fun t(){
        5.add100()
        r{
            iiiiiii,_,_->iiiiiii
        }
        Singleton.add2()
        val singleton= Singleton
        Test(0, 0).random=10
        throwException()
    }

    @Throws(IOException::class)
    fun throwException()  {
        throw Exception()
    }
}
object Singleton{
    var i=0;
    fun add2(){
        i.add2()

    }
    fun Int.add2()=this+2
}
fun Int.add2()=this+3
