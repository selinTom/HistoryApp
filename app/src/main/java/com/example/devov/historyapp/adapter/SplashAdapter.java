package com.example.devov.historyapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devov.historyapp.R;

/**
 * Created by devov on 2016/12/27.
 */

public class SplashAdapter extends RecyclerView.Adapter {
    public interface OnClickListener{
        void onClick(int position);
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
    private OnClickListener onClickListener;
    private int[] icons={R.drawable.news_icon,R.drawable.history_icon,R.drawable.robot_icon,R.drawable.search_icon};
    private String[] titles={"新闻","历史","机器人","号码网站查询"};
    private LayoutInflater layoutInflater;
    public SplashAdapter(Context context){
        layoutInflater=layoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.splash_item,parent,false);
        return new SplashHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SplashHolder)holder).iv.setImageResource(icons[position]);
        ((SplashHolder)holder).tv.setText(titles[position]);
        ((SplashHolder)holder).iv.setOnClickListener(v->onClickListener.onClick(position));

    }

    @Override
    public int getItemCount() {
        return icons.length;
    }
    private class SplashHolder extends RecyclerView.ViewHolder{
        View baseView;
        ImageView iv;
        TextView tv;
        public SplashHolder(View itemView) {
            super(itemView);
            baseView=itemView.findViewById(R.id.base_view);
            iv= (ImageView) itemView.findViewById(R.id.iv);
            tv=(TextView)itemView.findViewById(R.id.tv);
        }
    }
}
