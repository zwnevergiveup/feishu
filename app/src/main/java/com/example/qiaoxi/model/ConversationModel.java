package com.example.qiaoxi.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "conversations")
public class ConversationModel {
    @PrimaryKey
    @NonNull
    public String conversationKey;

    @ColumnInfo(name = "current_name")
    public String currentName;

    @ColumnInfo(name = "compact_man")
    public String compactMan;

    @ColumnInfo(name = "last_message")
    public String lastMessage;

    public ConversationModel(String currentName, String compactMan, String lastMessage) {
        this.currentName = currentName;
        this.compactMan = compactMan;
        this.lastMessage = lastMessage;
        this.conversationKey = currentName+compactMan;
    }
}
