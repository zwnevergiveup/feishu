package com.example.qiaoxi.dataprocess;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.datasource.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.model.MsgModel;
import com.example.qiaoxi.datasource.datasource.ListenRepositoryData;

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
        DataSourceHelper.getInstance().registerMessageListen(this);
        List<MsgModel> list = DataSourceHelper.getInstance().readMsgHistory(DataSourceHelper.getInstance().getCurrentUserName() , conversationName,10);
        if (list != null && list.size() > 0) {
            lastMessages.setValue(list);
        }
    }

    public void sendMessage() {
        if (editText.getValue() != null && !editText.getValue().isEmpty()) {
            DataSourceHelper.getInstance().sendMessage(editText.getValue(),conversationName, (msgModel) -> {
                msgModelMutableLiveData.postValue(msgModel);
            });
            editText.setValue("");
        }
    }

    @Override
    public void sendNewModel(MsgModel msgModel) {
        msgModelMutableLiveData.postValue(msgModel);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().unregisterMessageListen(this);
    }
}