package com.example.devov.historyapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by devov on 2016/10/14.
 */

public class LineView extends View {
    private Paint paint;
    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,paint);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}
