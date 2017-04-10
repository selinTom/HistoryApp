package com.example.devov.historyapp.imageload;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by devov on 2016/11/16.
 */

public class SimpleGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSize100MegaBytes = 104857600;
        String filePath="/sdcard/glideCache";
        builder.setDiskCache(new DiskLruCacheFactory(filePath,cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
