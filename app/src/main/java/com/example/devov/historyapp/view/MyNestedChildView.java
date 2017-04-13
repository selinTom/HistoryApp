package com.example.devov.historyapp.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by devov on 2017/4/12.
 */

public class MyNestedChildView extends LinearLayout implements NestedScrollingChild {
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private final int[] offset = new int[2]; //偏移量
    private final int[] consumed = new int[2]; //消费
    private int lastY;
    private int showHeight;
    public MyNestedChildView(Context context) {
        super(context);
    }

    public MyNestedChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();


        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private NestedScrollingChildHelper getNestedScrollingChildHelper(){
        if(nestedScrollingChildHelper==null){
            nestedScrollingChildHelper=new NestedScrollingChildHelper(this);
            nestedScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return nestedScrollingChildHelper;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY=(int)event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y=(int)(event.getRawY());
                int dy=y-lastY;
                lastY=y;
                if(startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL) && dispatchNestedPreScroll(0,dy,consumed,offset)){
                    int remain=dy-consumed[1];
                    if(remain!=0){
                        scrollBy(0,-remain);
                    }
                }
                else{
                    scrollBy(0,-dy);
                }
                break;
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        int maxY=getMeasuredHeight()-showHeight;
        if(y>maxY)
            y=maxY;
        if(y<0)
            y=0;
        super.scrollTo(x, y);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.i("aaa","Child===>setNestedScrollingEnabled");
        getNestedScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.i("aaa","Child===>isNestedScrollingEnabled");

        return getNestedScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.i("aaa","Child===>startNestedScroll");
        return getNestedScrollingChildHelper().startNestedScroll(axes);
    }
    @Override
    public void stopNestedScroll() {
        Log.i("aaa","Child===>stopNestedScroll");
        getNestedScrollingChildHelper().stopNestedScroll();
    }
    @Override
    public boolean hasNestedScrollingParent() {
        Log.i("aaa","Child===>hasNestedScrollingParent");
        return getNestedScrollingChildHelper().hasNestedScrollingParent();
    }
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.i("aaa","Child===>dispatchNestedScroll");
        return getNestedScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.i("aaa","Child===>dispatchNestedPreScroll");
        return getNestedScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.i("aaa","Child===>dispatchNestedFling");
        return getNestedScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.i("aaa","Child===>dispatchNestedPreFling");
        return getNestedScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

}
