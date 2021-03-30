package com.example.qiaoxi.datasource.delegate;

import com.example.qiaoxi.datasource.model.MsgModel;

import java.util.List;

public interface MessageDataDelegate {
    //规范对MsgModel数据的一些行为，比如存储取DB等

    void write2DB(MsgModel msgModel);

    List<MsgModel> readMsgFromDB(String current , String contact, int size);

    List<MsgModel> findMsgFromDB(String send, String receive);
}
