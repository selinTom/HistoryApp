package com.example.devov.historyapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.adapter.NewsViewPagerAdapter;
import com.example.devov.historyapp.interfaces.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/1/12.
 */

public class NewsActivity extends AppCompatActivity implements Constant {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        viewPager.setAdapter(new NewsViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<viewPager.getAdapter().getCount();i++){
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.tab_item);
            ((TextView)tab.getCustomView().findViewById(R.id.tv)).setText(items[i]);
            if(i%2==0){
                tab.getCustomView().findViewById(R.id.view).setVisibility(View.GONE);
            }
        }
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
