package com.example.qiaoxi.data.datasource.message;

import android.util.Log;

import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.data.model.MsgModel;
import com.example.qiaoxi.data.repository.DataRepository;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;
import java.util.function.Consumer;

public class MessageListenDataSource {
    private DataRepository repository;
    public MessageListenDataSource(DataRepository repository) {
        this.repository = repository;
        setupMessageListen();
    }

    private void setupMessageListen(){
        Log.e("qiaoxi","setupMessageListen");
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {//not mainThread
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                list.forEach(emMessage -> {
                    MsgModel msgModel = new MsgModel(emMessage);
                    repository.processNewMessage(msgModel);
                    repository.processNewConversation(new ConversationModel(msgModel.receive,msgModel.send,msgModel));
                });
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }
}
