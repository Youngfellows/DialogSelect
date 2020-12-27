package com.neandroid.dialogselect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.neandroid.dialogui.DialogUIUtils;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.listener.DialogUIItemListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        for (int i = 1; i < 401; i++) {
            strings.add(new TieBean(i, i + "条目", false));
        }
        DialogUIUtils.showSingleChooseSheet(this, strings, "列表标题", "取消", Gravity.CENTER, true, true, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    /**
     * 多选对话框
     *
     * @param view
     */
    public void multichoice(View view) {

    }
}
