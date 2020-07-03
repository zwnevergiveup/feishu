package com.example.qiaoxi.fragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qiaoxi.activity.BaseViewModel;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompactViewModel extends BaseViewModel {

    private MutableLiveData<List<String>> mContactList;

    public CompactViewModel() {
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        List<String> a = new ArrayList<>();
        a.add("zhongwu");
        a.add("wus6");
        String user = EMClient.getInstance().getCurrentUser();
        mContactList.getValue().addAll(a.stream().filter(p -> !user.equals(p)).collect(Collectors.<String>toList()));
    }

    public LiveData<List<String>> getContactList() {
        return mContactList;
    }
    private void listenContact() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                mContactList.getValue().add(s);
                Log.e("qiaoxi","has onContactAdded");
            }

            @Override
            public void onContactDeleted(String s) {
                Log.e("qiaoxi","has onContactDeleted");
            }

            @Override
            public void onContactInvited(String s, String s1) {
                Log.e("qiaoxi","has agreed");
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                Log.e("qiaoxi","onFriendRequestAccepted");

            }

            @Override
            public void onFriendRequestDeclined(String s) {
                Log.e("qiaoxi","onFriendRequestDeclined");

            }
        });
    }
}