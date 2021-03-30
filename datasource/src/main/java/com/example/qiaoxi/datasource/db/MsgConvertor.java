package com.example.qiaoxi.datasource.db;

import androidx.room.TypeConverter;

import com.example.qiaoxi.datasource.model.MsgModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MsgConvertor {
    @TypeConverter
    public MsgModel string2MsgModel(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str,new TypeToken<MsgModel>() {}.getType());
    }

    @TypeConverter
    public String msgModel2String(MsgModel msgModel) {
        Gson gson = new Gson();
        return gson.toJson(msgModel);
    }
}
