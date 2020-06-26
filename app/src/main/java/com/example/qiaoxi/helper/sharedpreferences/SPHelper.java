package com.example.qiaoxi.helper.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qiaoxi.helper.json.JsonHelper;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SPHelper {
    private SharedPreferences mSP;
    private SPHelper(Context context){
        mSP = context.getSharedPreferences("QX_SP", Context.MODE_PRIVATE);
    }
    private volatile static SPHelper instance = null;
    public static SPHelper getInstance(Context context){
        if (instance == null) {
            synchronized (SPHelper.class) {
                if (instance == null) {
                    instance = new SPHelper(context);
                }
            }
        }
        return instance;
    }

    public void writeObject(Object value, String key) {
        String jsonStr = new Gson().toJson(value);
        SharedPreferences.Editor editor = mSP.edit();
        editor.putString(key,jsonStr);
        editor.apply();
    }

    public Object readObject(String key, Type type) {
        String objJson = mSP.getString(key,null);
        if (objJson == null) return null;
        return JsonHelper.getInstance().getObject(objJson,type);
    }
}
