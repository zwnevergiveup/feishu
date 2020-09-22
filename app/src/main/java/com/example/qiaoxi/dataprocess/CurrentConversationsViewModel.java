package com.example.qiaoxi.dataprocess;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.MsgModel;
import com.example.qiaoxi.datasource.DataRepository;
import com.example.qiaoxi.datasource.ListenRepositoryData;
import com.example.qiaoxi.widget.QXApplication;

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
    public void onCreate(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.registerMsgListener(this);
        List<MsgModel> list = repository.readMsgFromDB(QXApplication.currentUser , conversationName);
        if (list != null && list.size() > 0) {
            lastMessages.setValue(list);
        }
    }

    public void sendMessage() {
        if (editText.getValue() != null && !editText.getValue().isEmpty()) {
            DataSourceHelper.getInstance().sendMessage(editText.getValue(),conversationName, (message) -> {
                MsgModel model = new MsgModel(message);
                msgModelMutableLiveData.postValue(model);
                insertMsgModel(model);
            });
            editText.setValue("");
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
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.unregisterMsgListener(this);
    }
}