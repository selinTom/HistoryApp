package com.example.devov.historyapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by devov on 2017/4/12.
 */

public class MyNestedParentView extends LinearLayout implements NestedScrollingParent {
    private ImageView img;
    private TextView tv;
    private MyNestedChildView myNestedScrollChild;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int maxHeight,minHeight,currentHeight;
    private ValueAnimator fadeAnimator;
    public MyNestedParentView(Context context) {
        super(context);
    }

    public MyNestedParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fadeAnimator=ValueAnimator.ofFloat(0,1);
        mNestedScrollingParentHelper=new NestedScrollingParentHelper(this);
    }
    public void setHeight(int max,int min){
        currentHeight=max;
        maxHeight=max;
        minHeight=min;
        fadeAnimator.setDuration((maxHeight-minHeight));
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if(target instanceof MyNestedChildView) {
            Log.i("aaa","Parent======>onStartNestedScroll  true");
            return true;
        }
        Log.i("aaa","Parent======>onStartNestedScroll  false");
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.i("aaa","Parent======>onNestedScrollAccepted");
        mNestedScrollingParentHelper.onNestedScrollAccepted(child,target,axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.i("aaa","Parent======>onStopNestedScroll");
        mNestedScrollingParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i("aaa","Parent======>onNestedPreScroll");
        if (showImg(dy) || hideImg(dy)) {//如果需要显示或隐藏图片，即需要自己(parent)滚动
            scrollBy(0, -dy);//滚动
            consumed[1] = dy;//告诉child我消费了多少
        }

    }
    //后于child滚动
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i("aaa","Parent======>onNestedScroll");
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i("aaa","Parent======>onNestedPreFling");
        return false;
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("aaa","Parent======>onNestedFling");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.i("aaa","Parent======>getNestedScrollAxes");
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public boolean showImg(int dy) {
        if (dy > 0) {
            if (getScrollY() > 0 && myNestedScrollChild.getScrollY() == 0) {
                return true;
            }
        }
        return false;
    }

    //上拉的时候，是否要向上滚动，隐藏图片
    public boolean hideImg(int dy) {
        if (dy < 0) {
            if (getScrollY() > minHeight) {
                return true;
            }
        }
        return false;
    }
    //scrollBy内部会调用scrollTo
    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        if (y < minHeight) {
            y = minHeight;
        }
        if (y > maxHeight) {
            y = maxHeight;
        }
        super.scrollTo(x, y);
    }
}
