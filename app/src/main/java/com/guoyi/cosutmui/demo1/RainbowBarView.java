package com.guoyi.cosutmui.demo1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.guoyi.cosutmui.R;
import com.guoyi.cosutmui.utils.DensityUtil;


/**
 * Author:Created by Credit on 2017/3/28  10:45.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class RainbowBarView extends View {


    private int PaintSize = 0; //默认线条大小
    private int line_width = 0;//默认线条宽度
    private int line_space = 0;//默认线条间隙宽度
    private int color = 0;  //默认线条颜色
    private Context mContext;
    private Paint mPaint;
    private float speed = 5f;


    private int startX = 0;

    public RainbowBarView(Context context) {
        super(context);
        init(context);
    }

    public RainbowBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        //获取命名空间属性
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.rainbowbar, 0, 0);
        //获取属性值
        PaintSize = t.getDimensionPixelSize(R.styleable.rainbowbar_rainbowbar_line_size, PaintSize);
        line_width = t.getDimensionPixelOffset(R.styleable.rainbowbar_rainbowbar_line_width, line_width);
        line_space = t.getDimensionPixelOffset(R.styleable.rainbowbar_rainbowbar_line_space, line_space);
        color = t.getColor(R.styleable.rainbowbar_rainbowbar_color, color);
        t.recycle();
        //创建一个油漆
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(PaintSize);
        mPaint.setColor(color);

    }

    private void init(Context context) {
        this.mContext = context;
        PaintSize = DensityUtil.dip2px(context, 4);
        line_width = DensityUtil.dip2px(context, 40);
        line_space = DensityUtil.dip2px(context, 10);
        color = Color.parseColor("#1E88E5");
    }

    /*
    View 为我们提供了 onMeasure()  onLayout()  onDraw() 这样的方法，其实所谓的自定义View，也就是对onMeasure() onLayout() onDraw()
    requestLayout
   View重新调用一次layout过程。

   invalidate
   View重新调用一次draw过程

   forceLayout
   标识View在下一次重绘，需要重新调用layout过程。
     */


    int count = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 获取RainbowBarView测量宽度
         */
        float sw = this.getMeasuredWidth();

        /**
         * 这部分是用来计算当前的坐标起始点有没有在屏幕外面
         * 公式：设sw=720,line_width=20, line_space=10,startX=？
         *  sw + (line_width + line_space) - (sw % (line_width + line_space)=750-24=726
         *
         * 当第一矩形的start==726，则表明一个循环
         */
        if (startX >= sw + (line_width + line_space) - (sw % (line_width + line_space))) {
            startX = 0;
        } else {
            //speed表示每次矩形框移动量
            startX += speed;
        }
        float start = startX;
        /**
         * 绘制startX之后部分
         */
        while (start < sw) {
            canvas.drawLine(start, 5, start + line_width, 5, mPaint);
            start += (line_width + line_space);
        }


        /**
         * 绘制startX之前部分
         */
        start = startX - line_width - line_space;

        while (start >= -line_width) {
            canvas.drawLine(start, 5, start + line_width, 5, mPaint);
            start -= (line_width + line_space);
        }


        invalidate();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //   setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
        //MeasureSpec由32位 int值组成，高2位表示的是测量模式(specMode),后面的30位代表的是测量的大小(specSize)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         *
         测量模式：父View不对子View的大小做任何限制，可以是子布局需要的任意大小
         * Measure specification mode: The parent has not imposed any constraint
         * on the child. It can be whatever size it wants.
         public static final int UNSPECIFIED = 0 << MODE_SHIFT;

         已经为子View给出了确切的值 相当于给View的LayoutParams指定了具体数值，或者match_parent.
         * Measure specification mode: The parent has determined an exact size
         * for the child. The child is going to be given those bounds regardless
         * of how big it wants to be.
         public static final int EXACTLY     = 1 << MODE_SHIFT;

         *子View可以跟自己的测量大小一样大。对应于View的LayoutParams的wrap_content
         * Measure specification mode: The child can be as large as it wants up
         * to the specified size.
         public static final int AT_MOST     = 2 << MODE_SHIFT;
         */
    }
}
