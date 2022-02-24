package com.example.qiaoxi.datasource.datasource;

import com.example.qiaoxi.datasource.db.AppDatabase;
import com.example.qiaoxi.datasource.db.DBHelper;
import com.example.qiaoxi.datasource.delegate.ConversationModelDelegate;
import com.example.qiaoxi.datasource.delegate.MessageDataDelegate;
import com.example.qiaoxi.datasource.delegate.UserDataDelegate;
import com.example.qiaoxi.datasource.model.ConversationModel;
import com.example.qiaoxi.datasource.model.MsgModel;
import com.example.qiaoxi.datasource.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DataRepository  implements MessageDataDelegate, ConversationModelDelegate, UserDataDelegate {
    private static volatile DataRepository instance = null;

    private AppDatabase db;

    private List<ListenRepositoryData> mMsgListeners = new ArrayList<>();
    private List<ListenRepositoryData> mUserListeners = new ArrayList<>();
    private List<ListenRepositoryData> mConversationListeners = new ArrayList<>();



    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }
        return instance;
    }

    public synchronized void processNewMessage(MsgModel msgModel) {
        write2DB(msgModel);
        mMsgListeners.forEach(listenRepositoryData -> listenRepositoryData.sendNewModel(msgModel));
    }

    public synchronized void processNewConversation(ConversationModel conversationModel) {
        write2DB(conversationModel);
        mConversationListeners.forEach(listenRepositoryData -> listenRepositoryData.sendNewModel(conversationModel));
    }

    @Override
    public synchronized void write2DB(UserModel userModel) {
        db.userModelDao().insert(userModel);
    }

    @Override
    public UserModel readUserFromDB(String current) {
        return db.userModelDao().getCurrentUserModel(current);
    }

    @Override
    public synchronized void write2DB(ConversationModel conversationModel) {
        db.conversationModelDao().insert(conversationModel);
    }

    @Override
    public synchronized void write2DB(MsgModel msgModel) {
        db.msgModelDao().insertAll(msgModel);
    }

    @Override
    public List<MsgModel> readMsgFromDB( String current ,String contact, int size) {
        return db.msgModelDao().loadMsgByName(contact,current);
    }

    @Override
    public List<MsgModel> findMsgFromDB(String send, String receive) {
        return db.msgModelDao().find(send,receive);
    }

    @Override
    public List<ConversationModel> readConversationsFromDB(String current) {
        return db.conversationModelDao().getCurrentUserConversations(current);
    }

    public void registerMsgListener(ListenRepositoryData<MsgModel> listener) {
        this.mMsgListeners.add(listener);
    }

    public void registerConversationListener(ListenRepositoryData<ConversationModel> listener) {
        this.mConversationListeners.add(listener);
    }

    public void registerUserListener(ListenRepositoryData<UserModel> listener) {
        this.mUserListeners.add(listener);
    }

    public void unregisterMsgListener(ListenRepositoryData<MsgModel> listener) {
        this.mMsgListeners.remove(listener);
    }

    public void unregisterConversationListener(ListenRepositoryData<ConversationModel> listener) {
        this.mConversationListeners.remove(listener);
    }

    public void unregisterUserListener(ListenRepositoryData<UserModel> listener) {
        this.mUserListeners.remove(listener);
    }

}
