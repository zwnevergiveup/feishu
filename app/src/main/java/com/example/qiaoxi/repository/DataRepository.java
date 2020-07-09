package com.example.qiaoxi.repository;

import android.util.Log;

import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.datasource.conversation.ConversationModelDelegate;
import com.example.qiaoxi.datasource.message.MessageDataDelegate;
import com.example.qiaoxi.datasource.message.MessageListenDataSource;
import com.example.qiaoxi.fragment.ConversationsViewModel;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DataRepository  implements MessageDataDelegate, ConversationModelDelegate {
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
        mMsgListeners.forEach(listenRepositoryData -> listenRepositoryData.sendNewMsgModel(msgModel));
        write2DB(msgModel);
    }

    public synchronized void write2DB(UserModel userModel) {
        db.userModelDao().insert(userModel);
    }

    public synchronized void write2DB(ConversationModel conversationModel) {
        db.conversationModelDao().insert(conversationModel);
    }

    @Override
    public synchronized void write2DB(MsgModel msgModel) {
        db.msgModelDao().insertAll(msgModel);
    }

    @Override
    public List<MsgModel> readMsgFromDB(String compact) {
        return db.msgModelDao().loadMsgByName(compact,QXApplication.currentUser);
    }

    @Override
    public List<ConversationModel> readConversationsFromDB(String current) {
        return db.conversationModelDao().getCurrentUserConversations(current);
    }

    public void setListenRepositoryData(ListenRepositoryData listener) {
        this.mMsgListeners.add(listener);
    }

}
