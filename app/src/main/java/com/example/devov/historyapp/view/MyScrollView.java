package com.example.devov.historyapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by devov on 2017/3/22.
 */

public class MyScrollView extends LinearLayout {
    private float yM,yD,diff,lastDiff,diffOffset,dYM,dYD;
    private boolean isIntercept;
    private int i;
    private View collapseView,barView,childView;
    private float maxHeight,minHeight,currentHeight;
    private ValueAnimator fadeAnimator;
    private boolean changeState;
    public MyScrollView(Context context) {
        super(context);
        fadeAnimator=ValueAnimator.ofFloat(0,1);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fadeAnimator=ValueAnimator.ofFloat(0,1);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fadeAnimator=ValueAnimator.ofFloat(0,1);

    }
    public void setHeight(float max,float min){
        currentHeight=max;
        maxHeight=max;
        minHeight=min;
        fadeAnimator.setDuration((int)(maxHeight-minHeight));
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        childView=getChildAt(1);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(currentHeight>minHeight ||currentHeight==0)
//            return true;
//        else
//            return false;
        Log.i("aaa","onIntercept");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                dYD=ev.getY();
                Log.i("aaa","dispatch down");
                break;
            case MotionEvent.ACTION_MOVE:


                dYM=ev.getY();
//                if(dYM-dYD)
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        disposeTouchEvent(event);
        return true;
    }

    private void disposeTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.yD=event.getY();
                Log.i("aaa","ontouch down");

                break;
            case MotionEvent.ACTION_MOVE:

                this.yM=event.getY();
                diff=yM-yD;
                diffOffset=diff-lastDiff;
                display(diff,diffOffset);
                lastDiff=diff;
                break;
            case MotionEvent.ACTION_UP:
//                if(diff<0 && currentHeight==minHeight)
//                    isIntercept=false;
                lastDiff=0;
                break;
        }
    }
    private void display(float diff,float diffOffset){
        if(currentHeight==minHeight)
            collapseView.setVisibility(View.GONE);
        else
            collapseView.setVisibility(View.VISIBLE);
        if(diff>0) {
            if(childView.getScrollY()<=0) {
                childView.setScrollY(0);
                calculateBarHeight((int)diffOffset);
                if(currentHeight==minHeight)changeState=true;
            }
            else{
                childView.scrollBy(0,-(int)diffOffset);
            }
        } else{
            if(currentHeight==minHeight){
                childView.scrollBy(0,-(int)diffOffset);
            }else {
                calculateBarHeight((int)diffOffset);
                if(currentHeight==maxHeight)changeState=true;
            }
        }
        if(changeState){
            childView.scrollBy(0,-(int)diffOffset);
            changeState=false;
        }
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)currentHeight);
        collapseView.setLayoutParams(layoutParams);
        barView.setLayoutParams(layoutParams);
        fadeAnimator.setCurrentPlayTime((int)(currentHeight-minHeight));
        collapseView.setAlpha((float)fadeAnimator.getAnimatedValue());
    }
    private void calculateBarHeight(int diffOffset){
        currentHeight = currentHeight + diffOffset;
        currentHeight = currentHeight > maxHeight ? maxHeight : currentHeight;
        currentHeight = currentHeight < minHeight ? minHeight : currentHeight;
    }
    public void setTargetView(View collapse,View bar){
        barView=bar;
        collapseView=collapse;
    }
}
