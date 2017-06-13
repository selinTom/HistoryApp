package com.example.devov.historyapp.kotlin.recycler;
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.devov.historyapp.R
import com.example.devov.historyapp.kotlin.NewsApi
import com.example.devov.historyapp.utils.xUtilsHelper
import kotlinx.android.synthetic.main.activity_kotlin.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by devov on 2017/5/23.
 */
class KotlinActivity : AppCompatActivity(){
    companion object{
        val baseUrl="http://v.juhe.cn/";
        val key="d5dce183a59a63aa71d454697eca23e3";
        val type= "top";
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin);
        recycler_view.layoutManager= LinearLayoutManager(this);
        recycler_view.adapter= KotlinAdapter{Toast.makeText(this,"AAA",Toast.LENGTH_LONG).show()};
        getData();
    }
    fun getData(){
        xUtilsHelper.RetrofitJson(NewsApi.NEWS ::class.java, baseUrl).getNews(key,type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {data->
                            (recycler_view.adapter as KotlinAdapter).data=data;
                            recycler_view.adapter.notifyDataSetChanged();
                        },{
                    exc->
                    Log.i("aaa",exc.toString())
                }
                )
    }
}