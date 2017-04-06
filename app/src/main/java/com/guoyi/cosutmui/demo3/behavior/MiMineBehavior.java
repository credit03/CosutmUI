package com.guoyi.cosutmui.demo3.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.R;


/**
 * Author:Created by Credit on 2017/3/29  10:36.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class MiMineBehavior extends AppBarLayout.ScrollingViewBehavior {
    private static final String TAG = "MiMineBehavior";

    public MiMineBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int MaxHeight;

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        MaxHeight = dependency.getHeight();
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final View child, final View dependency) {

        View img = parent.findViewById(R.id.iv);
        final View tv1 = parent.findViewById(R.id.tv1);
        final View ns = parent.findViewById(R.id.ll);

        float i = dependency.getBottom() * 1f / MaxHeight;
        img.setAlpha(i);
        if (child.getTop() == dependency.getBottom() && dependency.getBottom() <= 44) {
            tv1.setScaleX(1f);
            tv1.setScaleY(1f);
            tv1.setVisibility(View.GONE);
        } else {
            if (tv1.getVisibility() == View.GONE) {
                tv1.setVisibility(View.VISIBLE);
            }
            tv1.setScaleX(1f + 1f - i);
            tv1.setScaleY(1f + 1f - i);

        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

}
