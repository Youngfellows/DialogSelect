package com.neandroid.dialogui.config;

import android.support.annotation.ColorRes;

import com.neandroid.dialogui.R;

import java.security.PublicKey;


/**
 * 描 述：公共配置
 */
public class DialogConfig {

    @ColorRes
    public static int iosBtnColor = R.color.ios_btntext_blue;
    @ColorRes
    public static int lvItemTxtColor = R.color.text_item_33;
    @ColorRes
    public static int mdBtnColor = R.color.btn_alert;
    @ColorRes
    public static int titleTxtColor = R.color.text_title_11;
    @ColorRes
    public static int msgTxtColor = R.color.text_title_11;
    @ColorRes
    public static int inputTxtColor = R.color.text_input_44;


    public static CharSequence dialogui_btnTxt1 = "确定";
    public static CharSequence dialogui_btnTxt2 = "取消";
    public static CharSequence dialogui_bottomTxt = "取消";

    public static final int TYPE_CHOOSE_SHEET = 1;//列表选择对话框
    public static final int SINGLE_CHOOSE = 2;//单选对话框
    public static final int MULTI_CHOOSE = 3;//多选对话框
    public static final int TYPE_MD_LOADING = 4;
}
