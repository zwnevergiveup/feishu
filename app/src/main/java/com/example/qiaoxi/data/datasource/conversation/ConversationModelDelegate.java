package com.example.qiaoxi.data.datasource.conversation;

import com.example.qiaoxi.data.model.ConversationModel;

import java.util.List;

public interface ConversationModelDelegate {

    void write2DB(ConversationModel conversationModel);

    List<ConversationModel> readConversationsFromDB(String current);
}
