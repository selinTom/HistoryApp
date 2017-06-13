package com.example.devov.historyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by devov on 2017/4/26.
 */

public class MyScrollViewGroup extends ViewGroup {
    private Scroller mScroller;
    private int lastDx,leftBorder,rightBorder;
    public MyScrollViewGroup(Context context) {
        super(context);
        init(context);
    }

    public MyScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public MyScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context ctx){
        mScroller=new Scroller(ctx);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count=getChildCount();
        int min=b/count;
        int maxWidth=0;
        for(int i=0;i<count;i++){
            View v=getChildAt(i);
            v.layout(0,min*(i+1)/2,v.getMeasuredWidth(),min*(i+1)/2+v.getMeasuredHeight());
            maxWidth=Math.max(maxWidth,v.getMeasuredWidth());
        }
        leftBorder=0;
        rightBorder=getMeasuredWidth()-maxWidth;
    }
    public void startMove(int dx){
        int target=lastDx+dx;
        if(target>rightBorder) {
            target = rightBorder;
            dx=rightBorder-lastDx;
        }
        if(target <leftBorder) {
            target = leftBorder;
            dx=leftBorder-lastDx;
        }
        mScroller.startScroll(lastDx,0,dx,0);
        invalidate();
        lastDx=target;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(-1*(mScroller.getCurrX()),mScroller.getCurrY());

            invalidate();
        }
    }
}
