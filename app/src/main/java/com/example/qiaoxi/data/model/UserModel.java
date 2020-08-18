package com.example.qiaoxi.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;


@Entity(tableName = "userModels")
@TypeConverters(ListUserModelConvertor.class)
public class UserModel {
    @PrimaryKey
    @NonNull
    public String userId;

    @ColumnInfo(name = "friends")
    public List<UserModel> friends;

    @ColumnInfo(name = "icon")
    public String icon;

    @ColumnInfo(name = "privateFlag")
    public boolean privateFlag;


    public UserModel(String userId, List<UserModel> friends, String icon, boolean privateFlag) {
        this.userId = userId;
        this.friends = friends;
        this.icon = icon;
        this.privateFlag = privateFlag;
    }
}
