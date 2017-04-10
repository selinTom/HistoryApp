package com.example.devov.historyapp.imageload;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devov.historyapp.BaseApplication;

/**
 * Created by devov on 2016/11/16.
 */

public class ImageLoaderUtil {
    private static boolean isCircle=false;
    public static final int LOAD_FROM_DISK=0;
    public static final int LOAD_FROM_NET=1;
    public static void setImageView(ImageView imageView, Uri uri){
        setImageView(imageView,uri,LOAD_FROM_NET);
    }
    public static void setImageView(ImageView imageView, String uri){
        setImageView(imageView, Uri.parse(uri),LOAD_FROM_NET);
    }
    public static void setImageView(ImageView imageView, String uri, int loadLocation){
        setImageView(imageView, Uri.parse(uri),loadLocation);
    }
    public static void setImageView(ImageView imageView, Uri uri, int loadLocation){
        DrawableTypeRequest drawableTypeRequest= Glide.with(imageView.getContext())
                .load(uri);
        if(loadLocation==LOAD_FROM_DISK)
            drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        else
            drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
        drawableTypeRequest.fitCenter().into(imageView);
        if(isCircle)
            drawableTypeRequest.transform(new GlideCircleTransform(imageView.getContext())).into(imageView);
        else
            drawableTypeRequest.fitCenter().into(imageView);
        isCircle=false;
    }
    public static void clearCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(BaseApplication.getInstance()).clearDiskCache();
            }
        }).start();
    }
    public static void setIsCircle(boolean mCircle){
        isCircle=mCircle;
    }
}
