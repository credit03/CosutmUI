package com.guoyi.cosutmui.demo3.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.R;
import com.guoyi.cosutmui.helper.HeaderScrollingViewBehavior;

import java.util.List;


/**
 * Author:Created by Credit on 2017/3/29  14:05.
 * Email:credit_yi@163.com
 * Dscription:{一句话描述该类的作用}
 */

public class MiMineContentBehavior extends HeaderScrollingViewBehavior {

    private static final String TAG = "MiMineBehavior2";
    /**
     * 可移动总高度
     */
    private int mOffsetDelta;
    /**
     * 依赖view高度
     */
    private int DependencyHeight;

    private Context context;

    private View img;
    private View title;

    public MiMineContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    public View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view))
                return view;
        }
        return null;
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        boolean dependOn = isDependOn(dependency);
        if (dependOn) {
            img = parent.findViewById(R.id.iv); //获取img
            title = parent.findViewById(R.id.tv1); //获取title
            /**
             * 获取依赖view高度，即 R.id.head_pager的View高度，表明这个高度就是可以滑动高度
             */
            DependencyHeight = dependency.getMeasuredHeight();

            /**
             * 计算可移动总高度
             * + context.getResources().getDimensionPixelSize(R.dimen.tv_height)，这是要把Title滑动出屏幕外面；
             * 但我的需求是要把Title滑动到Toobar下面，-context.getResources().getDimensionPixelSize(R.dimen.toobar_height)
             *
             */
            // FIXME: 2017/3/30 要使用 getDimensionPixelOffset，不然少一个1px
            mOffsetDelta = DependencyHeight + context.getResources().getDimensionPixelOffset(R.dimen.tv_height)
                    - context.getResources().getDimensionPixelOffset(R.dimen.toobar_height);

        }
        return dependOn;
    }


    /**
     * 获取可移动到最小的高度参数
     *
     * @return
     */
    private int getOffsetRange() {
        return -mOffsetDelta;
    }

    /**
     * 判断依赖view
     *
     * @param view
     * @return
     */
    private boolean isDependOn(View view) {
        return view != null && view.getId() == R.id.head_pager;
    }


    /**
     * 在滑动开始时，判断是否可滑动事件，若不可以，则不执行滑动相关动作，例如不执行onNestedPreScroll等
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //SCROLL_AXIS_VERTICAL----VERTICAL（横）滑动动作;判断不是横滑动动作
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll(child, 0);
    }

    /**
     * 计算是否可以滑动
     *
     * @param child
     * @param pendingDy
     * @return
     */
    private boolean canScroll(View child, float pendingDy) {
        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        if (pendingTranslationY >= getOffsetRange() && (pendingTranslationY <= 0)) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        /**
         滚动过程中更新
         */
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        /**
         * 计算透明值
         */
        float i = (DependencyHeight + child.getTranslationY()) / DependencyHeight;
        img.setAlpha(i);

        float halfOfDis = dy / 4.0f;
        if (!canScroll(child, halfOfDis)) {
            //注意，要把Title滑动到toolbar下面，要把放大还原，要么title会显示一半
            title.setScaleX(1);
            title.setScaleY(1);
            child.setTranslationY(halfOfDis > 0 ? getOffsetRange() : 0);
        } else {
            child.setTranslationY(child.getTranslationY() - halfOfDis);
            if (Math.abs(child.getTranslationY()) > 0) {
                /**
                 * 放大title
                 */
                title.setScaleX(1f + 1f - i);
                title.setScaleY(1f + 1f - i);
            }


        }
        //consumed all scroll behavior after we started Nested Scrolling
        consumed[1] = dy;
    }

    /**
     * 动画下滑过渡
     *
     * @param coordinatorLayout
     * @param child
     * @param dy
     */
    public void updateDownStatus(CoordinatorLayout coordinatorLayout, View child, int dy) {
        /**
         * 计算透明值
         */
        float i = (DependencyHeight + child.getTranslationY()) / DependencyHeight;
        img.setAlpha(i);
        title.setScaleX(1f + 1f - i);
        title.setScaleY(1f + 1f - i);

    }


    public int getDependencyHeight() {
        return DependencyHeight;
    }
}
