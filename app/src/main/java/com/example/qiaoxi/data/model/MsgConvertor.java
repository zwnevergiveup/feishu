package com.example.qiaoxi.data.model;

import androidx.room.TypeConverter;

import com.example.qiaoxi.helper.json.JsonHelper;
import com.google.gson.reflect.TypeToken;

public class MsgConvertor {
    @TypeConverter
    public MsgModel string2MsgModel(String str) {
        return (MsgModel) (JsonHelper.getInstance().getObject(str,new TypeToken<MsgModel>() {}.getType()));
    }

    @TypeConverter
    public String msgModel2String(MsgModel msgModel) {
        return JsonHelper.getInstance().toJson(msgModel);
    }
}
