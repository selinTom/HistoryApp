package com.example.devov.historyapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.imageload.ImageLoaderUtil;
import com.example.devov.historyapp.utils.common.LocalImageHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/2/17.
 */

public class PhotoDetailAdapter extends RecyclerView.Adapter {
    public interface OnClickListener{
        void onClick(String imageName);
        void onLongClick(String imageName);
    }
    public void setOnItemClickListener(OnClickListener onClickListener){this.onClickListener=onClickListener;}
    private OnClickListener onClickListener;
    private List<LocalImageHelper.LocalFile> localFiles;
    private LayoutInflater layoutInflater;
    public PhotoDetailAdapter(Context cxt, ArrayList localFiles,String folderName){
        layoutInflater=LayoutInflater.from(cxt);
        this.localFiles=localFiles;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(layoutInflater.inflate(R.layout.activity_photo_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView i=((ItemViewHolder)holder).imageView;
        ImageLoaderUtil.setImageView(i,localFiles.get(position).getThumbnailUri());
        i.setOnClickListener(v->onClickListener.onClick(localFiles.get(position).getOriginalUri()));
        i.setOnLongClickListener(v->{
            onClickListener.onLongClick(localFiles.get(position).getOriginalUri());
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return localFiles.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
