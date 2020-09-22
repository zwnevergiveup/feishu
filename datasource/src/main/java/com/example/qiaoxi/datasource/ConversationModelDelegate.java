package com.example.qiaoxi.datasource;

import com.example.qiaoxi.datasource.ConversationModel;

import java.util.List;

public interface ConversationModelDelegate {

    void write2DB(ConversationModel conversationModel);

    List<ConversationModel> readConversationsFromDB(String current);
}
