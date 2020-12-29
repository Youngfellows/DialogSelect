package com.neandroid.dialogui.holder;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neandroid.dialogui.DialogUIUtils;
import com.neandroid.dialogui.R;
import com.neandroid.dialogui.adapter.SheetChooseAdapter;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.listener.OnItemClickListener;
import com.neandroid.dialogui.widget.DialogUIDividerItemDecoration;

import java.util.List;
import java.util.logging.Handler;

/**
 * 描 述：列表holder
 */
public class SheetHolder extends SuperHolder {

    /**
     * 是否列表类型
     */
    private boolean isItemType;

    /**
     * RV列表
     */
    private RecyclerView rView;

    /**
     * 对话框Title
     */
    private TextView tvTitle;

    /**
     * 底部按钮
     */
    private Button btnBottom;

    public SheetHolder(Context context, boolean isItemType) {
        super(context);
        this.isItemType = isItemType;
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialogui_holder_sheet;
    }

    @Override
    protected void findViews() {
        tvTitle = (TextView) rootView.findViewById(R.id.dialogui_tv_title);
        rView = (RecyclerView) rootView.findViewById(R.id.rlv);
        btnBottom = (Button) rootView.findViewById(R.id.btn_bottom);
    }

    /**
     * 分配数据和事件
     *
     * @param context
     * @param bean
     */
    @Override
    public void assingDatasAndEvents(final Context context, final BuildBean bean) {
        if (TextUtils.isEmpty(bean.bottomTxt)) {
            btnBottom.setVisibility(View.GONE);
        } else {
            btnBottom.setVisibility(View.VISIBLE);
            btnBottom.setText(bean.bottomTxt);
            btnBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick:: btnBottom,chooseType:" + bean.mChooseType);
                    if (bean.mChooseType == DialogConfig.MULTI_CHOOSE) {
                        //多选列表传递选中列表信息
                        if (bean.mAdapter instanceof SheetChooseAdapter) {
                            bean.mMultiSelected = ((SheetChooseAdapter) bean.mAdapter).getSelected();
                            //回调多选列表
                            bean.itemListener.onBottomBtnMultiChooseClick(bean.mMultiSelected);
                            DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                        }
                    } else if (bean.mChooseType == DialogConfig.SINGLE_CHOOSE) {
                        bean.itemListener.onBottomBtnCancelClick();
                        DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                    } else {
                        bean.itemListener.onBottomBtnClick();
                        DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                    }
                }
            });
        }
        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(bean.title);
        }
        if (bean.isVertical) {
            rView.setLayoutManager(new LinearLayoutManager(bean.mContext));
            rView.addItemDecoration(new DialogUIDividerItemDecoration(bean.mContext));// 布局管理器。
        }
        rView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        if (bean.mAdapter == null) {
            SheetChooseAdapter adapter = new SheetChooseAdapter(bean.mContext, bean, isItemType);
            bean.mAdapter = adapter;
        }
        rView.setAdapter(bean.mAdapter);
        bean.mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick:: " + position);
                changeSelectedStatus(bean, position, false);
            }

            @Override
            public void onItemLongClick(int position) {
                Log.d(TAG, "onItemLongClick:: " + position);
                changeSelectedStatus(bean, position, true);
            }
        });
    }

    private void changeSelectedStatus(BuildBean bean, int position, boolean isLongClick) {
        List<Integer> selected = ((SheetChooseAdapter) bean.mAdapter).getSelected();
        if (bean.mChooseType == DialogConfig.SINGLE_CHOOSE) {
            //单选选中条目对话框消失,获取选中列表,回调选中和未选中状态
            Log.d(TAG, "onItemClick:: single, selected size:" + (selected != null ? selected.size() : 0));
            if (selected != null && selected.size() > 0) {
                for (int i = 0; i < selected.size(); i++) {
                    Log.d(TAG, "onItemClick:: click selected " + selected.get(i));
                }
                if (selected.size() == 1) {
                    //选中
                    bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), selected.get(0), true);
                }
            } else {
                //取消选中
                bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), position, false);
            }
            //长按延时1秒消失,否则看不到选中效果
            if (isLongClick) {
                mHandler.postDelayed(new DelayDismissTask(bean), 500);
            } else {
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }
        } else if (bean.mChooseType == DialogConfig.MULTI_CHOOSE) {
            Log.d(TAG, "onItemClick:: multi, selected size:" + (selected != null ? selected.size() : 0));
            if (selected != null) {
                boolean isContains = selected.contains(position);
                if (isContains) {
                    bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), selected.get(selected.indexOf(position)), true);
                } else {
                    bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), position, false);
                }
            } else {
                bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), position, false);
            }
        }
    }

    private class DelayDismissTask implements Runnable {

        private BuildBean buildBean;

        public DelayDismissTask(BuildBean bean) {
            this.buildBean = bean;
        }

        @Override
        public void run() {
            DialogUIUtils.dismiss(buildBean.dialog, buildBean.alertDialog);
        }
    }
}
