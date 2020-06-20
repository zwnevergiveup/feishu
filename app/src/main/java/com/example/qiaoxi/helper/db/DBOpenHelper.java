package com.example.qiaoxi.helper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Room;

public final class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "qiaoxi.db";
    public static final int DB_VERSION = 1;



    public DBOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
