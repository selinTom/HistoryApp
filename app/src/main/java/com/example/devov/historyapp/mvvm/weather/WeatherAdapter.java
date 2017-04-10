package com.example.devov.historyapp.mvvm.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devov.historyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devov on 2016/10/20.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherItem>{
    private LayoutInflater layoutInflater;
    private List<WeatherLocalInfo> weatherInfoList;
    public WeatherAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        weatherInfoList=new ArrayList<>();
    }

    @Override
    public WeatherItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.weather_item,parent,false);
        return new WeatherItem(v);
    }
    public void addAllData(List<WeatherLocalInfo>infoList){
        weatherInfoList.addAll(infoList);
        notifyDataSetChanged();
    }
    public boolean addData(WeatherLocalInfo info){
        if(!weatherInfoList.contains(info)) {
            weatherInfoList.add(info);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return weatherInfoList.size();
    }

    @Override
    public void onBindViewHolder(WeatherItem holder, int position) {
        holder.title.setText(weatherInfoList.get(position).getPhrase());
        holder.explain.setText(weatherInfoList.get(position).getWeatherInfo().getResult().getChengyujs());
    }

    public static class WeatherItem extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView explain;
        public WeatherItem(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.weather_title);
            explain=(TextView)itemView.findViewById(R.id.weather_explain);
        }
    }
}
