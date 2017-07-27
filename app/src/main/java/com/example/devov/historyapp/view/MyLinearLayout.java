package com.example.devov.historyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by devov on 2017/6/29.
 */

public class MyLinearLayout extends LinearLayout {
    private ImageView iv;
    public MyLinearLayout(Context context) {
        super(context);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        iv=new ImageView(getContext());
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        addView(iv);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i("aaa","onMeasure->width:"+getWidth()+"  height:"+getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("aaa","onLayout");
    }
    public void setImage(int id){
        iv.setImageResource(id);
    }
}
