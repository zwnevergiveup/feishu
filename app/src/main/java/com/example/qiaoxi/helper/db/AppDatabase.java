package com.example.qiaoxi.helper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qiaoxi.model.MsgModel;

@Database(entities = {MsgModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  MsgModelDao msgModelDao();
}
