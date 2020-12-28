package com.neandroid.dialogui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.holder.SuperItemHolder;
import com.neandroid.dialogui.holder.TieItemHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描 述：列表选择对话框适配器
 */
public class SheetChooseAdapter extends SuperAdapter<TieBean> implements TieItemHolder.OnCheckedChangeListener {

    /**
     * 已经选择的item
     */
    private ArrayList<Integer> mSelected;

    /**
     * 对话框实体
     */
    private BuildBean mBuildBean;

    private boolean isItemType;

    public SheetChooseAdapter(Context mContext, List<TieBean> list) {
        super(mContext, list);
    }

    public SheetChooseAdapter(Context mContext, BuildBean bean, boolean isItemType) {
        super(mContext, bean.mLists);
        this.mBuildBean = bean;
        this.mSelected = new ArrayList<>();
        this.isItemType = isItemType;
        initSelected();
        TieItemHolder.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化已经选中列表
     */
    private void initSelected() {
        List<TieBean> mLists = mBuildBean.mLists;
        if (mLists != null) {
            int size = mLists.size();
            for (int i = 0; i < size; i++) {
                TieBean bean = mLists.get(i);
                boolean isSelect = bean.isSelect();//默认选中状态
                boolean isContains = mSelected.contains(i);//未包含选中
                if (isSelect && !isContains) {
                    mSelected.add((Integer) i);//设置默认选中的项
                }
            }
        }
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

    /**
     * @return 返回已经多选中的列表
     */
    public List<Integer> getSelected() {
        return mSelected;
    }

    @Override
    public void onCheckedChanged(View view, boolean isChecked, int position) {
        List<TieBean> datas = getDatas();
        //多选列表
        if (mBuildBean.mChooseType == DialogConfig.MULTI_CHOOSE) {
            boolean isContains = mSelected.contains(position);
            Log.i(TAG, "onCheckedChanged:: multi choose ,isChecked:" + isChecked + ",position:" + position + ",isContains:" + isContains);
            if (isContains) {
                mSelected.remove((Integer) position);
            } else {
                if (mSelected.size() < datas.size()) {
                    if (isChecked) {
                        mSelected.add((Integer) position);
                    }
                }
            }
            TieBean tieBean = datas.get(position);
            tieBean.setSelect(isChecked);
            //setDatas(datas);
            for (int i = 0; i < datas.size(); i++) {
                TieBean bean = datas.get(i);
                Log.w(TAG, "onCheckedChanged: " + i + "," + bean);
            }
            Collections.sort(mSelected);
            for (int i = 0; i < mSelected.size(); i++) {
                Log.d(TAG, "onCheckedChanged:: alery selected " + mSelected.get(i));
            }
        } else if (mBuildBean.mChooseType == DialogConfig.SINGLE_CHOOSE) {
            Log.i(TAG, "onCheckedChanged:: single choose ,isChecked:" + isChecked + ",position:" + position + ",selected size:" + mSelected.size());
            //把已经选中的置未选中
            if (mSelected.size() > 0) {
                mSelected.clear();
            }
            if (isChecked) {
                mSelected.add((Integer) position);
            }
            //单选列表,全部重置未选中
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setSelect(false);
            }
            TieBean tieBean = datas.get(position);
            tieBean.setSelect(isChecked);
            for (int i = 0; i < datas.size(); i++) {
                TieBean bean = datas.get(i);
                Log.w(TAG, "onCheckedChanged: " + i + "," + bean);
            }
        }
        notifyDataSetChanged();
    }
}
