package com.neandroid.dialogui.listener;

import android.content.Context;

import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.config.DialogConfig;

import java.util.List;

public class DialogAssigner implements Assignable {

    private static DialogAssigner instance;

    private DialogAssigner() {

    }

    public static DialogAssigner getInstance() {
        if (instance == null) {
            instance = new DialogAssigner();
        }
        return instance;
    }

    @Override
    public BuildBean assignSheet(Context context, List<TieBean> datas, String title, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.itemListener = listener;
        bean.mLists = datas;
        bean.title = title;
        bean.bottomTxt = bottomTxt;
        bean.gravity = gravity;
        bean.isVertical = true;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_SINGLE_CHOOSE_SHEET;
        return bean;
    }
}
