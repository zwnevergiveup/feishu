package com.example.qiaoxi.datasource;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class MessageListenDataSource {
    private DataRepository repository;
    public MessageListenDataSource(DataRepository repository) {
        this.repository = repository;
        setupMessageListen();
    }

    private void setupMessageListen(){
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {//not mainThread
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                list.forEach(emMessage -> {
                    if (emMessage.getBody() instanceof EMTextMessageBody) {
                        MsgModel msgModel = new MsgModel(emMessage);
                        repository.processNewMessage(msgModel);
                        repository.processNewConversation(new ConversationModel(msgModel.send,msgModel.receive,msgModel));
                    }
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
