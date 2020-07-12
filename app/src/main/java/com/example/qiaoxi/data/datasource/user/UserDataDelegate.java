package com.example.qiaoxi.data.datasource.user;

import com.example.qiaoxi.data.model.UserModel;

import java.util.List;

public interface UserDataDelegate {
    void write2DB(UserModel userModel);

    UserModel readUserFromDB(String current);
}
