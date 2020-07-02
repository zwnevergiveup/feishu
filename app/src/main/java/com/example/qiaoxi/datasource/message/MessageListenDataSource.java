package com.example.qiaoxi.datasource.message;

import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.repository.DataRepository;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class MessageListenDataSource {
    private DataRepository repository;
    public MessageListenDataSource() {
        repository = DataRepository.getInstance();
        setupMessageListen();
    }

    private void setupMessageListen(){
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {//not mainThread
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                EMMessage newMessage = list.get(0);
                MsgModel msgModel = new MsgModel(newMessage);
                repository.processNewMessage(msgModel);
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
