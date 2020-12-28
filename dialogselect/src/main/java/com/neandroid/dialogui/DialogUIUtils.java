package com.neandroid.dialogui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;

import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.listener.DialogAssigner;
import com.neandroid.dialogui.listener.DialogUIItemListener;

import java.util.List;

public class DialogUIUtils {
    /**
     * 全局上下文
     */
    public static Context appContext;

    /**
     * 如果有使用到showTost...相关的方法使用之前需要初始化该方法
     */
    public static void init(Context appContext) {
        DialogUIUtils.appContext = appContext;
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(DialogInterface... dialogs) {
        if (dialogs != null && dialogs.length > 0) {
            for (DialogInterface dialog : dialogs) {
                if (dialog instanceof Dialog) {
                    Dialog dialog1 = (Dialog) dialog;
                    if (dialog1.isShowing()) {
                        dialog1.dismiss();
                    }
                } else if (dialog instanceof AppCompatDialog) {
                    AppCompatDialog dialog2 = (AppCompatDialog) dialog;
                    if (dialog2.isShowing()) {
                        dialog2.dismiss();
                    }
                }
            }

        }
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(BuildBean buildBean) {
        if (buildBean != null) {
            if (buildBean.dialog != null && buildBean.dialog.isShowing()) {
                buildBean.dialog.dismiss();
            }
            if (buildBean.alertDialog != null && buildBean.alertDialog.isShowing()) {
                buildBean.alertDialog.dismiss();
            }
        }
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /***
     * 中间弹出列表
     *
     * @param context          上下文
     * @param chooseType       单选/多选
     * @param datas            素组集合
     * @param gravity
     * @param bottomTxt        底部item文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     * @return
     */
    public static BuildBean showChooseSheet(Context context, int chooseType, List<TieBean> datas, String title, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        return DialogAssigner.getInstance().assignSheet(context, chooseType, datas, title, bottomTxt, gravity, cancleable, outsideTouchable, listener);
    }

}
