package com.example.qiaoxi.datasource;

public interface ListenRepositoryData<T> {
    void sendNewModel(T t);
}
