package com.example.devov.historyapp.test.tPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.devov.historyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.devov.historyapp.test.tPackage.TestAdapter.ItemState.NOT_OVERFLOW;


/**
 * Created by devov on 2017/3/6.
 */

public class TestAdapter extends RecyclerView.Adapter {
    enum ItemState{
        UNKNOW,NOT_OVERFLOW,COLLAPSED,EXPAND
    }
    public static final int MAX_LINE=3;
    SparseArray<ItemState> list=new SparseArray<>();
    LayoutInflater layoutInflater;
    TestAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(layoutInflater.inflate(R.layout.test_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder=(ItemViewHolder)holder;
        ItemState state=list.get(position,ItemState.UNKNOW);
        if (state== ItemState.UNKNOW) {
            viewHolder.tv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    viewHolder.tv.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (viewHolder.tv.getLineCount()>MAX_LINE) {
                        viewHolder.tv.setMaxLines(MAX_LINE);
                        viewHolder.tag.setText("展开");
                        viewHolder.tag.setVisibility(View.VISIBLE);
                        list.put(position,ItemState.COLLAPSED);
                    }
                    else{
                        viewHolder.tag.setVisibility(View.GONE);
                        list.put(position, NOT_OVERFLOW);
                    }
                    return true;
                }
            });
        }else{
            switch (list.get(position)){
                case NOT_OVERFLOW:
                    viewHolder.tag.setVisibility(View.GONE);
                    break;
                case COLLAPSED:
                    viewHolder.tv.setMaxLines(MAX_LINE);
                    viewHolder.tag.setVisibility(View.VISIBLE);
                    viewHolder.tag.setText("展开");
                    break;
                case EXPAND:
                    viewHolder.tag.setText("收起");
                    viewHolder.tag.setVisibility(View.VISIBLE);
                    viewHolder.tv.setMaxLines(Integer.MAX_VALUE);
                    break;
            }
        }
        viewHolder.tag.setOnClickListener(v->{
            if (list.get(position).equals(ItemState.COLLAPSED)) {
                viewHolder.tv.setMaxLines(Integer.MAX_VALUE);
                viewHolder.tag.setVisibility(View.VISIBLE);
                viewHolder.tag.setText("收起");
                list.put(position,ItemState.EXPAND);
            }
            else {
                viewHolder.tag.setText("展开");
                viewHolder.tag.setVisibility(View.VISIBLE);
                viewHolder.tv.setMaxLines(MAX_LINE);
                list.put(position,ItemState.COLLAPSED);
            }
        });
       viewHolder.tv.setOnClickListener(v->{
           int count=viewHolder.tv.getLineCount();
           Log.i("aaa","count:"+count);
           int a= viewHolder.tv.getLayout().getLineVisibleEnd(count-1);
           Log.i("aaa","a="+a);
       });
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tag)
        TextView tag;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            String str="一二三四五一二三四五";
            tv.setText(str+str+str+str+str+str+str+str+str+str);
        }
    }
}
