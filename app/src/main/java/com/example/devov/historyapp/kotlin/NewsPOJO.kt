package com.example.devov.historyapp.kotlin

/**
 * Created by devov on 2017/5/24.
 */
data class NewsPOJO(val reason:String?,val result:Result?) {
    data class Result(val stat:String,val data:List<Data>)
    data class Data(val title:String,val data:String,val author_name:String)
}