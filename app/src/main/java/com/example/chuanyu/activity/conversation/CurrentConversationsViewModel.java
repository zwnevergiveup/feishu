package com.example.chuanyu.activity.conversation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CurrentConversationsViewModel extends ViewModel {

    private EMMessage lastMessage;
    private EMConversation conversation;
    public Disposable mDisposable;
    private EMMessage emMessage;
    private MutableLiveData<String> mText;


    public CurrentConversationsViewModel() {
        mText = new MutableLiveData<>();
        conversation = EMClient.getInstance().chatManager().getConversation("zhongwu", EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 1,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                emMessage = conversation.getLastMessage();
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (emMessage != null && (!emMessage.getFrom().equals( EMClient.getInstance().getCurrentUser()))) {
                    if (lastMessage == null) {
                        lastMessage = emMessage;
                        EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
                        mText.setValue(textBody.getMessage());
                    }else {
                        if (emMessage != null  && (!emMessage.getMsgId().equals(lastMessage.getMsgId()))) {
                            lastMessage = emMessage;
                            EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
                            mText.setValue(textBody.getMessage());
                        }
                    }
                }

            }
        });
    }

    public LiveData<String> getLastMessageText() {
        return mText;
    }

}