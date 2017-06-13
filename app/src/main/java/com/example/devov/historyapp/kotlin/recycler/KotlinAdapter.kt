package com.example.devov.historyapp.kotlin.recycler

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.devov.historyapp.kotlin.NewsPOJO

/**
 * Created by devov on 2017/5/23.
 */
class KotlinAdapter( val listener:(View)->Unit) : Adapter<RecyclerView.ViewHolder>() {
    var data: NewsPOJO?=null;
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
//        (holder as ViewHolder).initViewHolder(data?.result!!.data[position].title);
         with(data?.result!!.data.get(position)){
            (holder as ViewHolder).initViewHolder(title);
        }
    }
    override fun getItemCount(): Int = data?.result?.data?.size ?: 0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(TextView(parent.context),listener)
    class ViewHolder( itemView: View,listener:(View)->Unit) : RecyclerView.ViewHolder(itemView) {
        init{
            this.itemView.setOnClickListener(listener)
        }
        fun initViewHolder(info :String){
            (itemView as TextView).text=info;
        }
    }
}