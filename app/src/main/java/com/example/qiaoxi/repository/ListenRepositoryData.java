package com.example.qiaoxi.repository;

import com.example.qiaoxi.model.MsgModel;

public interface ListenRepositoryData<T> {
    void sendNewModel(T t);
}
