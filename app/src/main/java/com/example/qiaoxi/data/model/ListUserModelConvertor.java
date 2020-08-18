package com.example.qiaoxi.data.model;

import androidx.room.TypeConverter;

import com.example.qiaoxi.helper.json.JsonHelper;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ListUserModelConvertor {
    @TypeConverter
    public List<UserModel> string2UserModels(String str) {
        return (List<UserModel>) (JsonHelper.getInstance().getObject(str, new TypeToken<List<UserModel>>() {}.getType()));
    }

    @TypeConverter
   public String userModels2String(List<UserModel> models) {
        return JsonHelper.getInstance().toJson(models);
    }
}
