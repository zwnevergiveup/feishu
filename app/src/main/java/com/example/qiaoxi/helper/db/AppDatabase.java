package com.example.qiaoxi.helper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;

@Database(entities = {MsgModel.class, UserModel.class, ConversationModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  MsgModelDao msgModelDao();
    public abstract UserModelDao userModelDao();
    public abstract ConversationModelDao conversationModelDao();
}
