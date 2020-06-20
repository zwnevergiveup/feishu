package com.example.qiaoxi.helper.db;

import android.content.Context;

import androidx.room.Room;

public class DBHelper {
    private static volatile DBHelper instance = null;
    private DBHelper() { }

    private AppDatabase appDatabase;

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public AppDatabase getAppDatabase(Context context, String databaseName) {
        if (appDatabase == null) {
            appDatabase =  Room.databaseBuilder(context,AppDatabase.class,databaseName).build();
        }
        return appDatabase;
    }
}
