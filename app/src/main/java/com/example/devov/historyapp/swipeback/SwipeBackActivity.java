
package com.example.devov.historyapp.swipeback;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SwipeBackActivity extends AppCompatActivity {

    private SwipeBackLayout mSwipeBackLayout;

    private boolean mOverrideExitAniamtion = true;

    private boolean mIsFinishing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mSwipeBackLayout.findViewById(id);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     * Override Exit Animation
     * 
     * @param override
     */
    public void setOverrideExitAniamtion(boolean override) {
        mOverrideExitAniamtion = override;
    }

    /**
     * Scroll out contentView and finish the activity
     */
    public void scrollToFinishActivity() {
        mSwipeBackLayout.scrollToFinishActivity();
    }

    @Override
    public void finish() {
        if (mOverrideExitAniamtion && !mIsFinishing) {
            scrollToFinishActivity();
            mIsFinishing = true;
            return;
        }
        mIsFinishing = false;
        super.finish();
    }
}
