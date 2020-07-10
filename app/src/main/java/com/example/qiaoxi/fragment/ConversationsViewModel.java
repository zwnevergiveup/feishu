package com.example.qiaoxi.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.activity.BaseViewModel;
import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;
import com.example.qiaoxi.repository.DataRepository;
import com.example.qiaoxi.repository.ListenRepositoryData;

import java.util.List;

public class ConversationsViewModel extends BaseViewModel implements ListenRepositoryData<ConversationModel> {

    public MutableLiveData<List<ConversationModel>> conversations = new MutableLiveData<>();
    public MutableLiveData<ConversationModel> newConversation = new MutableLiveData<>();

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.registerConversationListener(this);
        List<ConversationModel> conversationModels = repository.readConversationsFromDB(QXApplication.currentUser);
        if (conversationModels != null && conversationModels.size() > 0 ) {
            conversations.setValue(conversationModels);
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        repository.unregisterConversationListener(this);
    }

    @Override
    public void sendNewModel(ConversationModel conversationModel) {
        DataRepository repository = DataRepository.getInstance();
        List<ConversationModel> conversationModels = repository.readConversationsFromDB(QXApplication.currentUser);
        if (conversationModels != null && conversationModels.size() > 0 ) {
            conversations.postValue(conversationModels);
        }
    }
}
