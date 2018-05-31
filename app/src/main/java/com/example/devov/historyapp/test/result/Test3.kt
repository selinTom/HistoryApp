package com.example.devov.historyapp.test.result

import com.example.devov.historyapp.test.TxChild.getInt
import retrofit2.Retrofit

/**
 * Created by devov on 2017/7/13.
 */
class Test3 {
    var i:Int?=null
    var retrofit:Retrofit?=null
    inline fun <reified TTT> ReifiedTest(){
        retrofit=Retrofit.Builder().build()
        retrofit?.create(TTT::class.java)
        5.add100()
    }
     fun <T>ReifiedTest2(clazz: Class<T>){
        retrofit=Retrofit.Builder().build()
        retrofit?.create(clazz)
//         list0.add()
//         list.add()
    }
    val list:MutableList<Int> =mutableListOf()
    val list0:List<Int> =listOf()




}
open class Tx(var i:Int){
    fun add1(){
        i++
    }
}
object TxChild : Tx(getInt()){
    fun init(){}
    fun getInt()=5
}

fun Int.add100()=this+1
