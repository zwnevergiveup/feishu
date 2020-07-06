package com.example.qiaoxi.datasource.message;

import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;

import java.util.List;

public interface MessageDataDelegate {
    //规范对MsgModel数据的一些行为，比如存储取DB等

    public void write2DB(MsgModel msgModel);
    public void write2DB(UserModel userModel);
    public void write2DB(ConversationModel conversationModel);


    public List<MsgModel> readFromDB(String compact);
}
