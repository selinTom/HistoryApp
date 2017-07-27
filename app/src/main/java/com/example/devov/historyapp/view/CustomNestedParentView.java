package com.example.devov.historyapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.devov.historyapp.R;

/**
 * Created by devov on 2017/7/26.
 */

public class CustomNestedParentView extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper mParentHelper;
    private int collapsedHeight,originalHeight;
    private int spreadId,collapseId,contentId;
    private int consumedY;
    private View spreadView,collapseView,parentView,contentView;
    private DisposeTitleView disposeTitleView;

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

    private void init(Context ctx, AttributeSet attrs){
        mParentHelper=new NestedScrollingParentHelper(this);
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
        if(titleViewParam.height>originalHeight)
            titleViewParam.height=originalHeight;
        if(titleViewParam.height<collapsedHeight)
            titleViewParam.height=collapsedHeight;
        contentViewParam.height=getHeight()-titleViewParam.height;
        float rate=((float)(originalHeight-titleViewParam.height)/(float)(originalHeight-collapsedHeight));
        requestLayout();
        if(disposeTitleView==null)
            throw new RuntimeException("DisposeTitleView Action doesn't exist !");
        else
            disposeTitleView.action(spreadView,collapseView,rate);
    }
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if(dyConsumed>0)
            disposeConsumeDistance(consumedY);
        if(dyConsumed==0 && dyUnconsumed<0)
            disposeConsumeDistance(consumedY);
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        findTargetView(target);
        Log.i("aaa","VelocityY is:"+velocityY);

        return false;
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        if (!consumed) {
//            return true;
//        }
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }

//    @Override
//    public void scrollTo(int x, int y) {
//        if (y < 0) {
//            y = 0;
//        }
//        if (y > originalHeight) {
//            y = originalHeight;
//        }
//
//        super.scrollTo(x, y);
//    }
    private void findTargetView(View targetView){
        if(targetView==spreadView){
            Log.i("aaa","target view is spreadView");
        }else if(targetView==collapseView){
            Log.i("aaa","target view is collapseView");
        }else if(targetView==parentView){
            Log.i("aaa","target view is parentView");
        }else if(targetView==contentView){
            Log.i("aaa","target view is contentView");
        }

    }
    public interface DisposeTitleView{
        void action(View spreadView,View collapseView,float rate);
    }
}
