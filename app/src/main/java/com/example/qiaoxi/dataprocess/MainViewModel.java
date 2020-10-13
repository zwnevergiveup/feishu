package com.example.qiaoxi.dataprocess;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.ListenRepositoryData;
import com.example.qiaoxi.datasource.MsgModel;

public final class MainViewModel extends BaseViewModel implements ListenRepositoryData<MsgModel> {
    public MainViewModel() {

    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().registerMessageListen(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().unregisterMessageListen(this);
    }

    @Override
    public void sendNewModel(MsgModel msgModel) {

    }
}
