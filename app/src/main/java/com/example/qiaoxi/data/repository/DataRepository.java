package com.example.qiaoxi.data.repository;

import com.example.qiaoxi.data.datasource.user.UserDataDelegate;
import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.data.datasource.conversation.ConversationModelDelegate;
import com.example.qiaoxi.data.datasource.message.MessageDataDelegate;
import com.example.qiaoxi.data.datasource.message.MessageListenDataSource;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.data.model.MsgModel;
import com.example.qiaoxi.data.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DataRepository  implements MessageDataDelegate, ConversationModelDelegate, UserDataDelegate {
    public static volatile DataRepository instance = null;

    private AppDatabase db;

    private List<ListenRepositoryData> mMsgListeners = new ArrayList<>();
    private List<ListenRepositoryData> mUserListeners = new ArrayList<>();
    private List<ListenRepositoryData> mConversationListeners = new ArrayList<>();


    private DataRepository() {
        db = DBHelper.getInstance().getAppDatabase(QXApplication.getContext(),"QX_DB");
        new MessageListenDataSource(this);
    }

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
    public List<MsgModel> readMsgFromDB( String current ,String contact) {
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
