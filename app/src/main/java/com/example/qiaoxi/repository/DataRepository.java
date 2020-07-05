package com.example.qiaoxi.repository;

import android.util.Log;

import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.datasource.message.MessageDataDelegate;
import com.example.qiaoxi.datasource.message.MessageListenDataSource;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.ConversationModel;
import com.example.qiaoxi.model.MsgModel;
import com.example.qiaoxi.model.UserModel;

import java.util.List;

public class DataRepository  implements MessageDataDelegate {
    public static volatile DataRepository instance = null;

    private AppDatabase db;

    private ListenRepositoryData mMsgModelListener;
    private ListenRepositoryData mUserModelListener;
    private ListenRepositoryData mConversationModelListener;


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
        mMsgModelListener.sendNewMsgModel(msgModel);
//        write2DB(msgModel);
        Log.e("qiaoxi","repository" + msgModel.content);
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
    public List<MsgModel> readFromDB(String compact) {
        return db.msgModelDao().loadMsgByName(compact,QXApplication.currentUser);
    }

    public void setListenRepositoryData(ListenRepositoryData listener) {
        this.mMsgModelListener = listener;
    }

}
