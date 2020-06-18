package com.example.qiaoxi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public abstract class BaseService extends Service {
    protected static final String TAG = "qiaoxi";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
