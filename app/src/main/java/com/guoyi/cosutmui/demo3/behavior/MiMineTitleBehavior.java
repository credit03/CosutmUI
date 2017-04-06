package com.guoyi.cosutmui.demo3.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.guoyi.cosutmui.R;
import com.guoyi.cosutmui.helper.HeaderScrollingViewBehavior;
import com.guoyi.cosutmui.utils.DensityUtil;

import java.util.List;

/**
 * Author:Created by Credit on 2017/3/30  09:59.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class MiMineTitleBehavior extends HeaderScrollingViewBehavior {


    private static String TAG = "MiMineTitleBehavior";
    private Context context;
    private View mContent;
    private int toolBarOffset;
    private int toobarHeight;
    private int mOffsetDelta;
    private int statusHeight;
    private MiMineContentBehavior mPagerBehavior;

    public MiMineTitleBehavior() {
    }

    public MiMineTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // TODO: 2017/3/30 要使用 getDimensionPixelOffset，不然少一个1px
        toolBarOffset = context.getResources().getDimensionPixelOffset(R.dimen.toobar_offset_height);
        toobarHeight = context.getResources().getDimensionPixelOffset(R.dimen.toobar_height);
        statusHeight = DensityUtil.getStatusBarHeight(context);
    }

    @Override
    public View findFirstDependency(List<View> views) {

        if (views != null) {
            for (View v : views) {
                if (isDependOn(v)) {
                    return v;
                }
            }
        }
        return null;
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


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        // FIXME: 16/7/27 不知道为啥在XML设置-55dip,不起作用，所以使用代码重设置一次
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = toolBarOffset;
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        boolean dependOn = isDependOn(dependency);
        if (dependOn) {
            mContent = parent.findViewById(R.id.content);
            mPagerBehavior = (MiMineContentBehavior) ((CoordinatorLayout.LayoutParams) mContent.getLayoutParams()).getBehavior();
            mOffsetDelta = dependency.getMeasuredHeight() + context.getResources().getDimensionPixelOffset(R.dimen.tv_height) - toobarHeight;
        }
        return dependOn;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        /**
         * 使用mContent为依赖，因为mContent的translationY滑动改变
         */
        offsetChildAsNeeded(parent, child, mContent);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {


        float translationY = dependency.getTranslationY();

        Log.d(TAG, "offsetChildAsNeeded: statusHeight " + statusHeight + "  translationY:" + translationY + " mOffsetDelta:" + mOffsetDelta + " toolBarOffset:" + toolBarOffset + "   toobarHeight:" + toobarHeight);

        if (translationY == 0.0f) {
            child.setTranslationY(toolBarOffset);
        } else if (Math.abs(translationY) >= mOffsetDelta) {
            child.setTranslationY(toobarHeight);
        } else {
            float v2 = translationY / mOffsetDelta;
            child.setTranslationY(Math.abs(toobarHeight * v2));

        }


    }


    @Override
    public boolean onInterceptTouchEvent(final CoordinatorLayout parent, final View child, MotionEvent ev) {

        /**
         * 监听TouchEvent 抬起时事件
         */
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            final float translationY = mContent.getTranslationY();
            float v = (mOffsetDelta + translationY) / mOffsetDelta;
            Log.d(TAG, "onInterceptTouchEvent:ACTION_UP== " + v);
            /**
             * v > 0.01f过滤震荡信号
             * 显示toolbar后，不同机型会出现不同的问题，在魅族手机，完成是精准。但在小米手机再继续往上滑动，
             * 还可以滑动那么一点点，产生 0.008333334比，我的计算不是精准的，所以解决方式如下
             */
            if (v > 0.01f && v < 0.55f) {//当滑动比小于x时,则显示toolbar
                Log.d(TAG, "onInterceptTouchEvent:当滑动比小于x时,则显示toolbar ");
                //  mContent.setTranslationY(-mOffsetDelta);

                ViewCompat.animate(mContent).translationY(-mOffsetDelta).setDuration(300).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        mContent.clearAnimation();
                        mContent.setTranslationY(-mOffsetDelta);
                        mPagerBehavior.onNestedPreScroll(parent, mContent, child, 0, (int) (mOffsetDelta + translationY), new int[2]);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });

                ViewCompat.animate(child).translationY(toobarHeight).setDuration(300).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        child.setTranslationY(toobarHeight);
                        child.clearAnimation();

                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });


            } else if (v != 1.0f && v >= 0.55f) {//当滑动比大于x时,则关闭toolbar
                Log.d(TAG, "onInterceptTouchEvent:当滑动比大于x时,则关闭toolbar ");
                ViewCompat.animate(mContent).translationY(0).setDuration(300).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        mContent.clearAnimation();
                        mContent.setTranslationY(0);
                        mPagerBehavior.onNestedPreScroll(parent, mContent, child, 0, 0, new int[2]);

                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(View view) {
                        //更新动画过渡
                        mPagerBehavior.updateDownStatus(parent, view, (int) (mOffsetDelta + view.getTranslationY()));
                    }
                });

                ViewCompat.animate(child).translationY(toolBarOffset).setDuration(300).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        child.setTranslationY(toolBarOffset);
                        child.clearAnimation();
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });

                //  child.setTranslationY(toolBarOffset);
            }
        }

        return super.onInterceptTouchEvent(parent, child, ev);
    }


    public int getToolBarOffset() {
        return toolBarOffset;
    }

    public int getToobarHeight() {
        return toobarHeight;
    }
}
