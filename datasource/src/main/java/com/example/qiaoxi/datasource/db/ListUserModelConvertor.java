package com.example.qiaoxi.datasource.db;

import androidx.room.TypeConverter;

import com.example.qiaoxi.datasource.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ListUserModelConvertor {

    @TypeConverter
    public List<UserModel> string2UserModels(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str,new TypeToken<List<UserModel>>() {}.getType());
    }

    @TypeConverter
   public String userModels2String(List<UserModel> models) {
        Gson gson = new Gson();
        return gson.toJson(models);
    }
}
