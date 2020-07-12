package com.example.qiaoxi.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;


@Entity(tableName = "userModels")
@TypeConverters(ListStringConvertor.class)
public class UserModel {
    @PrimaryKey
    @NonNull
    public String userId;

    @ColumnInfo(name = "friends")
    public List<String> friends;

    @ColumnInfo(name = "icon")
    public String icon;


    public UserModel(String userId, List<String> friends, String icon) {
        this.userId = userId;
        this.friends = friends;
        this.icon = icon;
    }
}
