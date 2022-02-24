package com.example.qiaoxi.datasource.datasource;

import android.content.Context;

import com.example.qiaoxi.datasource.CallBacker;
import com.example.qiaoxi.datasource.LambdaArg;
import com.example.qiaoxi.datasource.model.ConversationModel;
import com.example.qiaoxi.datasource.model.MsgModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;

import java.util.List;

public class DataSourceHelper {
    private Context mContext = null;
    private DataRepository repository = null;

    private static volatile  DataSourceHelper mInstance = null;

    private DataSourceHelper() { }

}
