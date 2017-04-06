package com.guoyi.cosutmui.demo2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.guoyi.cosutmui.R;
import com.guoyi.cosutmui.utils.DensityUtil;


/**
 * Author:Created by Credit on 2017/3/28  15:13.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class NinePhotoView extends ViewGroup {

    private static String TAG = "NinePhotoView";
    private int[] Rids = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i};

    //纵向间隙
    private int vSpace = DensityUtil.dip2px(getContext(), 10);
    //竖间隙
    private int hSpace = DensityUtil.dip2px(getContext(), 10);


    /**
     * +号按钮 个数
     */
    private static int add_count = 1;

    private int MAX_NUM = 9 + add_count;


    private int viewCount = 0;


    private int chindWidth = 0;
    private int chindHeight = 0;
    private View addPhotoView;


    public NinePhotoView(Context context) {
        super(context);
    }

    public NinePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ninephoto, 0, 0);
        vSpace = t.getDimensionPixelSize(R.styleable.ninephoto_ninephoto_vpace, vSpace);
        hSpace = t.getDimensionPixelSize(R.styleable.ninephoto_ninephoto_hpace, hSpace);
        t.recycle();

        addPhotoView = new View(context);
        addView(addPhotoView);
        viewCount = 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wm = MeasureSpec.getSize(widthMeasureSpec);
        int hm = MeasureSpec.getSize(heightMeasureSpec);

        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        int layMargin = (layoutParams.leftMargin + layoutParams.rightMargin) / 3;

        //计算平均宽度
        chindWidth = (wm - (2 * hSpace)) / 3;
        chindHeight = chindWidth;

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();

            //计算左位置
            lp.left = i % 3 * (chindWidth + hSpace) + layMargin;

            //计算top位置
            lp.top = i / 3 * (chindWidth + vSpace);
        }


        /**
         *若count小于3时，表明还没有满足一行的宽度
         */
        if (childCount < 3) {
            wm = childCount * (chindWidth + hSpace);
        }
        hm = ((childCount + 3) / 3) * (chindWidth + vSpace);
        setMeasuredDimension(wm, hm);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        Log.e(TAG, "onLayout: max:" + viewCount + "  childCount:" + childCount);
        for (int i = 0; i < childCount; i++) {
            View childAt = this.getChildAt(i);
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
            /**
             * 调用layout方法，根据我们measure阶段获得的LayoutParams中的left和top字段，
             * 也很好对每个子View进行位置排列。然后判断在图片未达到最大值9张时，默认最后一张是+号图片，然后设置点击事件，弹出对话框供用户选择操作
             */
            childAt.layout(lp.left, lp.top, lp.left + chindWidth, lp.top + chindHeight);
            if (i == viewCount - 1 && viewCount != MAX_NUM) {

                childAt.setBackgroundResource(R.mipmap.link_icon);
                childAt.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPhotoBtnClick();
                    }
                });
            } else if (i + 1 != MAX_NUM) {
                childAt.setBackgroundResource(Rids[i]);
                childAt.setOnClickListener(null);
            } else {
                /**
                 * 当达到maxnum时，删除+按钮
                 */
                removeViewAt(i);
            }
        }

    }

    public void addPhoto() {
        if (viewCount < MAX_NUM) {
            View newChild = new View(getContext());
            addView(newChild);
            viewCount++;
            requestLayout();
            invalidate();
        }
    }


    public void addPhotoBtnClick() {
        final CharSequence[] items = {"Take Photo", "Photo from gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                addPhoto();
            }

        });
        builder.show();
    }

    public void setMAX_NUM(int MAX_NUM) {
        this.MAX_NUM = MAX_NUM + add_count;
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public class LayoutParams extends ViewGroup.LayoutParams {

        public int left;
        public int top;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


        @Override
        public String toString() {
            return "LayoutParams{" +
                    "left=" + left +
                    ", top=" + top +
                    '}';
        }
    }
}
