package com.neandroid.dialogui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;

import java.util.List;

/**
 * 描 述：构建弹出框样式方法
 */
public interface Assignable {

    /**
     * 单选框
     */
    //BuildBean assignSingleChoose(Activity context, CharSequence title, final int defaultChosen, final CharSequence[] words, boolean cancleable, boolean outsideTouchable,
    //                             final DialogUIItemListener listener);

    /**
     * 多选选框
     */
    //BuildBean assignSingleChoose(Activity context, CharSequence title, final int defaultChosen, final CharSequence[] words, boolean cancleable, boolean outsideTouchable,
    //                             final DialogUIItemListener listener);

    /**
     * 中间弹出列表
     */
    BuildBean assignSheet(Context context, List<TieBean> datas, String title, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, final DialogUIItemListener listener);
}
