package com.example.devov.historyapp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.adapter.PhotoWallAdapter;
import com.example.devov.historyapp.utils.common.LocalImageHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/2/17.
 */

public class PhotoWallActivity extends Activity {
    @BindView(R.id.back)
    ImageView backView;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        ButterKnife.bind(this);
        backView.setOnClickListener(v->finish());
        gridLayoutManager=new GridLayoutManager(this,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position==0) return gridLayoutManager.getSpanCount();
                else return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        new Thread(()->{
            LocalImageHelper.getInstance().initImage();
            runOnUiThread(() -> {
                recyclerView.setAdapter(new PhotoWallAdapter(this,LocalImageHelper.getInstance().getFolderMap()));
            });
        }).start();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("UPDATE_DATA_ACTION");
        registerReceiver(myBroadCastReceiver,intentFilter);
    }
     BroadcastReceiver myBroadCastReceiver =new  BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            ((PhotoWallAdapter)recyclerView.getAdapter()).updateData(LocalImageHelper.getInstance().getFolderMap());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCastReceiver);
    }
}
