package com.example.devov.historyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by devov on 2017/3/22.
 */

public class MyScrollView extends LinearLayout {
    private float yM,yD,diff,lastDiff,diffOffset;
    private TouchEventCallback touchEventCallback;
    private boolean isIntercept;
    private int i;
    public MyScrollView(Context context) {
        super(context);
        setCanInterceptTouchEvent(true);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCanInterceptTouchEvent(true);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCanInterceptTouchEvent(true);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isIntercept)
            return true;
        else
            return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.yD=event.getY();
//                Log.i("aaa","ScrollView ACTION DOWN  yD:"+yD+"  yM:"+yM);
                break;
            case MotionEvent.ACTION_MOVE:
                this.yM=event.getY();
                diff=yM-yD;
                diffOffset=diff-lastDiff;
                if(touchEventCallback!=null)
                    touchEventCallback.doing(diff,diffOffset);
                lastDiff=diff;
//                Log.i("aaa","ScrollView ACTION MOVE  yD:"+yD+"  yM:"+yM);
                break;
            case MotionEvent.ACTION_UP:
                lastDiff=0;
//                setCanInterceptTouchEvent(false);
//                Log.i("aaa","ScrollView ACTION UPyD "+yD+"  yM:"+yM);
                break;
        }
        return isIntercept;
    }
    public interface TouchEventCallback{
        void doing(float diff,float diffOffset);
    }
    public void setTouchEventCallback(TouchEventCallback touchEventCallback){
        this.touchEventCallback=touchEventCallback;
    }
    public void setCanInterceptTouchEvent(boolean isIntercept){
        this.isIntercept=isIntercept;
    }
}
