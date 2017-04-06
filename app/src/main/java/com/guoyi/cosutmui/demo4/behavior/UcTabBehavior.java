package com.guoyi.cosutmui.demo4.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.guoyi.cosutmui.R;

import java.util.List;

/**
 * Author:Created by Credit on 2017/3/31  10:21.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class UcTabBehavior extends UcBaseBehavior {

    private static String TAG = "UcTabBehavior";

    private int mTabHeight;
    private View mTab;
    private int mTabTop;
    private View mContent;

    private int mOffsetDelta;

    private UcBehaviorHelper mHelper;

    public UcTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = UcBehaviorHelper.getInstance();
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.uc_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mTab = child;
        mTabHeight = child.getMeasuredHeight();
        mTabTop = child.getTop();
        mContent = parent.findViewById(R.id.uc_content_pager);
        /**
         * 计算可以滑动高度
         */
        mOffsetDelta = mHelper.getOffsetDelta();
        if (mOffsetDelta > 0) {
            sureInit = true;
        }
        //  mOffsetDelta = dependency.getMeasuredHeight() - mToolbarHeight - mTabHeight;

    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mHelper.isCanAsNeeded()) {
            offsetChildAsNeeded(parent, child, mContent);
        }
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {

        Log.d(TAG, "offsetChildAsNeeded: ");
        float translationY = dependency.getTranslationY();
        if (translationY >= 0.0f) {
            child.setTranslationY(mTabTop);
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            child.setTranslationY(translationY - mTabHeight);
        } else {
            float v1 = translationY / mOffsetDelta;
            float v = translationY + (mTabHeight * v1);
            child.setTranslationY(v);


        }

    }


    @Override
    public void openHeadPager(int Duration) {

        Log.d(TAG, "openHeadPager: ");
        mTab.animate().translationY(mTabTop).setDuration(Duration);
    }

    @Override
    public void closeHeadPager(int Duration) {
        mTab.animate().translationY(-(mOffsetDelta + mTabHeight)).setDuration(Duration);


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
