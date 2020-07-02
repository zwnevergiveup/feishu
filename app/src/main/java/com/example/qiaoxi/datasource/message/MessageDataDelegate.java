package com.example.qiaoxi.datasource.message;

import com.example.qiaoxi.model.MsgModel;

import java.util.List;

public interface MessageDataDelegate {
    //规范对MsgModel数据的一些行为，比如存储取DB等

    public void write2DB(MsgModel msgModel);

    public List<MsgModel> readFromDB(String compact);
}
