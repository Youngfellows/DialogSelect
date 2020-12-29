package com.neandroid.dialogui.listener;


import java.util.List;

public abstract class DialogUIItemListener {

    /**
     * item点击事件
     *
     * @param text      条目内容
     * @param position  item 位置
     * @param isChecked 是否选中
     */
    public abstract void onItemClick(CharSequence text, int position, boolean isChecked);

    /**
     * item长按事件
     *
     * @param text      条目内容
     * @param position  item 位置
     * @param isChecked 是否选中
     */
    public void onItemLongClick(CharSequence text, int position, boolean isChecked) {

    }

    /**
     * 多项选中底部点击事件
     *
     * @param multiSelected 选中列表集合
     */
    public void onBottomBtnMultiChooseClick(List<Integer> multiSelected) {

    }

    /**
     * 底部取消点击事件
     */
    public void onBottomBtnCancelClick() {

    }

    /**
     * 底部点击事件
     */
    public void onBottomBtnClick() {

    }
}
