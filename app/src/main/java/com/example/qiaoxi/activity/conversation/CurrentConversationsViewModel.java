package com.example.qiaoxi.activity.conversation;

import android.util.Log;

import androidx.annotation.NonNull;
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

public class CurrentConversationsViewModel extends ViewModel {

    private EMMessage lastMessage;
    public EMConversation conversation;
    private Disposable mDisposable;
    private EMMessage emMessage;
    private List<EMMessage> temp = new ArrayList<>();
    public MutableLiveData<String> conversationName = new MutableLiveData<>();
    public final MutableLiveData<List<EMMessage>> mEMMessageList = new MutableLiveData<>();

    public CurrentConversationsViewModel(String titleName) {
        mEMMessageList.setValue(new ArrayList<>());
        conversationName.setValue(titleName);
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
        conversation = EMClient.getInstance().chatManager().getConversation(conversationName.getValue(), EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 5,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                temp = conversation.getAllMessages();

            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                conversationName.setValue(""+aLong);
                mEMMessageList.setValue(temp);
//                if (emMessage != null && (!emMessage.getFrom().equals( EMClient.getInstance().getCurrentUser()))) {
//                    if (lastMessage == null) {
//                        lastMessage = emMessage;
////                        EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
//                        mEMMessageList.getValue().add(emMessage);
//                    }else {
//                        if (emMessage != null  && (!emMessage.getMsgId().equals(lastMessage.getMsgId()))) {
//                            lastMessage = emMessage;
//                            EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
//                            mEMMessageList.getValue().add(emMessage);
//                        }
//                    }
//                }

            }
        });
    }
}