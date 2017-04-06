package com.guoyi.cosutmui.demo4.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.helper.HeaderScrollingViewBehavior;


/**
 * Author:Created by Credit on 2017/3/31  10:10.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public abstract class UcBaseBehavior extends HeaderScrollingViewBehavior {


    protected Context mContext;

    protected boolean sureInit = false;

    public UcBaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        UcBehaviorHelper.getInstance().put(this);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        boolean onDepend = isOnDepend(dependency);
        if (onDepend && !sureInit) {
            initView(parent, child, dependency);
        }
        return onDepend;
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {


        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        sureInit = false;
        UcBehaviorHelper.getInstance().delete(this);
    }


    /**
     * 判断依赖view
     *
     * @param depend
     * @return
     */
    protected abstract boolean isOnDepend(View depend);


    /**
     * 这个方法会在创建时，多次调用的
     *
     * @param parent
     * @param child
     * @param dependency
     */
    protected abstract void initView(CoordinatorLayout parent, View child, View dependency);


    /**
     * 打开头部页面
     */
    public abstract void openHeadPager(int Duration);

    /**
     * 关闭头部页面
     */
    public abstract void closeHeadPager(int Duration);


}
