package com.example.devov.historyapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.adapter.PhotoDetailAdapter;
import com.example.devov.historyapp.imageload.ImageLoaderUtil;
import com.example.devov.historyapp.utils.common.LocalImageHelper;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.example.devov.historyapp.view.MyFrameLayout;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.devov.historyapp.R.id.iv;

/**
 * Created by devov on 2017/2/17.
 */

public class PhotoDetailsActivity extends Activity {
    @BindView(R.id.back)
    ImageView backView;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.fl)
    MyFrameLayout frameLayout;
    @BindView(iv)
    ImageView imageView;
    ArrayList<LocalImageHelper.LocalFile> localFiles=new ArrayList<>();
    String folderName;
    PhotoDetailAdapter photoDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        backView.setOnClickListener(v->finish());
        Intent i=getIntent();
        folderName=i.getStringExtra("PHOTO_LIST_NAME");
        localFiles.addAll((ArrayList<LocalImageHelper.LocalFile>) i.getSerializableExtra("PHOTO_LIST_ITEM"));
        printLog();
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        photoDetailAdapter=new PhotoDetailAdapter(this,localFiles,folderName);
        photoDetailAdapter.setOnItemClickListener(new PhotoDetailAdapter.OnClickListener() {
            @Override
            public void onClick(String imageName) {
                frameLayout.setVisibility(View.VISIBLE);
                ImageLoaderUtil.setImageView(imageView,imageName);
            }

            @Override
            public void onLongClick(String imageName) {
                xUtilsHelper.startUCrop(PhotoDetailsActivity.this,imageName);
            }
        });

        recyclerView.setAdapter(photoDetailAdapter);
        textView.setText(folderName);
    }

    @Override
    public void onBackPressed() {
        if(frameLayout.getVisibility()==View.GONE)
            finish();
        else
            frameLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            LocalImageHelper.getInstance().restart();
            while(!LocalImageHelper.getInstance().isInited());
            sendBroadcast(new Intent("UPDATE_DATA_ACTION"));
        }
        else if(resultCode==UCrop.RESULT_ERROR){
            Log.i("aaa", UCrop.getError(data).toString());
        }
    }
    private void printLog(){
        for(LocalImageHelper.LocalFile localFile:localFiles){
            Log.i("aaa","Original:"+localFile.getOriginalUri()+"  Thumbnail"+localFile.getThumbnailUri());
        }
    }
}
