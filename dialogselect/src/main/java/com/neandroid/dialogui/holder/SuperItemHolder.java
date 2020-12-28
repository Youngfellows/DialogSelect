package com.neandroid.dialogui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;

import com.neandroid.dialogui.listener.OnItemClickListener;


/**
 * 描 述：RecyclerView的ViewHolder的基类
 */
public abstract class SuperItemHolder<T> extends ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 加载得到的数据
     */
    public T mDatas;

    public int itemPositionType;

    /**
     * 只有当该holder作为item使用，并且使用带参构造函数实例化position才有意义，使用无参构造函数则position没有意义
     */
    protected int position;

    /**
     * 条目点击监听回调
     */
    private OnItemClickListener mItemClickListener;

    public SuperItemHolder(Context mContext, OnItemClickListener listener, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        this.mContext = mContext;
        this.mItemClickListener = listener;
    }

    /**
     * 设置数据
     */
    public void setData(T data, int itemPositionType) {
        this.mDatas = data;
        this.itemPositionType = itemPositionType;
        refreshView();
    }

    /**
     * 刷新数据
     */
    public abstract void refreshView();

    /**
     * 获得数据
     */
    public T getData() {
        return mDatas;
    }

    /**
     * 当复用holder的时候，需要调用该方法来同步holder对应的索引位置
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 回调item 条目点击事件
     *
     * @param view 点击的item
     */
    @Override
    public void onClick(View view) {
        //Log.d(TAG, "onClick:: " + view.getTag() + ",position:" + position);
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(position);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        //Log.d(TAG, "onLongClick:: " + view.getTag() + ",position:" + position);
        if (mItemClickListener != null) {
            mItemClickListener.onItemLongClick(position);
        }
        return false;
    }
}