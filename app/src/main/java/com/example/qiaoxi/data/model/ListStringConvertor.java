package com.example.qiaoxi.data.model;

import androidx.room.TypeConverter;

import com.example.qiaoxi.helper.json.JsonHelper;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ListStringConvertor {
    @TypeConverter
    public List<String> string2UserModel(String str) {
        return (List<String>) (JsonHelper.getInstance().getObject(str, new TypeToken<List<String>>() {}.getType()));
    }

    @TypeConverter
   public String userModel2String(List<String> models) {
        return JsonHelper.getInstance().toJson(models);
    }
}
