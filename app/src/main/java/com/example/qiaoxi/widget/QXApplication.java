package com.example.qiaoxi.widget;

import android.app.Application;
import android.content.Context;

import com.example.qiaoxi.data.repository.DataRepository;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

public class QXApplication extends Application {
    private  static Context mContext;
    public static String currentUser;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
        options.setAutoTransferMessageAttachments(true);
        options.setAutoDownloadThumbnail(true);
        options.setAutoLogin(false);
        //注：如果你的 APP 中有第三方的服务启动，请在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）方法的前面添加以下相关代码
        EMClient.getInstance().init(mContext,options);
        EMClient.getInstance().setDebugMode(false);
        DataRepository repository = DataRepository.getInstance();

    }
    public static Context getContext(){
        return mContext;
    }
}
