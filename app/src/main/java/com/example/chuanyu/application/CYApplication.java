package com.example.chuanyu.application;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

public class CYApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
        options.setAutoTransferMessageAttachments(true);
        options.setAutoDownloadThumbnail(true);
        //注：如果你的 APP 中有第三方的服务启动，请在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）方法的前面添加以下相关代码
        EMClient.getInstance().init(mContext,options);
        EMClient.getInstance().setDebugMode(true);
    }

    public static Context getInstance() {
        return mContext;
    }
}
