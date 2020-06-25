package com.example.qiaoxi.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.qiaoxi.helper.model.UserConvertor;

import java.util.List;


@Entity(tableName = "userModels")
@TypeConverters(UserConvertor.class)
public class UserModel {
    @PrimaryKey
    @NonNull
    public String userId;

    @ColumnInfo(name = "friends")
    public List<UserModel> friends;

    @ColumnInfo(name = "icon")
    public String icon;

}
