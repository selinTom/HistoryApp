package com.example.devov.historyapp.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.devov.historyapp.R;

/**
 * Created by devov on 2016/11/18.
 */

public class DragLayout extends LinearLayout {
    private ViewDragHelper viewDragHelper;
    private View v;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        v=findViewById(R.id.btn);
    }
    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper= ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.i("aaa","trybababa");
                return child==v;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d("aaa", "clampViewPositionHorizontal " + left + "," + dx);
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
