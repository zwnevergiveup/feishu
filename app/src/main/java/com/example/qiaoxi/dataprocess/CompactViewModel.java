package com.example.qiaoxi.dataprocess;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.data.repository.DataRepository;
import com.example.qiaoxi.widget.QXApplication;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompactViewModel extends BaseViewModel {

    private MutableLiveData<List<UserModel>> mContactList;

    public CompactViewModel() {
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        DataRepository repository = DataRepository.getInstance();
        UserModel userModel = repository.readUserFromDB(QXApplication.currentUser);
        if (userModel != null && userModel.friends.size() >0) {
            mContactList.getValue().addAll(userModel.friends);
        }
    }

    public LiveData<List<UserModel>> getContactList() {
        return mContactList;
    }

}