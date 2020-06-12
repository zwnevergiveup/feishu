package com.example.feishu.ui.conversation;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ConversationViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private EMConversation conversation;
    private Disposable mDisposable;
    private List<EMMessage> list;


    public ConversationViewModel() {
        mText = new MutableLiveData<>();
        conversation = EMClient.getInstance().chatManager().getConversation("", EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 1,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                list = conversation.getAllMessages();

            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("FS","设置文本: " + aLong + "\n");
                if (list.isEmpty()) {
                    mText.setValue("没有任何消息");
                }else{
                    EMMessageBody body = list.get(list.size() -1).getBody();
                    String content = body.toString();
                    mText.setValue(content);
                }
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}