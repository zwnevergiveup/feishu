package com.example.qiaoxi.dataprocess;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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