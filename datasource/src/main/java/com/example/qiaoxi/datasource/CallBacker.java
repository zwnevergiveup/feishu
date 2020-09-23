package com.example.qiaoxi.datasource;

public interface CallBacker {
    public void onSuccess(Object message);
    public void onFail(Object reason);
}
