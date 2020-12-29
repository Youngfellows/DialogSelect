package com.neandroid.dialogui.holder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.neandroid.dialogui.bean.BuildBean;


/**
 * 描 述：根布局 holder
 */
public abstract class SuperHolder {

    protected String TAG = this.getClass().getSimpleName();

    public Handler mHandler;

    /**
     * 根布局
     */
    public View rootView;

    public SuperHolder(Context context) {
        mHandler = new Handler(Looper.getMainLooper());
        rootView = View.inflate(context, setLayoutRes(), null);
        findViews();
    }

    /**
     * 根布局加载完成
     */
    protected abstract void findViews();

    /**
     * 设置对话框布局
     */
    @LayoutRes
    protected abstract int setLayoutRes();

    /**
     * 分配数据和事件
     *
     * @param context
     * @param bean    对话框实体
     */
    public abstract void assingDatasAndEvents(Context context, BuildBean bean);

    /**
     * 更新对话框数据集
     *
     * @param bean 对话框实体
     */
    public void refreshDatas(BuildBean bean) {

    }

}
