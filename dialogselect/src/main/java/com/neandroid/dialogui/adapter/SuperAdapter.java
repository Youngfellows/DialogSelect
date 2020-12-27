package com.neandroid.dialogui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neandroid.dialogui.holder.SuperItemHolder;
import com.neandroid.dialogui.listener.OnItemClickListener;

import java.util.List;

/**
 * 描 述：RecyclerView的Adapter的基类
 */
public abstract class SuperAdapter<T> extends Adapter<ViewHolder> {

    protected String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     */
    protected Context mContext;

    protected LayoutInflater mLayoutInflater;

    /**
     * 接收传递过来的数据
     */
    protected List<T> mDatas;

    /**
     * 获得holder
     */
    private SuperItemHolder baseHolder;

    /**
     * 条目点击点击回调
     */
    protected OnItemClickListener mItemClickListener;

    public SuperAdapter(Context context, List<T> mDatas) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        setDatas(mDatas);
    }

    /**
     * 获得Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getLayoutRes(), parent, false);
        return getItemHolder(view);
    }

    /**
     * 为Holder绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            baseHolder = (SuperItemHolder) holder;
            baseHolder.setPosition(position); //为Holder绑定数据
            baseHolder.setData(mDatas.get(position), countPosition(position));
        }
    }

    /**
     * 1top 2midle 3bottom 4all
     */
    protected int countPosition(int position) {
        return 2;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 设置列表数据集
     *
     * @param mDatas 数据集
     */
    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 获取条目Layout资源
     */
    public abstract int getLayoutRes();

    /**
     * 获得Holder
     */
    public abstract SuperItemHolder getItemHolder(View view);

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}