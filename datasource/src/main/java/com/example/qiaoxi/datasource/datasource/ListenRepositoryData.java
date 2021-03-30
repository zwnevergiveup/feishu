package com.example.qiaoxi.datasource.datasource;

public interface ListenRepositoryData<T> {
    void sendNewModel(T t);
}
