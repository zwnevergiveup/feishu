package com.example.qiaoxi.dataprocess;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.data.repository.DataRepository;
import com.example.qiaoxi.data.repository.ListenRepositoryData;

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
