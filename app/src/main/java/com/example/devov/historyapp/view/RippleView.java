package com.example.devov.historyapp.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by devov on 2016/12/16.
 */

public class RippleView extends View {
    private Paint fillPaint,paint;
    private ObjectAnimator oa;
    private static final Interpolator INTERPOLATOR = new AccelerateInterpolator();
    private int currentRadius;
    private AnimatorSet animatorSet;
    private boolean isAnimationStart=false;
    private int drawWidth;
    public RippleView(Context context) {
        super(context);
        init();

    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }
    public RippleView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }
    private void init(){
        fillPaint=new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(0xFFEBEBEB);
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isAnimationStart)
            currentRadius=getWidth();
        canvas.drawCircle(getWidth()/2,getHeight()/2,currentRadius,fillPaint);
        if(isAnimationStart)
            canvas.drawCircle(getWidth()/2,getHeight()/2,drawWidth*1.2f,paint);
    }

    public void startAnimation(int height,int width){
        drawWidth=width;
        oa=ObjectAnimator.ofInt(this,"currentRadius",height,getWidth());
        fillPaint.setColor(0xFF00EEEE);
        animatorSet=new AnimatorSet();
        animatorSet.play(oa);
        animatorSet.setDuration(3000);
        animatorSet.setInterpolator(INTERPOLATOR);
        animatorSet.start();
        isAnimationStart=true;
    }
    public void stop(){
        animatorSet.end();
        fillPaint.setColor(0xFFEBEBEB);
        isAnimationStart=false;
        invalidate();

    }

    public void setCurrentRadius(int currentRadius) {
        this.currentRadius = currentRadius;
        invalidate();
    }
}
