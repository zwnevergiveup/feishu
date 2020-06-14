package com.example.qiaoxi.activity.conversation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CurrentConversationsViewModel extends ViewModel {

    private EMMessage lastMessage;
    private EMConversation conversation;
    private Disposable mDisposable;
    private EMMessage emMessage;
    public MutableLiveData<String> str ;
    private final MutableLiveData<List<EMMessage>> mEMMessageList = new MutableLiveData<>();


    public CurrentConversationsViewModel() {
        str = new MutableLiveData<>();
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
                if (aLong == 10) {
                    str.setValue( "ok get");
                }else if (aLong == 20) {
                    str.setValue( "10s adter");
                }
//                if (emMessage != null && (!emMessage.getFrom().equals( EMClient.getInstance().getCurrentUser()))) {
//                    if (lastMessage == null) {
//                        lastMessage = emMessage;
////                        EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
////                        mEMMessageList.add(emMessage);
//                    }else {
//                        if (emMessage != null  && (!emMessage.getMsgId().equals(lastMessage.getMsgId()))) {
//                            lastMessage = emMessage;
//                            EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
////                            mEMMessageList.add(emMessage);
//                        }
//                    }
//                }

            }
        });
    }
}