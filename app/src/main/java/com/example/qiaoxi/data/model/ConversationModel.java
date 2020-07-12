package com.example.qiaoxi.data.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "conversations")
@TypeConverters(MsgConvertor.class)
public class ConversationModel {
    @PrimaryKey
    @NonNull
    public String conversationKey;

    @ColumnInfo(name = "current_name")
    public String currentName;

    @ColumnInfo(name = "compact_man")
    public String compactMan;

    @ColumnInfo(name = "last_message")
    public MsgModel lastMessage;

    public ConversationModel(String currentName, String compactMan, MsgModel lastMessage) {
        this.currentName = currentName;
        this.compactMan = compactMan;
        this.lastMessage = lastMessage;
        this.conversationKey = currentName+compactMan;
    }
}
