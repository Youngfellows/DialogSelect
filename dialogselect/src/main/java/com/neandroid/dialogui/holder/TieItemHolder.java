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

    private Map<Integer, Boolean> map = new HashMap<>();

    RelativeLayout llTie;
    TextView tvTitle;
    CompoundButton rbControl;

    public TieItemHolder(Context mContext, OnItemClickListener listener, View itemView) {
        super(mContext, listener, itemView);
        llTie = (RelativeLayout) itemView.findViewById(R.id.ll_tie);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        rbControl = itemView.findViewById(R.id.rb_control);
        rbControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged:: position:" + position + ",isChecked:" + isChecked);
                if (isChecked == true) {
                    map.put(position, true);
                } else {
                    map.remove(position);
                }
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
        Log.d(TAG, "refreshView:: " + data.isSelect());
//        if (data.isSelect()) {
//            rbControl.setChecked(true);
//        }

        rbControl.setTag(new Integer(position));

        if (map != null && map.containsKey(position)) {
            rbControl.setChecked(true);
        } else {
            rbControl.setChecked(false);
        }
    }
}
