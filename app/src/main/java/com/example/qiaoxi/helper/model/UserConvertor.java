package com.example.qiaoxi.helper.model;

import androidx.room.TypeConverter;

import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.model.UserModel;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class UserConvertor {
    @TypeConverter
    public List<UserModel> string2UserModel(String str) {
        return (List<UserModel>) (JsonHelper.getInstance().getObject(str, new TypeToken<List<UserModel>>() {}.getType()));
    }

    @TypeConverter
   public String userModel2String(List<UserModel> models) {
        return JsonHelper.getInstance().toJson(models);
    }
}
