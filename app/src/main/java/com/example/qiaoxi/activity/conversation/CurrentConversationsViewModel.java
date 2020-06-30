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
import com.hyphenate.chat.EMMessage;

import java.util.List;

public final class CurrentConversationsViewModel extends ViewModel {
    public String conversationName;
    public MutableLiveData<String> editText = new MutableLiveData<>();
    public MutableLiveData<MsgModel> msgModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<MsgModel>> lastMessages = new MutableLiveData<>();
    private AppDatabase db;

    public CurrentConversationsViewModel(String titleName, Context context) {
        conversationName = titleName;
        db = DBHelper.getInstance().getAppDatabase(context,"QX_DB");
        List<MsgModel> temp = db.msgModelDao().loadMsgByName(conversationName, EMClient.getInstance().getCurrentUser());
        lastMessages.setValue(temp);
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

    public void sendMessage() {
        if (editText.getValue() != null && !editText.getValue().equals("")){
            EMMessage a = EMMessage.createTxtSendMessage(editText.getValue(),conversationName);
            EMClient.getInstance().chatManager().sendMessage(a);
            MsgModel msg = new MsgModel(a);
            db.msgModelDao().insertAll(msg);
            msgModelMutableLiveData.setValue(msg);
            editText.setValue("");
        }
    }
}