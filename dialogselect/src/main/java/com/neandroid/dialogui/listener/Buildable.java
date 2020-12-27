package com.neandroid.dialogui.listener;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.holder.SheetHolder;
import com.neandroid.dialogui.utils.ToolUtils;

public class Buildable {

    protected static int singleChosen;

    protected BuildBean buildByType(BuildBean bean) {
        ToolUtils.fixContext(bean);
        switch (bean.type) {
            case DialogConfig.TYPE_MULTI_CHOOSE_SHEET:
                buildMdMultiChoose(bean);
                break;
            case DialogConfig.TYPE_SINGLE_CHOOSE_SHEET:
                buildSingleChooseSheet(bean);
            default:
                break;
        }
        ToolUtils.setDialogStyle(bean);
        ToolUtils.setCancelable(bean);
        return bean;
    }

    /**
     * 多选列表对话框
     *
     * @param bean
     * @return 弹出框实体
     */
    protected BuildBean buildMdMultiChoose(final BuildBean bean) {
        return bean;
    }

    /**
     * 单选列表对话框
     *
     * @param bean 弹出框实体
     * @return
     */
    protected BuildBean buildSingleChooseSheet(BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.mContext);
        SheetHolder holder = new SheetHolder(bean.mContext, true);
        builder.setView(holder.rootView);
        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        if (bean.isVertical && !TextUtils.isEmpty(bean.bottomTxt) && TextUtils.isEmpty(bean.title)) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(R.drawable.dialogui_shape_wihte_round_corner);
        }
        holder.assingDatasAndEvents(bean.mContext, bean);//分配数据和事件
        return bean;
    }
}
