package com.neandroid.dialogui.listener;


public interface OnItemClickListener {

    /**
     * Item 点击事件
     *
     * @param position
     */
    void onItemClick(int position);

    /**
     * Item 长安事件
     *
     * @param position
     */
    void onItemLongClick(int position);
}
