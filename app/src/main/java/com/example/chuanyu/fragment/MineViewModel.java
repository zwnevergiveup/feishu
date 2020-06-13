package com.example.chuanyu.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hyphenate.chat.EMClient;

public class MineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(EMClient.getInstance().getCurrentUser());
    }

    public LiveData<String> getText() {
        return mText;
    }
}