package com.example.qiaoxi.helper.json;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonHelper {
    private volatile static JsonHelper instance = null;

    private JsonHelper() {}

    public static JsonHelper getInstance() {
        if (instance == null) {
            synchronized (JsonHelper.class) {
                if (instance == null) {
                    instance = new JsonHelper();
                }
            }
        }
        return instance;
    }

    public <T> T getObject(String objStr, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(objStr,type);
    }

    public String toJson(Object t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }
}
