package com.neandroid.dialogui.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.neandroid.dialogui.bean.BuildBean;


/**
 * 描 述：根布局 holder
 */
public abstract class SuperHolder {

    /**
     * 根布局
     */
    public View rootView;

    public SuperHolder(Context context) {
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
     * @param bean
     */
    public abstract void assingDatasAndEvents(Context context, BuildBean bean);

}