package com.neandroid.dialogselect;

import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.timezonepicker.TimeZoneData;
import com.android.timezonepicker.TimeZoneInfo;
import com.android.timezonepicker.TimeZonePickerDialog;
import com.neandroid.dialogselect.async.TimeZoneAsync;
import com.neandroid.dialogui.DialogUIUtils;
import com.neandroid.dialogui.bean.BuildBean;
import com.neandroid.dialogui.bean.TieBean;
import com.neandroid.dialogui.config.DialogConfig;
import com.neandroid.dialogui.listener.DialogUIItemListener;
import com.neandroid.dialogui.listener.DialogUIListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final String FRAG_TAG_TIME_ZONE_PICKER = "timeZonePickerDialogFragment";
    private ArrayList<TimeZoneInfo> mTimeZoneInfos;//原始时区列表
    private ArrayList<TimeZoneInfo> mSearchTimeZoneInfos;//查询到时区列表
    private boolean isSearch;//是否搜索
    private TimeZoneInfo mSelectTimeZoneInfo;//当前选中的时区

    private Handler mHandler;

    /**
     * 单选默认被选中的条目
     */
    private int mSingleSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(Looper.getMainLooper());
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

    private BuildBean buildBean;

    /**
     * 搜索对话框
     *
     * @param view
     */
    public void searchchoose(View view) {
        List<TieBean> strings = new ArrayList<TieBean>();
        for (int i = 0; i < 11; i++) {
            strings.add(new TieBean(i, "条目" + i, (i % 2 == 0) ? false : true));
        }
        buildBean = DialogUIUtils.showSearchSheet(this, strings, "搜索标题", null, "请输入姓名", null, "确认", "取消", false, true, true,
                new DialogUIListener() {
                    @Override
                    public void onPositive() {
                        Log.d(TAG, "onPositive:: ");
                    }

                    @Override
                    public void onNegative() {
                        Log.d(TAG, "onNegative:: ");
                    }

                    @Override
                    public void onGetInput(CharSequence inputText) {
                        Log.d(TAG, "onGetInput:: inputText:" + inputText);
                        Toast.makeText(MainActivity.this, "inputText:" + inputText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRealtimeInput(CharSequence inputText) {
                        Log.d(TAG, "onRealtimeInput:: " + inputText);
                        //实时搜索,更新列表数据集
                        List<TieBean> strings = new ArrayList<TieBean>();
                        for (int i = 0; i < 110; i++) {
                            if (!TextUtils.isEmpty(inputText)) {
                                strings.add(new TieBean(i, "条目" + i + "刷新", (i % 2 == 0) ? true : false));
                            } else {
                                strings.add(new TieBean(i, "条目" + i, (i % 2 == 0) ? false : true));
                            }
                        }
                        buildBean.refresh(strings);
                    }
                },
                new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position, boolean isChecked) {
                        Log.d(TAG, "onItemClick:: " + position + "," + isChecked + "," + text);
                        Toast.makeText(MainActivity.this, position + "," + isChecked + "," + text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(CharSequence text, int position, boolean isChecked) {
                        Log.d(TAG, "onItemLongClick:: " + position + "," + isChecked + "," + text);
                        Toast.makeText(MainActivity.this, "长按" + position + "," + text, Toast.LENGTH_SHORT).show();
                    }
                });
        buildBean.show();
    }


    /**
     * 时区搜索
     *
     * @param view
     */
    public void searchtimezone(View view) {
        showTimezoneDialog();
    }

    /**
     * 选择时区
     */
    private void showTimezoneDialog() {
        Calendar calendar = Calendar.getInstance();
        long startTimeInMillis = calendar.getTimeInMillis();
        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneID = timeZone.getID();
        Log.d(TAG, "showTimezoneDialog:: timeZoneID:" + timeZoneID);

        Bundle b = new Bundle();
        b.putLong(TimeZonePickerDialog.BUNDLE_START_TIME_MILLIS, startTimeInMillis);
        b.putString(TimeZonePickerDialog.BUNDLE_TIME_ZONE, timeZoneID);

        FragmentManager fm = this.getFragmentManager();
        TimeZonePickerDialog tzpd = (TimeZonePickerDialog) fm.findFragmentByTag(FRAG_TAG_TIME_ZONE_PICKER);
        if (tzpd != null) {
            tzpd.dismiss();
        }
        tzpd = new TimeZonePickerDialog();
        tzpd.setArguments(b);
        tzpd.setOnTimeZoneSetListener(new TimeZonePickerDialog.OnTimeZoneSetListener() {
            @Override
            public void onTimeZoneSet(TimeZoneInfo tzi) {
                Log.d(TAG, "onTimeZoneSet: " + tzi);
            }
        });
        tzpd.show(fm, FRAG_TAG_TIME_ZONE_PICKER);
    }


    /**
     * 时区选择
     *
     * @param view
     */
    public void timezonechoose(View view) {
        mSelectTimeZoneInfo = null;
        TimeZoneAsync asyncTimeZone = new TimeZoneAsync(this, mHandler, TimeZoneAsync.TYPE_QUERY, new TimeZoneAsync.TimeZoneDataCallback() {
            @Override
            public void onTimeZone(ArrayList<TimeZoneInfo> timeZoneInfos) {
                mTimeZoneInfos = timeZoneInfos;
                showTimeZoneDialog();
            }
        });
        asyncTimeZone.start();
    }

    /**
     * 时区选择对话框
     */
    public void showTimeZoneDialog() {
        if (mTimeZoneInfos == null) {
            return;
        }
        isSearch = false;
        int size = mTimeZoneInfos.size();
        List<TieBean> strings = new ArrayList<TieBean>();
        for (int i = 0; i < size; i++) {
            TimeZoneInfo timeZoneInfo = mTimeZoneInfos.get(i);
            String timeZoneId = timeZoneInfo.mTzId;
            String country = timeZoneInfo.mCountry;
            String displayName = timeZoneInfo.mDisplayName;
            CharSequence gmtDisplayName = timeZoneInfo.getGmtDisplayName(MainActivity.this);
            strings.add(new TieBean(i, country + "(" + gmtDisplayName + "," + displayName + ")", (i % 2 == 0) ? false : true));
        }
        buildBean = DialogUIUtils.showSearchSheet(this, strings, "搜索标题", null, "请输入姓名", null, "确认", "取消", false, true, true,
                new DialogUIListener() {
                    @Override
                    public void onPositive() {
                        Log.d(TAG, "onPositive:: ");
                        isSearch = false;
                    }

                    @Override
                    public void onNegative() {
                        Log.d(TAG, "onNegative:: ");
                        isSearch = false;
                    }

                    @Override
                    public void onGetInput(CharSequence inputText) {
                        Log.d(TAG, "onGetInput:: inputText:" + inputText);
                        Toast.makeText(MainActivity.this, "inputText:" + inputText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRealtimeInput(CharSequence inputText) {
                        Log.d(TAG, "onRealtimeInput:: " + inputText);
                        //实时搜索,更新列表数据集
                        filterSearch(inputText.toString());
                    }
                },
                new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position, boolean isChecked) {
                        Log.d(TAG, "onItemClick:: " + position + "," + isChecked + "," + text + ",isSearch:" + isSearch);
                        Toast.makeText(MainActivity.this, position + "," + isChecked + "," + text + "," + isSearch, Toast.LENGTH_SHORT).show();
                        if (isSearch) {
                            if (mSearchTimeZoneInfos != null && mSearchTimeZoneInfos.size() > 0) {
                                mSelectTimeZoneInfo = mSearchTimeZoneInfos.get(position);
                            }
                        } else {
                            if (mTimeZoneInfos != null && mTimeZoneInfos.size() > 0) {
                                mSelectTimeZoneInfo = mTimeZoneInfos.get(position);
                            }
                        }
                        isSearch = false;
                        if (mSelectTimeZoneInfo != null) {
                            String timeZoneId = mSelectTimeZoneInfo.mTzId;
                            String country = mSelectTimeZoneInfo.mCountry;
                            CharSequence gmtDisplayName = mSelectTimeZoneInfo.getGmtDisplayName(MainActivity.this);
                            String displayName = mSelectTimeZoneInfo.mDisplayName;
                            Log.d(TAG, "onItemClick:: timeZoneId:" + timeZoneId + ",country:" + country + ",gmtDisplayName:" + gmtDisplayName + ",displayName:" + displayName);
                        }
                    }

                    @Override
                    public void onItemLongClick(CharSequence text, int position, boolean isChecked) {
                        Log.d(TAG, "onItemLongClick:: " + position + "," + isChecked + "," + text + ",isSearch:" + isSearch);
                        Toast.makeText(MainActivity.this, "长按" + position + "," + text + "," + isSearch, Toast.LENGTH_SHORT).show();
                        if (isSearch) {
                            if (mSearchTimeZoneInfos != null && mSearchTimeZoneInfos.size() > 0) {
                                mSelectTimeZoneInfo = mSearchTimeZoneInfos.get(position);
                            }
                        } else {
                            if (mTimeZoneInfos != null && mTimeZoneInfos.size() > 0) {
                                mSelectTimeZoneInfo = mTimeZoneInfos.get(position);
                            }
                        }
                        isSearch = false;
                        if (mSelectTimeZoneInfo != null) {
                            String timeZoneId = mSelectTimeZoneInfo.mTzId;
                            String country = mSelectTimeZoneInfo.mCountry;
                            CharSequence gmtDisplayName = mSelectTimeZoneInfo.getGmtDisplayName(MainActivity.this);
                            String displayName = mSelectTimeZoneInfo.mDisplayName;
                            Log.d(TAG, "onItemLongClick:: timeZoneId:" + timeZoneId + ",country:" + country + ",gmtDisplayName:" + gmtDisplayName + ",displayName:" + displayName);
                        }
                    }
                });
        buildBean.show();
    }

    /**
     * 过滤搜索查询
     */
    private void filterSearch(String keyword) {
        TimeZoneAsync asyncTimeZone = new TimeZoneAsync(this, mHandler, TimeZoneAsync.TYPE_FILTER_SEARCH, keyword, mTimeZoneInfos, new TimeZoneAsync.TimeZoneDataCallback() {
            @Override
            public void onTimeZone(ArrayList<TimeZoneInfo> timeZoneInfos) {
                if (timeZoneInfos != null && timeZoneInfos.size() > 0) {
                    isSearch = true;
                }
                mSearchTimeZoneInfos = timeZoneInfos;
                refresh();
            }
        });
        asyncTimeZone.start();
    }

    /**
     * 刷新列表
     */
    private void refresh() {
        if (mSearchTimeZoneInfos == null) {
            return;
        }
        int size = mSearchTimeZoneInfos.size();
        if (size != 0) {
            List<TieBean> strings = new ArrayList<TieBean>();
            for (int i = 0; i < size; i++) {
                TimeZoneInfo timeZoneInfo = mSearchTimeZoneInfos.get(i);
                String timeZoneId = timeZoneInfo.mTzId;
                String country = timeZoneInfo.mCountry;
                String displayName = timeZoneInfo.mDisplayName;
                CharSequence gmtDisplayName = timeZoneInfo.getGmtDisplayName(MainActivity.this);
                strings.add(new TieBean(i, country + "(" + gmtDisplayName + "," + displayName + ")", (i % 2 == 0) ? false : true));
            }
            buildBean.refresh(strings);
        }
    }
}
