package com.neandroid.dialogui.listener;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.holder.SearchDialogHolder;
import com.neandroid.dialogui.holder.SheetHolder;
import com.neandroid.dialogui.utils.ToolUtils;

public class Buildable {

    /**
     * 根据类型刷新对话框
     *
     * @param bean 对话框实体
     * @return
     */
    protected BuildBean refreshByType(BuildBean bean) {
        if (bean.holder != null) {
            bean.holder.refreshDatas(bean);
        }
        return bean;
    }

    /**
     * 根据类型创建对话框
     *
     * @param bean 对话框实体
     * @return
     */
    protected BuildBean buildByType(BuildBean bean) {
        ToolUtils.fixContext(bean);
        switch (bean.type) {
            case DialogConfig.TYPE_CHOOSE_SHEET:
                buildChooseSheet(bean);
                break;
            case DialogConfig.TYPE_SEARCH_SHEET:
                buildSearchSheet(bean);
                break;
            default:
                break;
        }
        ToolUtils.setDialogStyle(bean);
        ToolUtils.setCancelable(bean);
        return bean;
    }

    /**
     * 列表选择对话框
     *
     * @param bean 弹出框实体
     * @return
     */
    protected BuildBean buildChooseSheet(BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.mContext);
        SheetHolder holder = new SheetHolder(bean.mContext, true);
        builder.setView(holder.rootView);
        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        bean.holder = holder;
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

    /**
     * 列表搜索对话框
     *
     * @param bean 弹出框实体
     * @return
     */
    protected BuildBean buildSearchSheet(BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.mContext);
        SearchDialogHolder holder = new SearchDialogHolder(bean.mContext, true);
        builder.setView(holder.rootView);
        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        bean.holder = holder;
        holder.assingDatasAndEvents(bean.mContext, bean);
        return bean;
    }
}
