package com.example.qiaoxi.data.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "profile_pic_path")
    public String profilePath;

    @Ignore
    public int temp_profile;

    @Ignore
    public Integer unread;



    public ConversationModel(String currentName, String compactMan, MsgModel lastMessage) {
        this.currentName = currentName;
        this.compactMan = compactMan;
        this.lastMessage = lastMessage;
        this.conversationKey = currentName+compactMan;
    }

    public ConversationModel(String currentName, String compactMan, MsgModel lastMessage, int temp_profile, int unread) {
        this.currentName = currentName;
        this.compactMan = compactMan;
        this.lastMessage = lastMessage;
        this.conversationKey = currentName+compactMan;
        this.temp_profile = temp_profile;
        this.unread = unread;
    }
}
