package com.example.qiaoxi.datasource.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qiaoxi.datasource.db.ConversationModelDao;
import com.example.qiaoxi.datasource.db.MsgModelDao;
import com.example.qiaoxi.datasource.db.UserModelDao;
import com.example.qiaoxi.datasource.model.ConversationModel;
import com.example.qiaoxi.datasource.model.MsgModel;
import com.example.qiaoxi.datasource.model.UserModel;

@Database(entities = {MsgModel.class, UserModel.class, ConversationModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MsgModelDao msgModelDao();
    public abstract UserModelDao userModelDao();
    public abstract ConversationModelDao conversationModelDao();
}
