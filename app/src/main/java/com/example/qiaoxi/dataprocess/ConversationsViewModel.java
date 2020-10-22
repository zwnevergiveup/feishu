package com.example.qiaoxi.dataprocess;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.MsgModel;
import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.datasource.ListenRepositoryData;

import java.util.List;
import java.util.function.Consumer;

public class ConversationsViewModel extends BaseViewModel implements  ListenRepositoryData<MsgModel> {

    public MutableLiveData<List<ConversationModel>> conversations = new MutableLiveData<>();
    public MutableLiveData<ConversationModel> newConversation = new MutableLiveData<>();



    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().registerMessageListen(this);
        List<ConversationModel> conversationModels = DataSourceHelper.getInstance().readConversationsList(DataSourceHelper.getInstance().getCurrentUserName());
        if (conversationModels != null ) {
            conversations.setValue(conversationModels);
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().unregisterMessageListen(this);
    }

    @Override
    public void sendNewModel(MsgModel msgModel) {
        conversations.getValue().forEach(conversationModel -> {
            if ((conversationModel.currentName.equals(msgModel.receive) && conversationModel.contactMan.equals(msgModel.send)) || (conversationModel.currentName.equals(msgModel.send) && conversationModel.contactMan.equals(msgModel.receive))) {
                conversationModel.lastMessage = msgModel;
            }
        });
    }
}
