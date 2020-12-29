package com.neandroid.dialogui.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.listener.OnItemClickListener;


/**
 * 描 述：列表选择的item
 */
public class ItemHolder extends SuperItemHolder<TieBean> {

    RelativeLayout llTie;
    TextView tvTitle;

    public ItemHolder(Context mContext, OnItemClickListener listener, View itemView) {
        super(mContext, listener, itemView);
        llTie = (RelativeLayout) itemView.findViewById(R.id.ll_tie);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }

    /**
     * 刷新条目数据
     */
    @Override
    public void refreshView() {
        /**
         * 1top 2midle 3bottom 4all
         */
        if (itemPositionType == 1) {
            llTie.setBackgroundResource(R.drawable.dialogui_selector_all_top);
        } else if (itemPositionType == 3) {
            llTie.setBackgroundResource(R.drawable.dialogui_selector_all_bottom);
        } else if (itemPositionType == 4) {
            llTie.setBackgroundResource(R.drawable.dialogui_selector_all);
        } else {
            llTie.setBackgroundResource(R.drawable.dialogui_selector_all_no);
        }
        TieBean data = getData();//获取数据,并设置条目状态
        tvTitle.setText("" + data.getTitle());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick:: " + view.getTag());
        super.onClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick:: " + view.getTag());
        return super.onLongClick(view);
    }
}
