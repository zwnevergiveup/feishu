package com.example.qiaoxi.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.activity.base.BaseViewModel;
import com.hyphenate.chat.EMClient;

public class MineViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public MineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(EMClient.getInstance().getCurrentUser());
    }

    public LiveData<String> getText() {
        return mText;
    }
}