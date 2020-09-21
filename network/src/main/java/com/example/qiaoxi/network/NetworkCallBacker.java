package com.example.qiaoxi.network;

public interface NetworkCallBacker {
    void onSuccess(ResponseModel model);
    void onFail(ResponseModel model);
}
