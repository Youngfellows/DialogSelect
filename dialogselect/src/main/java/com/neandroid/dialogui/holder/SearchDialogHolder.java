package com.neandroid.dialogui.holder;


import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neandroid.dialogui.DialogUIUtils;
import com.neandroid.dialogui.R;
import com.neandroid.dialogui.adapter.SheetAdapter;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.listener.OnItemClickListener;
import com.neandroid.dialogui.utils.ToolUtils;
import com.neandroid.dialogui.widget.DialogUIDividerItemDecoration;
import com.neandroid.dialogui.widget.MaxLimitRecyclerView;

public class SearchDialogHolder extends SuperHolder {
    protected TextView tvTitle;
    public TextView tvMsg;
    public EditText et1;
    protected View line;
    protected Button btn1;
    protected View lineBtn2;
    protected Button btn2;
    protected View lineBtn3;
    protected Button btn3;
    protected LinearLayout llContainerHorizontal;
    protected MaxLimitRecyclerView rView;
    private boolean isItemType;


    public SearchDialogHolder(Context context, boolean isItemType) {
        super(context);
        this.isItemType = isItemType;
    }

    @Override
    protected void findViews() {
        tvTitle = (TextView) rootView.findViewById(R.id.dialogui_tv_title);
        tvMsg = (TextView) rootView.findViewById(R.id.dialogui_tv_msg);
        et1 = (EditText) rootView.findViewById(R.id.et_1);
        line = (View) rootView.findViewById(R.id.line);
        btn1 = (Button) rootView.findViewById(R.id.btn_1);
        lineBtn2 = (View) rootView.findViewById(R.id.line_btn2);
        btn2 = (Button) rootView.findViewById(R.id.btn_2);
        lineBtn3 = (View) rootView.findViewById(R.id.line_btn3);
        btn3 = (Button) rootView.findViewById(R.id.btn_3);
        llContainerHorizontal = (LinearLayout) rootView.findViewById(R.id.ll_container_horizontal);
        rView = (MaxLimitRecyclerView) rootView.findViewById(R.id.rlv);
    }


    @Override
    protected int setLayoutRes() {
        return R.layout.dialogui_holder_search_sheet;
    }

    /**
     * 分配数据和事件
     *
     * @param context 上下文
     * @param bean    弹出实体
     */
    @Override
    public void assingDatasAndEvents(Context context, final BuildBean bean) {
        //style
        tvMsg.setTextColor(ToolUtils.getColor(tvMsg.getContext(), bean.msgTxtColor));
        tvMsg.setTextSize(bean.msgTxtSize);

        tvTitle.setTextColor(ToolUtils.getColor(tvTitle.getContext(), bean.titleTxtColor));
        tvTitle.setTextSize(bean.titleTxtSize);

        btn3.setTextSize(bean.btnTxtSize);
        btn2.setTextSize(bean.btnTxtSize);
        btn1.setTextSize(bean.btnTxtSize);

        btn1.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn1Color));
        btn2.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn2Color));
        btn3.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn3Color));

        //隐藏view
        if (bean.isVertical) {
            llContainerHorizontal.setVisibility(View.GONE);
        } else {
            llContainerHorizontal.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(bean.title);
        }

        if (TextUtils.isEmpty(bean.msg)) {
            tvMsg.setVisibility(View.GONE);
        } else {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(bean.msg);

            tvMsg.setTextColor(ToolUtils.getColor(tvMsg.getContext(), bean.msgTxtColor));
            tvMsg.setTextSize(bean.msgTxtSize);
        }

        if (TextUtils.isEmpty(bean.hint1)) {
            et1.setVisibility(View.GONE);
        } else {
            et1.setVisibility(View.VISIBLE);
            et1.setHint(bean.hint1);

            et1.setTextColor(ToolUtils.getColor(et1.getContext(), bean.inputTxtColor));
            et1.setTextSize(bean.inputTxtSize);
        }

        //按钮数量
        if (TextUtils.isEmpty(bean.text3)) {
            btn3.setVisibility(View.GONE);
            lineBtn3.setVisibility(View.GONE);
            btn2.setBackgroundResource(R.drawable.dialogui_selector_right_bottom);
        } else {
            btn3.setVisibility(View.VISIBLE);
            lineBtn3.setVisibility(View.VISIBLE);
            btn3.setText(bean.text3);
        }

        if (TextUtils.isEmpty(bean.text2)) {
            btn2.setVisibility(View.GONE);
            lineBtn2.setVisibility(View.GONE);
            btn1.setBackgroundResource(R.drawable.dialogui_selector_right_bottom);
        } else {
            btn2.setVisibility(View.VISIBLE);
            lineBtn2.setVisibility(View.VISIBLE);
            btn2.setText(bean.text2);
        }
        if (TextUtils.isEmpty(bean.text1)) {
            line.setVisibility(View.GONE);
            llContainerHorizontal.setVisibility(View.GONE);
        } else {
            btn1.setText(bean.text1);
        }

        //事件
        if (!bean.isVertical) {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                    bean.listener.onPositive();
                    bean.listener.onGetInput(et1.getText().toString().trim());
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                    bean.listener.onNegative();
                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                    bean.listener.onNeutral();
                }
            });
        }

        //监听EditText实时输入
        et1.addTextChangedListener(new TextChangeWatcher(bean));

        //RecyclerView
        rView.setLayoutManager(new LinearLayoutManager(bean.mContext));
        rView.addItemDecoration(new DialogUIDividerItemDecoration(bean.mContext));// 布局管理器。
        rView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        if (bean.mAdapter == null) {
            SheetAdapter adapter = new SheetAdapter(bean.mContext, bean, isItemType);
            bean.mAdapter = adapter;
        }
        rView.setAdapter(bean.mAdapter);
        bean.mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick:: " + position);
                bean.itemListener.onItemClick(bean.mLists.get(position).getTitle(), position, true);
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }

            @Override
            public void onItemLongClick(int position) {
                Log.d(TAG, "onItemLongClick:: " + position);
                bean.itemListener.onItemLongClick(bean.mLists.get(position).getTitle(), position, true);
                //DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }
        });
    }

    /**
     * 刷新数据集
     *
     * @param bean 对话框实体
     */
    @Override
    public void refreshDatas(BuildBean bean) {
//        if (bean.mAdapter instanceof SheetAdapter) {
//            SheetAdapter adapter = (SheetAdapter) bean.mAdapter;
//            adapter.setDatas();
//        }
        if (bean.mAdapter != null) {
            bean.mAdapter.setDatas(bean.mLists);
            bean.mAdapter.notifyDataSetChanged();
        }
    }

    private class TextChangeWatcher implements TextWatcher {

        private BuildBean buildBean;

        public TextChangeWatcher(BuildBean bean) {
            this.buildBean = bean;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged:: s:" + s + ",start:" + start + ",contu:" + count + ",after:" + after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged:: s:" + s + ",start:" + start + ",before:" + before + ",count:" + count);
            if (buildBean.listener != null) {
                buildBean.listener.onRealtimeInput(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged:: " + s.toString());
        }
    }
}
