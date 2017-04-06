package com.guoyi.cosutmui.demo4.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.R;

import java.util.List;

/**
 * Author:Created by Credit on 2017/3/31  10:21.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class UcContentBehavior extends UcBaseBehavior {

    private static final String TAG = "UcContentBehavior";
    /**
     * 可移动总高度
     */
    private int mOffsetDelta;
    /**
     * 头部view高度
     */
    private int mHeadPageHeight;
    /**
     * Toolbar高度
     */
    private int mToolbarHeight;
    /**
     * Tablayout高度
     */
    private int mTabLayoutHeight;

    /**
     * 依赖view,即 R.id.uc_head_pager的view
     */
    private View mDependencyView;

    private View mContnetView;

    private UcBehaviorHelper mHelper;


    public UcContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = UcBehaviorHelper.getInstance();
    }

    @Override
    protected boolean isOnDepend(View depend) {
        return depend != null && depend.getId() == R.id.uc_head_pager;
    }

    @Override
    protected void initView(CoordinatorLayout parent, View child, View dependency) {
        mDependencyView = dependency;
        mContnetView = child;
        mHeadPageHeight = dependency.getMeasuredHeight();
        mToolbarHeight = parent.findViewById(R.id.uc_toolbar_pager).getMeasuredHeight();
        mTabLayoutHeight = parent.findViewById(R.id.uc_tab_pager).getMeasuredHeight();
        /**
         * 计算可以滑动高度
         */
        mOffsetDelta = mHeadPageHeight - mToolbarHeight - mTabLayoutHeight;
        /**
         * 保存滑动高度
         */
        mHelper.setOffsetDelta(mOffsetDelta);
        /**
         * 这是一个大bug，若不设置，(mToolbarHeight + mTabLayoutHeight)高度的内容给遮挡不会显示
         */
        if ((mToolbarHeight + mTabLayoutHeight) > 0) {
            child.setPadding(child.getPaddingLeft(), child.getPaddingRight(), child.getPaddingTop(), mToolbarHeight + mTabLayoutHeight);
            sureInit = true;
        }

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

    private float lastVelocityY = 0;

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        // consumed the flinging behavior until Closed
        //监听快速滑动，是否要消费事件，若不判断recyclerview会快速滑动
        lastVelocityY = velocityY;
        return !isClosed(child);
    }


    private boolean isClosed(View child) {
        return child.getTranslationY() == getOffsetRange();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (ViewCompat.SCROLL_AXIS_VERTICAL & nestedScrollAxes) != 0 && canScroll(child, 0) && isOnScrollTop(child, target) && mHelper.isCloseAfterEndabled();
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);


        float translationY = child.getTranslationY();
        /**
         * 计算缩放比,即折叠率
         */
        float i = translationY / getOffsetRange() / 5;

       // Log.d(TAG, "onNestedPreScroll: 折叠率:" + i + "translationY" + translationY + "mOffsetDelta:" + mOffsetDelta + " dy:" + dy);

        float halfOfDis = dy / 4.0f;
        if (!canScroll(child, halfOfDis)) {
            child.setTranslationY(halfOfDis > 0 ? getOffsetRange() : 0);
        } else if (Math.abs(dy) / 3 > 5) { //去抖动
            if (translationY - halfOfDis <= 0) {
                child.setTranslationY(translationY - halfOfDis);
                mDependencyView.setTranslationY((translationY * i));
            }

        }
        consumed[1] = dy;

    }


    /**
     * 判断是否recyclerview，若是recyclerview，则判断是否已经滑动到第一个item
     * to judge whether current recyclerview'item is at the top of recyclerview
     *
     * @param child
     * @param target
     * @return
     */
    private boolean isOnScrollTop(View child, View target) {
        if (target instanceof RecyclerView) {
            boolean isRVTop = ((LinearLayoutManager) ((RecyclerView) target)
                    .getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0;
            return isRVTop;
        }
        return true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

        final float translationY = child.getTranslationY();
        if (translationY >= 0) {
            //设置打开状态
            mHelper.setOpenState();
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            //设置关闭状态
            mHelper.setCloseState();
        }
        float v = (mOffsetDelta + translationY) / mOffsetDelta;
        //去抖动
        if (v > 0.001f && v < 0.5f || lastVelocityY >= 4000) {//当滑动比小于x时,则显示toolbar和快速滑动值
            mHelper.closeHeadPager();
        } else if (v >= 0.5f || lastVelocityY <= -4000) {//当滑动比大于x时,则关闭toolbar
            mHelper.openHeadPager();
        }
        lastVelocityY = 0;
    }


    @Override
    public void openHeadPager(int Duration) {
        ViewCompat.animate(mContnetView).translationY(0).setDuration(Duration)
                .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(View view) {
                        float translationY = view.getTranslationY();
                        /**
                         * 计算缩放比,即折叠率
                         */
                        float i = translationY / getOffsetRange() / 5;
                        mDependencyView.setTranslationY(translationY * i);
           /*     mDependencyView.setScaleX(1f - i);
                mDependencyView.setScaleY(1f - i);*/

                    }
                });
    }

    @Override
    public void closeHeadPager(int Duration) {
        mContnetView.animate().translationY(-mOffsetDelta).setDuration(Duration);
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


    public int getOffsetRange() {
        return -mOffsetDelta;
    }
}
