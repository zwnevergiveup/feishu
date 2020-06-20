package com.example.qiaoxi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

@Entity
public class MsgModel {
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "send_time")
    public long sendTime;

    @ColumnInfo(name = "send_name")
    public String send;

    @ColumnInfo(name = "receive_name")
    public String receive;

    @ColumnInfo(name = "content")
    public String content;

    public MsgModel(EMMessage msg) {
        id = msg.getMsgId();
        sendTime = msg.getMsgTime();
        send = msg.getFrom();
        receive = msg.getTo();
        content = ((EMTextMessageBody)msg.getBody()).getMessage();
    }
}
