package com.example.qiaoxi.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.activity.BaseViewModel;
import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.repository.DataRepository;

import java.util.List;

public class ConversationsViewModel extends BaseViewModel {

    public MutableLiveData<List<ConversationModel>> conversations = new MutableLiveData<>();

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DataRepository repository = DataRepository.getInstance();
        List<ConversationModel> conversationModels = repository.readConversationsFromDB(QXApplication.currentUser);
        if (conversationModels != null && conversationModels.size() > 0 ) {
            conversations.setValue(conversationModels);
        }
    }
}
