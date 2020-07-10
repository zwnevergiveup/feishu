package com.example.qiaoxi.service;

import android.content.Context;
import android.os.Vibrator;

public class ForegroundService extends BaseService  {
    public static final int NOTICE_ID = 100;
    private Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

}
