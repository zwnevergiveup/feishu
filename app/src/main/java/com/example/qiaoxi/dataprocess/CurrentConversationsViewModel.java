package com.example.qiaoxi.dataprocess;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.data.model.MsgModel;
import com.example.qiaoxi.data.repository.DataRepository;
import com.example.qiaoxi.data.repository.ListenRepositoryData;
import com.example.qiaoxi.widget.QXApplication;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public final class CurrentConversationsViewModel extends BaseViewModel implements ListenRepositoryData<MsgModel> {
    public String conversationName;
    public MutableLiveData<String> editText = new MutableLiveData<>();
    public MutableLiveData<MsgModel> msgModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<MsgModel>> lastMessages = new MutableLiveData<>();

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

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.registerMsgListener(this);
        List<MsgModel> list = repository.readMsgFromDB(QXApplication.currentUser , conversationName);
        if (list != null && list.size() > 0) {
            lastMessages.setValue(list);
        }
    }

    public void sendMessage() {
        if (editText.getValue() != null && !editText.getValue().equals("")){
            EMMessage a = EMMessage.createTxtSendMessage(editText.getValue(),conversationName);
            EMClient.getInstance().chatManager().sendMessage(a);
            MsgModel msg = new MsgModel(a);
            editText.setValue("");
            insertMsgModel(msg);
        }
    }

    private void insertMsgModel(MsgModel msgModel) {
        DataRepository repository = DataRepository.getInstance();
        repository.processNewMessage(msgModel);
        repository.processNewConversation(new ConversationModel(msgModel.send,msgModel.receive,msgModel));
    }

    @Override
    public void sendNewModel(MsgModel msgModel) {
        msgModelMutableLiveData.postValue(msgModel);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.unregisterMsgListener(this);
    }
}