package com.example.qiaoxi.datasource.delegate;

import com.example.qiaoxi.datasource.model.ConversationModel;

import java.util.List;

public interface ConversationModelDelegate {

    void write2DB(ConversationModel conversationModel);

    List<ConversationModel> readConversationsFromDB(String current);
}
