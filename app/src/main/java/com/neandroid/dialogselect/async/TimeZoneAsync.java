package com.neandroid.dialogselect.async;


import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.timezonepicker.TimeZoneData;
import com.android.timezonepicker.TimeZoneInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 异步加载时区数据
 */
public class TimeZoneAsync extends Thread {
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Handler mHandler;
    private TimeZoneDataCallback mTimeZoneDataCallback;
    public static final int TYPE_QUERY = 1;//查询时区
    public static final int TYPE_FILTER_SEARCH = 2;//关键字查询
    private int mType;
    private ArrayList<TimeZoneInfo> mTimeZoneInfos;//原始数据集合
    private String mKeyword;//关键字

    public TimeZoneAsync(Context context, Handler handler, int type, String keyword, ArrayList<TimeZoneInfo> timeZoneInfos, TimeZoneDataCallback callback) {
        this.mContext = context;
        this.mHandler = handler;
        this.mType = type;
        this.mKeyword = keyword;
        this.mTimeZoneInfos = timeZoneInfos;
        this.mTimeZoneDataCallback = callback;
    }

    public TimeZoneAsync(Context context, Handler handler, int type, TimeZoneDataCallback callback) {
        this.mContext = context;
        this.mHandler = handler;
        this.mType = type;
        this.mTimeZoneDataCallback = callback;
    }

    @Override
    public void run() {
        switch (mType) {
            case TYPE_QUERY:
                loadTimeZoneData();
                break;
            case TYPE_FILTER_SEARCH:
                filterSearch();
                break;
        }
    }

    /**
     * 加载时区数据
     */
    private void loadTimeZoneData() {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneID = timeZone.getID();//当前默认时区
        Log.d(TAG, "TimeZoneAsync run:: timeZoneID:" + timeZoneID + "," + Thread.currentThread().getName());
        TimeZoneData tzd = new TimeZoneData(mContext, timeZoneID, timeInMillis);
        ArrayList<TimeZoneInfo> timeZoneInfos = filter(tzd);
        if (mHandler != null) {
            mHandler.post(new HandlerTask(timeZoneInfos));
        }
    }

    /**
     * 条件过滤查询
     */
    private void filterSearch() {
        ArrayList<TimeZoneInfo> searchTimeZoneInfos = new ArrayList<>();
        if (mTimeZoneInfos != null) {
            int size = mTimeZoneInfos.size();
            if (size > 0) {
                Log.d(TAG, "filter:: size:" + size + "," + Thread.currentThread().getName());
                for (int i = 0; i < size; i++) {
                    TimeZoneInfo timeZoneInfo = mTimeZoneInfos.get(i);
                    String timeZoneId = timeZoneInfo.mTzId;
                    String country = timeZoneInfo.mCountry;
                    String displayName = timeZoneInfo.mDisplayName;
                    CharSequence gmtDisplayName = timeZoneInfo.getGmtDisplayName(mContext);
                    Log.d(TAG, "filter:: " + i + ",timeZoneId:" + timeZoneId + ",country:" + country + ",gmtDisplayName:" + gmtDisplayName + ",displayName:" + displayName);
                    if (country.contains(mKeyword)) {
                        searchTimeZoneInfos.add(timeZoneInfo);
                    }
                }
            }
        }

        if (mHandler != null) {
            mHandler.post(new HandlerTask(searchTimeZoneInfos));
        }
    }

    /**
     * 过滤时区
     */
    private ArrayList<TimeZoneInfo> filter(TimeZoneData tzd) {
        ArrayList<TimeZoneInfo> timeZoneInfos = new ArrayList<>();
        int size = tzd.size();
        Log.d(TAG, "filter:: size:" + size + "," + Thread.currentThread().getName());
        for (int i = 0; i < size; i++) {
            TimeZoneInfo timeZoneInfo = tzd.get(i);
            String timeZoneId = timeZoneInfo.mTzId;
            String country = timeZoneInfo.mCountry;
            String displayName = timeZoneInfo.mDisplayName;
            CharSequence gmtDisplayName = timeZoneInfo.getGmtDisplayName(mContext);
            Log.d(TAG, "filter:: " + i + ",timeZoneId:" + timeZoneId + ",country:" + country + ",gmtDisplayName:" + gmtDisplayName + ",displayName:" + displayName);
            if ((!TextUtils.isEmpty(timeZoneId) && timeZoneId.startsWith("Etc")) || TextUtils.isEmpty(country)) {
                continue;
            }
            timeZoneInfos.add(timeZoneInfo);
        }
        return timeZoneInfos;
    }

    /**
     * 时区数据回调
     */
    public interface TimeZoneDataCallback {
        void onTimeZone(ArrayList<TimeZoneInfo> timeZoneInfos);
    }

    /**
     * 时区数据回调
     */
    public interface TimeZoneDataFilterCallback {
        void onFilterTimeZone(ArrayList<TimeZoneInfo> timeZoneInfos);
    }

    /**
     * 线程切换
     */
    private class HandlerTask implements Runnable {

        private ArrayList<TimeZoneInfo> mTimeZoneInfos;

        public HandlerTask(ArrayList<TimeZoneInfo> timeZoneInfos) {
            this.mTimeZoneInfos = timeZoneInfos;
        }

        @Override
        public void run() {
            if (mTimeZoneDataCallback != null) {
                mTimeZoneDataCallback.onTimeZone(mTimeZoneInfos);
            }
        }
    }
}
