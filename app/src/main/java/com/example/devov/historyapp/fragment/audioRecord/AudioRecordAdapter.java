package com.example.devov.historyapp.fragment.audioRecord;

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
 * Created by devov on 2016/12/15.
 */

public class AudioRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AskAndAnswerInfo>infoList;
    private LayoutInflater layoutInflater;
    public AudioRecordAdapter(Context context){
        infoList=new ArrayList<>();
        layoutInflater=LayoutInflater.from(context);
    }
    public synchronized void addData(AskAndAnswerInfo info){
        infoList.add(info);
        notifyItemInserted(getItemCount());
//        Log.i("aaa","addData");
    }
    @Override
    public int getItemViewType(int position) {
        int type=infoList.get(position).getType();
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        if(viewType==AskAndAnswerInfo.ME){
            v=layoutInflater.inflate(R.layout.ask_item,parent,false);
        }
        else if(viewType==AskAndAnswerInfo.ROBOT){
            v=layoutInflater.inflate(R.layout.answer_item,parent,false);
        }
        return new BaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            String dialog=infoList.get(position).getDialog();
//            Log.i("aaa", dialog);
            ((BaseViewHolder)holder).tv.setText(dialog);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
    public static class BaseViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public BaseViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.tv);
        }
    }
    public  boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
