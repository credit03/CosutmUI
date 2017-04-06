package com.guoyi.cosutmui.demo4.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.R;

import java.util.List;

/**
 * Author:Created by Credit on 2017/3/31  10:25.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class UcToolbarBehavior extends UcBaseBehavior {

    private static String TAG = "UcToolbarBehavior";
    private int mToolbarOffset;
    private int mToolbarHeight;
    private View mToolbar;

    private int mOffsetDelta;
    private View mContent;

    private UcBehaviorHelper mHelper;

    public UcToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mToolbarOffset = context.getResources().getDimensionPixelOffset(R.dimen.toobar_offset_height);
        mToolbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.toobar_height);
        mHelper = UcBehaviorHelper.getInstance();
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.uc_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mToolbar = child;
        mContent = parent.findViewById(R.id.uc_content_pager);
        mOffsetDelta = mHelper.getOffsetDelta();
        if (mOffsetDelta > 0) {
            sureInit = true;
        }
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mHelper.isCanAsNeeded()) {
            offsetChildAsNeeded(parent, child, mContent);
        }
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {

        float translationY = dependency.getTranslationY();
        if (translationY >= 0.0f) {
            child.setTranslationY(mToolbarOffset);
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            child.setTranslationY(mToolbarHeight);
        } else {
            float v2 = translationY / mOffsetDelta;
            child.setTranslationY(Math.abs(mToolbarHeight * v2));

        }

    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = mToolbarOffset;
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }


    @Override
    public void closeHeadPager(int Duration) {
        mToolbar.animate().translationY(mToolbarHeight).setDuration(Duration);

    }

    @Override
    public void openHeadPager(int Duration) {
        mToolbar.animate().translationY(mToolbarOffset).setDuration(Duration);

    }

    @Override
    public View findFirstDependency(List<View> views) {
        if (views != null) {
            for (View v : views) {
                if (isOnDepend(v)) {
                    return v;
                }
            }
        }
        return null;
    }
}
