package com.example.qiaoxi.datasource;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MsgModel.class, UserModel.class, ConversationModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  MsgModelDao msgModelDao();
    public abstract UserModelDao userModelDao();
    public abstract ConversationModelDao conversationModelDao();
}
