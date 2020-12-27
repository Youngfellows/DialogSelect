package com.neandroid.dialogui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.holder.SuperItemHolder;
import com.neandroid.dialogui.holder.TieItemHolder;

import java.util.List;

/**
 * 描 述：单选对话框适配器
 */
public class SingleChooseAdapter extends SuperAdapter<TieBean> implements TieItemHolder.OnCheckedChangeListener {

    private boolean isItemType;

    public SingleChooseAdapter(Context mContext, List<TieBean> list) {
        super(mContext, list);
    }

    public SingleChooseAdapter(Context mContext, List<TieBean> list, boolean isItemType) {
        super(mContext, list);
        this.isItemType = isItemType;
        TieItemHolder.setOnCheckedChangeListener(this);
    }

    /**
     * @param view 已经加载好的Holder条目
     * @return 加载条目 Holder
     */
    @Override
    public SuperItemHolder getItemHolder(View view) {
        return new TieItemHolder(mContext, mItemClickListener, view);
    }

    /**
     * @return 返回条目layout资源
     */
    @Override
    public int getLayoutRes() {
        return R.layout.dialogui_holder_item_tie;
    }

    /**
     * 1top 2midle 3bottom 4all
     */
    protected int countPosition(int position) {
        if (isItemType) {
            if (mDatas.size() == 1) {
                return 4;
            }
            if (mDatas.size() > 1) {
                if (position == 0) {
                    return 1;
                } else if (position == mDatas.size() - 1) {
                    return 3;
                }
            }
            return 2;
        } else {
            return super.countPosition(position);
        }
    }

    @Override
    public void onCheckedChanged(View view, boolean isChecked, int position) {
        Log.i(TAG, "onCheckedChanged:: isChecked:" + isChecked + ",position:" + position);
        List<TieBean> datas = getDatas();
        TieBean tieBean = datas.get(position);
        tieBean.setSelect(isChecked);
        //setDatas(datas);
        for (int i = 0; i < datas.size(); i++) {
            TieBean bean = datas.get(i);
            Log.w(TAG, "onCheckedChanged: " + i + "," + bean);
        }
        notifyDataSetChanged();
    }
}
