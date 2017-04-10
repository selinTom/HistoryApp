package com.example.devov.historyapp.test;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by devov on 2017/3/17.
 */

public class  MyPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        Log.i("aaa","Position:"+position+"  Tag:"+page.getTag());
        if(position<0){
//            page.setRotation(-1*360*position);
//            page.setTranslationX(page.getWidth());
            page.setScaleX(-1);
        }
        else if(position>0){
//            page.setRotation(360*position);
            page.setScaleX(-1);
        }
    }
}
