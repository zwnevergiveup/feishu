package com.example.qiaoxi.activity.conversation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class CurrentConversationsViewModel extends ViewModel {

    private EMMessage temp;
    public EMConversation conversation;
    private Disposable mDisposable;
    public MutableLiveData<EMMessage> lastMessage = new MutableLiveData<>();
    public String conversationName;

    public CurrentConversationsViewModel(String titleName) {
        conversationName = titleName;
    }
    public static class Factory implements ViewModelProvider.Factory {
        private String mTitle;
        public Factory(String title) {
            mTitle = title;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CurrentConversationsViewModel(mTitle);
        }
    }

    public void loopReceiveConversation() {
        conversation = EMClient.getInstance().chatManager().getConversation(conversationName, EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 1,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                temp = conversation.getLastMessage();
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (temp != null) {
                    if (lastMessage.getValue() == null ) {
                        lastMessage.setValue(temp);
                    }else if (!lastMessage.getValue().getMsgId().equals(temp.getMsgId())) {
                        lastMessage.setValue(temp);
                    }
                }
            }
        });
    }
}