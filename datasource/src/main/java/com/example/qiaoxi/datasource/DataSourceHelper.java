package com.example.qiaoxi.datasource;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.push.EMPushConfig;

public class DataSourceHelper {
    private Context mContext = null;

    private static volatile  DataSourceHelper mInstance = null;

    private DataSourceHelper() { }

    public void initDataSourceHelper(Context context) {
        mContext = context;

        EMPushConfig.Builder builder = new EMPushConfig.Builder(mContext);
        builder.enableMeiZuPush("134533","8c00657df41e466089b84ee9a0603065");

        EMOptions options = new EMOptions();
        options.setPushConfig(builder.build());
        options.setAcceptInvitationAlways(true);
        options.setAutoTransferMessageAttachments(true);
        options.setAutoDownloadThumbnail(true);
        options.setAutoLogin(false);
        //注：如果你的 APP 中有第三方的服务启动，请在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）方法的前面添加以下相关代码
        EMClient.getInstance().init(mContext,options);
        EMClient.getInstance().setDebugMode(false);
    }

    public static DataSourceHelper getInstance() {
        if (mInstance == null) {
            synchronized (DataSourceHelper.class) {
                if (mInstance == null) {
                    mInstance = new DataSourceHelper();
                }
            }
        }
        return mInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public void sendMessage(String content,String toWho, LambdaArg lambda) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toWho);
        EMClient.getInstance().chatManager().sendMessage(message);
        lambda.noArg(message);
    }


}
