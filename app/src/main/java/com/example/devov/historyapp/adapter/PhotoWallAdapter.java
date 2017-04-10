package com.example.devov.historyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.activity.PhotoDetailsActivity;
import com.example.devov.historyapp.imageload.ImageLoaderUtil;
import com.example.devov.historyapp.utils.common.LocalImageHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/2/17.
 */

public class PhotoWallAdapter extends RecyclerView.Adapter {
    private Map<String, ArrayList<LocalImageHelper.LocalFile>> folders;
    private List<String>folderNames;
    private LayoutInflater layoutInflater;
    private Context cxt;
    public PhotoWallAdapter(Context cxt,Map<String, ArrayList<LocalImageHelper.LocalFile>> folders){
        this.cxt=cxt;
        layoutInflater=LayoutInflater.from(cxt);
        updateData(folders);
    }
    public void updateData(Map<String, ArrayList<LocalImageHelper.LocalFile>> folders){
        this.folders=folders;
        folderNames=new ArrayList<>();
        Iterator iterator=folders.entrySet().iterator();
        while(iterator.hasNext()){
            folderNames.add((String)(((Map.Entry)iterator.next()).getKey()));
        }
        Collections.sort(folderNames, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                Integer num1 = folders.get(arg0).size();
                Integer num2 = folders.get(arg1).size();
                return num2.compareTo(num1);
            }
        });
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==0)
           return new HeadViewHolder(layoutInflater.inflate(R.layout.photo_wall_head,parent,false));
        else
//           return new ItemViewHolder(layoutInflater.inflate(R.layout.photo_wall_item,parent,false));
           return new HeadViewHolder(layoutInflater.inflate(R.layout.photo_wall_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeadViewHolder hvd;
//        ItemViewHolder ivh;
//        if(position==0){
            hvd=(HeadViewHolder)holder;
            hvd.tv.setText(folderNames.get(position));
            ImageLoaderUtil.setImageView(hvd.iv,folders.get(folderNames.get(position)).get(0).getThumbnailUri());
            hvd.relativeLayout.setOnClickListener(v->{
                Intent i=new Intent(cxt, PhotoDetailsActivity.class);
                i.putExtra("PHOTO_LIST_NAME",folderNames.get(position));
                i.putExtra("PHOTO_LIST_ITEM",folders.get(folderNames.get(position)));
                cxt.startActivity(i);
            });
        Log.i("aaa",folders.get(folderNames.get(position)).get(0).getOriginalUri());
//        }
//        else {
//            ivh=(ItemViewHolder) holder;
//            ivh.tv.setText(folderNames.get(position));
//            ImageLoaderUtil.setImageView(ivh.iv,folders.get(folderNames.get(position)).get(0).getThumbnailUri());
//        }

    }
    @Override
    public int getItemCount() {
        return folderNames.size();
    }
    public class HeadViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rl)
        RelativeLayout relativeLayout;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv)
        TextView tv;
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
//    public class ItemViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.rl)
//        RelativeLayout relativeLayout;
//        @BindView(R.id.iv)
//        ImageView iv;
//        @BindView(R.id.tv)
//        TextView tv;
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
