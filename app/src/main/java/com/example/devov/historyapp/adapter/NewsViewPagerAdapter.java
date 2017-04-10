package com.example.devov.historyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.devov.historyapp.fragment.NewsFragment;
import com.example.devov.historyapp.interfaces.Constant;

/**
 * Created by devov on 2017/1/12.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter implements Constant{
    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.getInstance(TAG[position],position);
    }

    @Override
    public int getCount() {
        return items.length;
    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return items[position];
//    }
}
