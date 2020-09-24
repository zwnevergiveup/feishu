package com.example.qiaoxi.datasource;

import android.content.Context;
import android.util.Log;

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

        EMPushHelper.getInstance().setPushListener(new PushListener() {
            @Override
            public void onError(EMPushType emPushType, long l) {

            }

            @Override
            public boolean isSupportPush(EMPushType emPushType, EMPushConfig emPushConfig) {
                return super.isSupportPush(emPushType, emPushConfig);
            }
        });

        repository = DataRepository.getInstance();
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
        MsgModel model = new MsgModel(message);

        repository.processNewMessage(model);
        repository.processNewConversation(new ConversationModel(model.send,model.receive,model));

        lambda.noArg(model);
    }

    public void loginEM(String name, String password, CallBacker callBacker) {
        EMClient.getInstance().login(name, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                callBacker.onSuccess("登录成功");
            }

            @Override
            public void onError(int i, String s) {
                callBacker.onFail(s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    public void logoutEM(CallBacker callBacker){
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                callBacker.onSuccess("logout success");
            }

            @Override
            public void onError(int i, String s) {
                callBacker.onFail("logout fail: "+ s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public String getCurrentUserName() {
        return EMClient.getInstance().getCurrentUser();
    }

    public boolean isConnectedEM() {
        return EMClient.getInstance().isConnected();
    }


    public void registerMessageListen(ListenRepositoryData<MsgModel> listener) {
        repository.registerMsgListener(listener);
    }

    public void unregisterMessageListen(ListenRepositoryData<MsgModel> listener) {
        repository.unregisterMsgListener(listener);
    }

    public void registerConversationListen(ListenRepositoryData<ConversationModel> listener) {
        repository.unregisterConversationListener(listener);
    }

    public void unregisterConversationListen(ListenRepositoryData<ConversationModel> listener) {
        repository.unregisterConversationListener(listener);
    }

    public List<MsgModel> readMsgHistory(String user, String contact, int size) {
        return repository.readMsgFromDB(user,contact,size);
    }

    public List<ConversationModel> readConversationsList(String user) {
        return repository.readConversationsFromDB(user);
    }

}
