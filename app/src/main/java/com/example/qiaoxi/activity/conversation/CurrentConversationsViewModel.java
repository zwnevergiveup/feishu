package com.example.qiaoxi.activity.conversation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.activity.BaseViewModel;
import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;
import com.example.qiaoxi.repository.DataRepository;
import com.example.qiaoxi.repository.ListenRepositoryData;
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
        //viewModel 注册repository
        DataRepository repository = DataRepository.getInstance();
        repository.setListenRepositoryData(this);
        List<MsgModel> list = repository.readMsgFromDB(conversationName);
        if (list != null && list.size() > 0) {
            lastMessages.setValue(list);
        }
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

    public void sendMessage() {
        if (editText.getValue() != null && !editText.getValue().equals("")){
            EMMessage a = EMMessage.createTxtSendMessage(editText.getValue(),conversationName);
            EMClient.getInstance().chatManager().sendMessage(a);
            MsgModel msg = new MsgModel(a);
            msgModelMutableLiveData.setValue(msg);
            editText.setValue("");
            insertMsgModel(msg);
        }
    }

    private void insertMsgModel(MsgModel msgModel) {
        DataRepository repository = DataRepository.getInstance();
        repository.write2DB(msgModel);
    }

    @Override
    public void sendNewMsgModel(MsgModel msgModel) {
        msgModelMutableLiveData.postValue(msgModel);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.write2DB(new ConversationModel(QXApplication.currentUser,conversationName,msgModelMutableLiveData.getValue()));
    }
}