package com.example.qiaoxi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import java.util.UUID;


@Entity(tableName = "userModels")
@TypeConverters(ListUserModelConvertor.class)
public class UserModel  {
    @PrimaryKey
    @NonNull
    public String userId;

    @ColumnInfo(name = "friends")
    public List<UserModel> friends;

    @ColumnInfo(name = "icon")
    public String icon;

    @ColumnInfo(name = "privateFlag")
    public boolean privateFlag;

    @ColumnInfo(name = "userName")
    public String userName;

    public UserModel(String userName, List<UserModel> friends, String icon, boolean privateFlag) {
        this.userId = UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
        this.userName = userName;
        this.friends = friends;
        this.icon = icon;
        this.privateFlag = privateFlag;
    }

}
