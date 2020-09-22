package com.example.qiaoxi.datasource;

import com.example.qiaoxi.datasource.UserModel;

public interface UserDataDelegate {
    void write2DB(UserModel userModel);

    UserModel readUserFromDB(String current);
}
