package com.example.qiaoxi.activity.conversation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.MsgModel;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public final class CurrentConversationsViewModel extends ViewModel {
    public MutableLiveData<List<MsgModel>> msgModels = new MutableLiveData<>();
    private Disposable mDisposable;
    public String conversationName;

    public CurrentConversationsViewModel(String titleName, Context context) {
        conversationName = titleName;
        AppDatabase db = DBHelper.getInstance().getAppDatabase(context,"QX_DB");
        List<MsgModel> temp = db.msgModelDao().loadMsgByName(conversationName, EMClient.getInstance().getCurrentUser());
        msgModels.setValue(temp);
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
}