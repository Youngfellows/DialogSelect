package com.neandroid.dialogui.listener;

import android.content.Context;

import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;

import java.util.List;

/**
 * 描 述：构建弹出框样式方法
 */
public interface Assignable {

    /**
     * 中间弹出列表
     */
    BuildBean assignSheet(Context context, int chooseType, List<TieBean> datas, String title, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, final DialogUIItemListener listener);

    /**
     * 搜索弹出框
     */
    BuildBean assignAlert(Context activity, List<TieBean> datas, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                          CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, boolean cancleable, boolean outsideTouchable, final DialogUIListener listener,DialogUIItemListener itemListener);

}
