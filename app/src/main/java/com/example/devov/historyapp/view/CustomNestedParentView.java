package com.example.devov.historyapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.devov.historyapp.R;

/**
 * Created by devov on 2017/7/26.
 */

public class CustomNestedParentView extends LinearLayout implements NestedScrollingParent {
    enum State{
        SPREAD,COLLAPSED,INCOMPLETED
    }
    private NestedScrollingParentHelper mParentHelper;
    private int collapsedHeight,originalHeight;
    private int spreadId,collapseId,contentId;
    private int consumedY;
    private View spreadView,collapseView,parentView,contentView;
    private DisposeTitleView disposeTitleView;
    private State state;
    private ScrollerCompat scroller;
    private boolean startAnimation,enabledFling;

    public CustomNestedParentView(Context context) {
        super(context);
    }

    public CustomNestedParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomNestedParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    public void setDisposeTitleViewAction(DisposeTitleView disposeTitleViewAction){
        this.disposeTitleView=disposeTitleViewAction;
    }
    private void init(Context ctx, AttributeSet attrs){
        mParentHelper=new NestedScrollingParentHelper(this);
        scroller= ScrollerCompat.create(getContext());
        new Thread(()->{
//            if(startAnimation)


        }).start();
        if(attrs!=null){
            TypedArray typedArray= ctx.obtainStyledAttributes(attrs, R.styleable.View);
            try {
                final int indexCount = typedArray.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    final int attr = typedArray.getIndex(i);
                    // most popular ones first
                    if (attr == R.styleable.View_TitleSpread) {
                        spreadId=typedArray.getResourceId(attr,0);
                    } else if (attr == R.styleable.View_TitleCollapse) {
                        collapseId = typedArray.getResourceId(attr, 0);
                    }else if (attr == R.styleable.View_Content) {
                        contentId = typedArray.getResourceId(attr, 0);
                    }
                }
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        spreadView = findViewById(spreadId);
        collapseView = findViewById(collapseId);
        contentView=findViewById(contentId);
        collapseView.post(()->collapsedHeight=collapseView.getHeight());
        spreadView.post(()->originalHeight=spreadView.getHeight());
        if(spreadView.getParent()==collapseView.getParent()){
            parentView=(View)spreadView.getParent();
            parentView.setBackgroundColor(((ColorDrawable)collapseView.getBackground()).getColor());
        }else{
            throw new RuntimeException("spreadView and collapsedView must have the same parentView!");
        }
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return target instanceof NestedScrollingChild && nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        mParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if(dy>0){//上滑
            if(parentView.getHeight()>=collapsedHeight){
//                disposeConsumeDistance(dy/3);
                consumedY=dy/3;
                consumed[1]=dy/3;
            }
        }else{
            if(parentView.getHeight()<=originalHeight ){
//                disposeConsumeDistance(dy/3);
                consumedY=dy/3;
                consumed[1]=dy/3;
            }
        }
    }
    private void disposeConsumeDistance(int dy){
        ViewGroup.LayoutParams titleViewParam=parentView.getLayoutParams();
        ViewGroup.LayoutParams contentViewParam=contentView.getLayoutParams();
        titleViewParam.height=parentView.getHeight()-dy;
        if(titleViewParam.height>=originalHeight) {
            titleViewParam.height = originalHeight;
            state=State.SPREAD;
            startAnimation=false;
        }
        if(titleViewParam.height<=collapsedHeight) {
            titleViewParam.height = collapsedHeight;
            state=State.COLLAPSED;
            startAnimation=false;
        }else{
            state=State.INCOMPLETED;
        }
        contentViewParam.height=getHeight()-titleViewParam.height;
        float rate=((float)(originalHeight-titleViewParam.height)/(float)(originalHeight-collapsedHeight));
        requestLayout();
        if(disposeTitleView==null)
            throw new RuntimeException("DisposeTitleView Action doesn't exist !");
        else
            disposeTitleView.action(spreadView,collapseView,rate);
    }
    private int lastDy;
    private void disposeFlingAction(){
        if(startAnimation && scroller.computeScrollOffset()) {
            int dy = scroller.getCurrY();
            int diff = dy - lastDy;
            disposeConsumeDistance(diff);
            lastDy = dy;
            invalidate();
        }else{
            lastDy=0;
        }
    }
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if(dyConsumed>0) {
            disposeConsumeDistance(consumedY);
            enabledFling=true;
        }else
            enabledFling=false;
        if(dyConsumed==0 && dyUnconsumed<0) {
            disposeConsumeDistance(consumedY);
            enabledFling=true;
        }else
            enabledFling=false;
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {

        return false;
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if(velocityY>1000 ){//下滑
            if(state!=State.COLLAPSED) {
                scroller.fling(0, 0, 0, (int) velocityY, 0, 0, -10000, 10000);
                startAnimation=true;
            }
        }else if(velocityY<-1000){
            if(state!=State.SPREAD && enabledFling){
                scroller.fling(0,0,0,(int)velocityY,0,0,-10000,10000);
                startAnimation=true;
            }
        }
        invalidate();
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        disposeFlingAction();
    }

    public interface DisposeTitleView{
        void action(View spreadView,View collapseView,float rate);
    }
}
