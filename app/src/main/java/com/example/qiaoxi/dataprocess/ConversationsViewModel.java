package com.example.qiaoxi.dataprocess;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.datasource.ListenRepositoryData;

import java.util.List;

public class ConversationsViewModel extends BaseViewModel implements ListenRepositoryData<ConversationModel> {

    public MutableLiveData<List<ConversationModel>> conversations = new MutableLiveData<>();
    public MutableLiveData<ConversationModel> newConversation = new MutableLiveData<>();

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().registerConversationListen(this);
        List<ConversationModel> conversationModels = DataSourceHelper.getInstance().readConversationsList(DataSourceHelper.getInstance().getCurrentUserName());
        if (conversationModels != null && conversationModels.size() > 0 ) {
            conversations.setValue(conversationModels);
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().unregisterConversationListen(this);
    }

    @Override
    public void sendNewModel(ConversationModel conversationModel) {
        List<ConversationModel> conversationModels = DataSourceHelper.getInstance().readConversationsList(DataSourceHelper.getInstance().getCurrentUserName());
        if (conversationModels != null && conversationModels.size() > 0 ) {
            conversations.postValue(conversationModels);
        }
    }
}
