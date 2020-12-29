package com.neandroid.dialogui.adapter;

import android.content.Context;
import android.view.View;

import com.neandroid.dialogui.R;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.holder.ItemHolder;
import com.neandroid.dialogui.holder.SuperItemHolder;

import java.util.List;

/**
 * 描 述：列表选择对话框适配器
 */
public class SheetAdapter extends SuperAdapter<TieBean>{

    /**
     * 对话框实体
     */
    private BuildBean mBuildBean;

    private boolean isItemType;

    public SheetAdapter(Context mContext, List<TieBean> list) {
        super(mContext, list);
    }

    public SheetAdapter(Context mContext, BuildBean bean, boolean isItemType) {
        super(mContext, bean.mLists);
        this.mBuildBean = bean;
        this.isItemType = isItemType;
    }

    /**
     * @param view 已经加载好的Holder条目
     * @return 加载条目 Holder
     */
    @Override
    public SuperItemHolder getItemHolder(View view) {
        return new ItemHolder(mContext, mItemClickListener, view);
    }

    /**
     * @return 返回条目layout资源
     */
    @Override
    public int getLayoutRes() {
        return R.layout.dialogui_holder_item;
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
}
