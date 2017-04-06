package com.guoyi.cosutmui.demo5;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author:Created by Credit on 2017/4/5  16:55.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class MyCoordinatorLayout extends CoordinatorLayout {

    private static String TAG = "MyCoordinatorLayout";

    public MyCoordinatorLayout(Context context) {
        super(context);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onStopNestedScroll(View target) {
        /**
         *
         * 默认给屏蔽
         *  final LayoutParams lp = (LayoutParams) view.getLayoutParams();
         if (!lp.isNestedScrollAccepted()) {
         continue;
         }
         *
         *  使用 AppBarLayout.ScrollingViewBehavior的onStopNestedScroll可以回调。
         *
         */
        super.onStopNestedScroll(target);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            final LayoutParams lp = (LayoutParams) view.getLayoutParams();
            final Behavior viewBehavior = lp.getBehavior();
            if (viewBehavior != null) {
                viewBehavior.onStopNestedScroll(this, view, target);
            }
        }

    }

}
