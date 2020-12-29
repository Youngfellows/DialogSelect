package com.neandroid.dialogui.listener;

import android.content.Context;
import android.view.Gravity;

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
    public BuildBean assignSheet(Context context, int chooseType, List<TieBean> datas, String title, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.mChooseType = chooseType;
        bean.itemListener = listener;
        bean.mLists = datas;
        bean.title = title;
        bean.bottomTxt = bottomTxt;
        bean.gravity = gravity;
        bean.isVertical = true;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_CHOOSE_SHEET;
        return bean;
    }


    @Override
    public BuildBean assignAlert(Context activity, List<TieBean> datas, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                                 CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, boolean cancleable, boolean outsideTouchable, DialogUIListener listener, DialogUIItemListener itemListener) {

        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.mLists = datas;
        bean.msg = msg;
        bean.title = title;
        bean.hint1 = hint1;
        bean.hint2 = hint2;
        bean.text1 = firstTxt;
        bean.text2 = secondTxt;
        bean.isVertical = isVertical;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = listener;
        bean.itemListener = itemListener;
        bean.type = DialogConfig.TYPE_SEARCH_SHEET;
        return bean;
    }
}
