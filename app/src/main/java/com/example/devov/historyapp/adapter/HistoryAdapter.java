package com.example.devov.historyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.devov.historyapp.Model.HistoryData;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.imageload.ImageLoaderUtil;
import com.example.devov.historyapp.utils.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by devov on 2016/10/14.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryItemView> implements ItemTouchHelperAdapter {
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(results,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }
    public interface OnItemClickListener{
       public void onItemClick(int position);
   }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }
    private List<HistoryData.Result> results;
    public void addData(List<HistoryData.Result> results){
        this.results=Stream.of(results).filter(v->!("".equals(v.getPic())))
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }
    public List<HistoryData.Result>getData(){
        return results;
    }
    @Override
    public HistoryItemView onCreateViewHolder(ViewGroup parent,  int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new HistoryItemView(view);
    }

    @Override
    public void onBindViewHolder(HistoryItemView holder,final int position) {
//        displayImage(holder.imageView,results.get(position).getPic());
        ImageLoaderUtil.setIsCircle(true);
        ImageLoaderUtil.setImageView(holder.imageView,results.get(position).getPic());
        holder.textView.setText(results.get(position).getTitle());
        if(mOnItemClickListener!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(results==null)
            return 0;
        return results.size();
    }

    public static class HistoryItemView extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public HistoryItemView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.history_item_iv );
            textView= (TextView) itemView.findViewById(R.id.history_item_tv);
        }

    }

}
