package com.example.qiaoxi.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName  = "tb_contact")
public class ContactModel {

    @PrimaryKey
    @NonNull
    public String mainId;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "friend_name")
    public String friendName;

    @ColumnInfo(name = "friend_nickname")
    public String friendNickName;


}