package com.example.devov.historyapp.mvvm.viewTypeTest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devov.historyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devov on 2016/10/26.
 */

public class ViewTypeTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseData> baseDataList;
    private LayoutInflater layoutInflater;
    public ViewTypeTestAdapter(Context context){
        baseDataList=new ArrayList<>();
        layoutInflater=LayoutInflater.from(context);
    }
    public boolean addData(BaseData baseData){
        for(int i=0;i<baseDataList.size();i++)
            if(baseData.getInputData().equals(baseDataList.get(i).getInputData()))
                return false;
        baseDataList.add(baseData);
        notifyDataSetChanged();
        return true;
    }
    public void setData(List<BaseData> baseDatas){
        baseDataList=new ArrayList<>(baseDatas);
    }
    public List<BaseData>getData(){
        return baseDataList;
    }
    @Override
    public int getItemViewType(int position) {
        Log.i("aaa","Position:"+
                position);
        return baseDataList.get(position).getTag();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        if(viewType==BaseData.IP_DATA){
            v=layoutInflater.inflate(R.layout.ip_item,parent,false);
            return new IpViewHolder(v);
        }
        else if(viewType==BaseData.NUMBER_DATA){
            v=layoutInflater.inflate(R.layout.phone_number_item,parent,false);
            return new NumberViewHolder(v);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof IpViewHolder){
            ((IpViewHolder) holder).titleTextView.setText(baseDataList.get(position).getInputData());
            ((IpViewHolder) holder).areaTextView.setText(baseDataList.get(position).getIpAddressInfo().getResult().getArea());
            ((IpViewHolder) holder).locationTextView.setText(baseDataList.get(position).getIpAddressInfo().getResult().getLocation());
        }
        else if(holder instanceof NumberViewHolder){
            ((NumberViewHolder) holder).titleTextView.setText(baseDataList.get(position).getInputData());
            ((NumberViewHolder) holder).areaTextView.setText(baseDataList.get(position).getPhoneNumberInfo().getResult().getProvince()+"  "
            +baseDataList.get(position).getPhoneNumberInfo().getResult().getCity());
            ((NumberViewHolder) holder).cardTextView.setText(baseDataList.get(position).getPhoneNumberInfo().getResult().getCard());
            ((NumberViewHolder) holder).companyTextView.setText(baseDataList.get(position).getPhoneNumberInfo().getResult().getCompany());

        }
    }

    @Override
    public int getItemCount() {
        return baseDataList.size();
    }
    public static class IpViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView areaTextView;
        private TextView locationTextView;
        public IpViewHolder(View itemView){
            super(itemView);
            titleTextView=(TextView)itemView.findViewById(R.id.ip_tv);
            areaTextView=(TextView)itemView.findViewById(R.id.ip_area);
            locationTextView=(TextView)itemView.findViewById(R.id.ip_location);
        }
    }
    public static class NumberViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView areaTextView;
        private TextView cardTextView;
        private TextView companyTextView;
        public NumberViewHolder(View itemView){
            super(itemView);
            titleTextView=(TextView)itemView.findViewById(R.id.number_tv);
            areaTextView=(TextView)itemView.findViewById(R.id.number_area);
            cardTextView=(TextView)itemView.findViewById(R.id.number_card);
            companyTextView=(TextView)itemView.findViewById(R.id.number_company);
        }
    }
}
