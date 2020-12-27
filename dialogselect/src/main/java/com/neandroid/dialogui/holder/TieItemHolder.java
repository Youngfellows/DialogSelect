package com.neandroid.dialogui.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.listener.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 描 述：列表选择的item
 */
public class TieItemHolder extends SuperItemHolder<TieBean> {

    RelativeLayout llTie;
    TextView tvTitle;
    CompoundButton rbControl;
    private OnCheckedChangeListener checkedChangeListener;

    public TieItemHolder(Context mContext, OnItemClickListener listener, View itemView, OnCheckedChangeListener checkedChangeListener) {
        super(mContext, listener, itemView);
        llTie = (RelativeLayout) itemView.findViewById(R.id.ll_tie);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        rbControl = itemView.findViewById(R.id.rb_control);
        rbControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged:: position:" + position + ",isChecked:" + isChecked);
            }
        });
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
        rbControl.setTag(new Integer(position));
        Log.d(TAG, "refreshView:: " + position + "," + data.isSelect());
        if (data.isSelect()) {
            rbControl.setChecked(true);
        } else {
            rbControl.setChecked(false);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        boolean isChecked = rbControl.isChecked();
        int tagPosition = (int) rbControl.getTag();
        Log.d(TAG, "onClick:: click item ,position:" + position + ",tagPosition:" + tagPosition + ",checkedChangeListener:" + checkedChangeListener);
        rbControl.setChecked(!isChecked);
        if (checkedChangeListener != null) {
            checkedChangeListener.onCheckedChanged(view, rbControl.isChecked(), tagPosition);
        }

    }

    public interface OnCheckedChangeListener {
        /**
         * 选中状态改变
         *
         * @param view
         * @param isChecked 是否选中
         * @param position  位置
         */
        void onCheckedChanged(View view, boolean isChecked, int position);
    }
}
