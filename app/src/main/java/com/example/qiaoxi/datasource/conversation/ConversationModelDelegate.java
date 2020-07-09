package com.example.qiaoxi.datasource.conversation;

import com.example.qiaoxi.model.ConversationModel;

import java.util.List;

public interface ConversationModelDelegate {

    void write2DB(ConversationModel conversationModel);

    List<ConversationModel> readConversationsFromDB(String current);
}
