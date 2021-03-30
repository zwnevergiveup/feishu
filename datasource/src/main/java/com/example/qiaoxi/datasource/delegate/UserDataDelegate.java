package com.example.qiaoxi.datasource.delegate;

import com.example.qiaoxi.datasource.model.UserModel;

public interface UserDataDelegate {
    void write2DB(UserModel userModel);

    UserModel readUserFromDB(String current);
}
