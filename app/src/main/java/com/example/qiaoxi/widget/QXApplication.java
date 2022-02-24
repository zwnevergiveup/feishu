package com.example.qiaoxi.widget;

import android.app.Application;
import android.content.Context;

import com.example.qiaoxi.datasource.datasource.DataSourceHelper;


public class QXApplication extends Application {
    private  static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

//        DataSourceHelper.getInstance().initDataSourceHelper(mContext); //init dataSource module
    }
    public static Context getContext(){
        return mContext;
    }
}
