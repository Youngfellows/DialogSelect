package com.neandroid.dialogui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.neandroid.dialogui.R;


/**
 * 限制宽高的RecyclerView
 */
public class MaxLimitRecyclerView extends RecyclerView {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 最到高度
     */
    private int mMaxHeight = -1;

    /**
     * 最大宽度
     */
    private int mMaxWidth = -1;

    /**
     * 最多多少个item
     */
    private int mMaxCount = -1;

    public MaxLimitRecyclerView(Context context) {
        this(context, null);
    }

    public MaxLimitRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxLimitRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (getContext() != null && attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaxLimitRecyclerView);
                if (typedArray.hasValue(R.styleable.MaxLimitRecyclerView_limit_maxHeight)) {
                    mMaxHeight = typedArray.getDimensionPixelOffset(R.styleable.MaxLimitRecyclerView_limit_maxHeight, -1);
                }
                if (typedArray.hasValue(R.styleable.MaxLimitRecyclerView_limit_maxWidth)) {
                    mMaxWidth = typedArray.getDimensionPixelOffset(R.styleable.MaxLimitRecyclerView_limit_maxWidth, -1);
                }
                if (typedArray.hasValue(R.styleable.MaxLimitRecyclerView_limit_maxCount)) {
                    mMaxCount = typedArray.getInteger(R.styleable.MaxLimitRecyclerView_limit_maxCount, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (typedArray != null) {
                    typedArray.recycle();
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthSpec, heightSpec);
        //        boolean needLimit = false;
        //        if (mMaxHeight >= 0 || mMaxWidth >= 0) {
        //            needLimit = true;
        //        }
        //        if (needLimit) {
        //            int limitHeight = getMeasuredHeight();
        //            int limitWith = getMeasuredWidth();
        //            if (getMeasuredHeight() > mMaxHeight) {
        //                limitHeight = mMaxHeight;
        //            }
        //            if (getMeasuredWidth() > mMaxWidth) {
        //                limitWith = mMaxWidth;
        //            }
        //            setMeasuredDimension(limitWith, limitHeight);
        //        } else {
        //            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        }

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int limitWith = getMeasuredWidth();//宽度
        int limitHeight = getMeasuredHeight();//高度
        Log.d(TAG, "onMeasure:: widthSize:" + widthSize + ",heightSize:" + heightSize);
        Log.d(TAG, "onMeasure:: limitWith:" + limitWith + ",limitHeight:" + limitHeight);

        //测量高度
        int childCount = getChildCount();
        Log.d(TAG, "onMeasure:: childCount:" + childCount + ",mMaxCount:" + mMaxCount + ",mMaxWidth:" + mMaxWidth + ",mMaxHeight:" + mMaxHeight);
        if (childCount > 0) {
            View child = getChildAt(0);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int itemCount = getAdapter().getItemCount(); // item个数
            int itemHeight = child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom() + params.topMargin + params.bottomMargin; // item高度,单位ps像素
            int itemWidth = child.getWidth() + getPaddingLeft() + getPaddingRight() + params.leftMargin + params.rightMargin; // item宽度,单位ps像素
            Log.d(TAG, "onMeasure:: itemWidth:" + itemWidth + ",itemHeight:" + itemHeight);
            if (mMaxCount > 0 && childCount > mMaxCount) {
                limitHeight = mMaxCount * itemHeight;
                Log.d(TAG, "onMeasure::  ... mMaxCount ");
            } else {
                if (mMaxHeight > 0) {
                    //                    int max = itemCount * dip2px(itemHeight);// 把item的高度转成px
                    int max = itemCount * itemHeight;// 把item的高度转成px
                    limitHeight = Math.min(max, (mMaxHeight / itemHeight) * itemHeight);
                    Log.d(TAG, "onMeasure::  ... mMaxHeight ");
                }
            }

            //测量宽度
            if (mMaxWidth > itemWidth) {
                limitWith = mMaxWidth;
            }else {
                limitWith = widthMeasureSpec;
            }

            Log.d(TAG, "onMeasure:: 设置最大宽高,limitWith:" + limitWith + ",limitHeight:" + limitHeight);
            setMeasuredDimension(limitWith, limitHeight);
            //setMeasuredDimension(widthMeasureSpec, limitHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, Resources.getSystem().getDisplayMetrics());
    }

}
