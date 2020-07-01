package com.nut2014.newtech.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author feiltel 2020/5/27 0027
 */
public class MyFlowLayout extends ViewGroup {
    private static final String TAG="FlowLayout";
    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        //临时宽度 临时高度
        int mLeftHeight = 0;
        int mLeftWidth = 0;
        //实际测量的高度宽度
        int maxHeight=0;
        int maxWidth=0;
        final int widthSize =  MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);
            mLeftWidth+=childAt.getMeasuredWidth();
            maxWidth+=childAt.getMeasuredWidth();
            //如果子view 相加的宽度大于总宽度
            if (mLeftWidth>widthSize){
                //加上上一行的高度
                maxHeight += mLeftHeight;

                //初始化当前宽度当前宽度高度
                mLeftWidth = childAt.getMeasuredWidth();
                mLeftHeight = childAt.getMeasuredHeight();
            }else {
                //获取当前行数总最高的一行
                mLeftHeight = Math.max(mLeftHeight,   childAt.getMeasuredHeight());
            }
        }
        maxHeight += mLeftHeight;

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        //childLeft和childTop代表在StaggerLayout的坐标系中，能够用来layout子View的区域的
        //左上角的顶点的坐标。
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();

        //childRight代表在StaggerLayout的坐标系中，能够用来layout子View的区域的
        //右边那条边的坐标。
        final int childRight = r -  l - getPaddingRight();


        /*
          curLeft和curTop代表StaggerLayout准备用来layout子View的起点坐标，这个点的坐标随着
          子View一个一个地被layout，在不断变化，有点像数据库中的Cursor，指向下一个可用区域。
          maxHeight代表当前行中最高的子View的高度，当需要换行时，curTop要加上该值，以确保新行中
          的子View不会与上一行中的子View发生重叠。
         */
        int curLeft, curTop, maxHeight;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            int curWidth, curHeight;
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //用来判断是否应当将该子View放到下一行
            if (curLeft + curWidth >= childRight) {
                    /*
                    需要移到下一行时，更新curLeft和curTop的值，使它们指向下一行的起点
                    同时将maxHeight清零。
                     */
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //所有的努力只为了这一次layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //更新maxHeight和curLeft
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }

}
