package com.example.qiaoxi.activity.conversation;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.MsgModel;
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

    private List<MsgModel> temp = new ArrayList<>();
    public MutableLiveData<List<MsgModel>> msgModels = new MutableLiveData<>();
    public EMConversation conversation;
    private Disposable mDisposable;
    public MutableLiveData<EMMessage> lastMessage = new MutableLiveData<>();
    public String conversationName;
    private Context mContext;
    private AppDatabase db;

    public CurrentConversationsViewModel(String titleName, Context context) {
        conversationName = titleName;
        mContext = context;
        db = DBHelper.getInstance().getAppDatabase(context,"messageDB");
    }
    public static class Factory implements ViewModelProvider.Factory {
        private String mTitle;
        private Context mContext;
        public Factory(String title, Context context) {
            mTitle = title;
            mContext = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CurrentConversationsViewModel(mTitle, mContext);
        }
    }

    public void loopReceiveConversation() {
        conversation = EMClient.getInstance().chatManager().getConversation(conversationName, EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 1,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                temp = db.msgModelDao().loadMsgByName(conversationName);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                msgModels.setValue(temp);
            }
        });
    }
}