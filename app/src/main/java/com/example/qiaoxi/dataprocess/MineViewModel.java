package com.example.qiaoxi.dataprocess;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.datasource.DataSourceHelper;

public class MineViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public MineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(DataSourceHelper.getInstance().getCurrentUserName());
    }

    public LiveData<String> getText() {
        return mText;
    }
}