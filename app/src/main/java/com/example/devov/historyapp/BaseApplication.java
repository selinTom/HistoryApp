package com.example.devov.historyapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.devov.historyapp.utils.common.LocalImageHelper;
import com.example.devov.historyapp.utils.dao.DaoRepo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import org.xutils.x;

/**
 * Created by devov on 2016/10/13.
 */

public class BaseApplication extends Application {
    public  static Application instance;
    public  static int width;
    public static int height;
    @Override
    public void onCreate() {
        super.onCreate();
        LocalImageHelper.getInstance().init(this);
        x.Ext.init(this);
        instance=this;
        Log.i("aaa","H1");
        width=getResources().getDisplayMetrics().widthPixels;
        height=getResources().getDisplayMetrics().heightPixels;
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig()
//                        new ProgressiveJpegConfig() {
//                    @Override
//                    public int getNextScanNumberToDecode(int scanNumber) {
//                        Log.i("aaa","ScanNumber"+scanNumber);
//                        return scanNumber + 2;
//                    }
//                    public QualityInfo getQualityInfo(int scanNumber) {
//                        Log.i("aaa","getQualityInfo:"+scanNumber);
//                        boolean isGoodEnough = (scanNumber >= 5);
//                        return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
//                    }}
                )
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(),imagePipelineConfig);
        DaoRepo.init(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized Application getInstance() {
        return instance;
    }
}
