package com.neandroid.dialogselect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.neandroid.dialogui.DialogUIUtils;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.listener.DialogUIItemListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 单选默认被选中的条目
     */
    private int mSingleSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 单选对话框
     *
     * @param view
     */
    public void singlechoice(View view) {
        List<TieBean> strings = new ArrayList<TieBean>();
        for (int i = 0; i < 11; i++) {
            strings.add(new TieBean(i, i + "条目", (i == mSingleSelected) ? true : false));
        }
        DialogUIUtils.showChooseSheet(this, DialogConfig.SINGLE_CHOOSE, strings, "单选列表", "取消", Gravity.CENTER, true, true, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position, boolean isChecked) {
                Log.d(TAG, "onItemClick: mSingleSelected:" + mSingleSelected + ",position:" + position + "," + isChecked + "," + text);
                if (isChecked) {
                    mSingleSelected = position;
                } else {
                    if (mSingleSelected == position) {
                        mSingleSelected = 0;
                    }
                }
                Toast.makeText(MainActivity.this, position + "," + isChecked + "," + text, Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    /**
     * 多选对话框
     *
     * @param view
     */
    public void multichoice(View view) {
        List<TieBean> strings = new ArrayList<TieBean>();
        for (int i = 0; i < 11; i++) {
            strings.add(new TieBean(i, i + "条目", (i % 2 == 0) ? true : false));
        }
        DialogUIUtils.showChooseSheet(this, DialogConfig.MULTI_CHOOSE, strings, "多选列表", "确定", Gravity.CENTER, true, true, new DialogUIItemListener() {

            @Override
            public void onBottomBtnMultiChooseClick(List<Integer> multiSelected) {
                Log.d(TAG, "onBottomBtnMultiChooseClick:: multiChecked:" + multiSelected);
                if (multiSelected != null) {
                    int size = multiSelected.size();
                    Log.d(TAG, "onBottomBtnMultiChooseClick:: multiChecked size:" + size);
                    for (int i = 0; i < size; i++) {
                        Log.d(TAG, "onBottomBtnMultiChooseClick:: " + multiSelected.get(i) + " selected ... ");
                    }
                }
            }

            @Override
            public void onItemClick(CharSequence text, int position, boolean isChecked) {
                Log.d(TAG, "onItemClick:: " + position + "," + isChecked + "," + text);
                Toast.makeText(MainActivity.this, position + "," + isChecked + "," + text, Toast.LENGTH_SHORT).show();
            }
        }).show();

    }
}
