package com.guoyi.cosutmui.demo4;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.guoyi.cosutmui.demo4.behavior.UcBehaviorHelper;


/**
 * Author:Created by Credit on 2017/3/31  15:57.
 * Email:credit_yi@163.com
 * Description:{头部打开时，不可以左右滑动}
 */

public class HeadStateViewPager extends ViewPager {
    private UcBehaviorHelper mHelper;

    public HeadStateViewPager(Context context) {
        super(context);
        mHelper = UcBehaviorHelper.getInstance();
    }

    public HeadStateViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = UcBehaviorHelper.getInstance();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mHelper.isOpen()) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mHelper.isOpen()) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
