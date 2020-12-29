package com.neandroid.dialogui.listener;


public abstract class DialogUIListener {

    /**
     * 确定
     */
    public abstract void onPositive();

    /**
     * 否定
     */
    public abstract void onNegative();

    /**
     * 中立
     */
    public void onNeutral() {
    }

    /**
     * 取消
     */
    public void onCancle() {
    }

    /**
     * 获取输入内容
     */
    public void onGetInput(CharSequence inputText) {
    }

    /**
     * 获取实时输入内容
     */
    public void onRealtimeInput(CharSequence inputText) {

    }

    /**
     * 获取选择集合
     */
    public void onGetChoose(boolean[] states) {

    }

}
