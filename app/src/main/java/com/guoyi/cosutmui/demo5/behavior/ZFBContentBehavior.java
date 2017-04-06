package com.guoyi.cosutmui.demo5.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.guoyi.cosutmui.R;

import java.util.List;

/**
 * Author:Created by Credit on 2017/4/5  11:50.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class ZFBContentBehavior extends AppBarLayout.ScrollingViewBehavior {

    private static final String TAG = "ZFBContentBehavior";

    private int OffsetData = 0;
    private View mToolbar1;
    private View mToolbar2;
    private AppBarLayout mBarLayout;
    private View mDependency;

    public ZFBContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    private float lastV = 0;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (mDependency == null) {
            mDependency = dependency;
        }
        float v = child.getTop() * 1f / dependency.getMeasuredHeight();
        dependency.setAlpha(v);
        Log.d(TAG, "onDependentViewChanged: 透明值 ：" + v + " setAlpha:" + v / 3 + "   child.getTop():" + child.getTop() + "  dependency top:" + dependency.getTop());
        if (mToolbar1 != null && mToolbar2 != null) {

            if (v <= 0) {
                mToolbar1.setVisibility(View.VISIBLE);
                mToolbar2.setVisibility(View.INVISIBLE);
                mToolbar1.setAlpha(1);
                mToolbar2.setAlpha(0);
            } else if (v < 0.98f) {

                if (v <= 0.55f) {
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar1.setAlpha(1f - (v * 1.5f));
                } else if (v > 0.55f && v < 0.60f) {
                    mToolbar2.setAlpha(0);
                    mToolbar2.setVisibility(View.INVISIBLE);
                } else {
                    mToolbar1.setVisibility(View.INVISIBLE);
                    mToolbar2.setVisibility(View.VISIBLE);

                    mToolbar1.setAlpha(0);
                    if (v > lastV) {
                        mToolbar2.setAlpha(v / 2);
                    } else if (v != lastV) {
                        mToolbar2.setAlpha(v / 6);
                    }
                }
            } else {
                mToolbar1.setVisibility(View.INVISIBLE);
                mToolbar2.setVisibility(View.VISIBLE);
                mToolbar1.setAlpha(0);
                mToolbar2.setAlpha(1);
            }

            lastV = v;

        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        if (mBarLayout == null) {
            View rootView = parent.getRootView();
            mToolbar1 = rootView.findViewById(R.id.zfb_toolbar_1);
            mToolbar2 = rootView.findViewById(R.id.zfb_toolbar_2);
            mBarLayout = findFirstDependency(parent.getDependencies(child));
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        /**
         * 要重写CoordinatorLayout 的onStopNestedScroll，可以监听当前事件
         */
        super.onStopNestedScroll(coordinatorLayout, child, target);
        if (mBarLayout != null) {

            float v = child.getTop() * 1f / mDependency.getMeasuredHeight();
            if (v < 1f && v > 0.55f) {
                //打开
                mBarLayout.setExpanded(true, true);
            } else if (v > 0f && v <= 0.55f) {
                //关闭
                mBarLayout.setExpanded(false, true);

            }
        }

    }

    /**
     * 查找AppBarLayout
     *
     * @param views
     * @return
     */
    AppBarLayout findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof AppBarLayout) {
                return (AppBarLayout) view;
            }
        }
        return null;
    }
}
