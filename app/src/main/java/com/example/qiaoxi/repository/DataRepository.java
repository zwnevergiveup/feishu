package com.example.qiaoxi.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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
    public MutableLiveData<UserModel> userModel;
    public MutableLiveData<MsgModel> msgModel;
    public MutableLiveData<ConversationModel> conversationModel;
    private MessageListenDataSource msgDataSource;

    private DataRepository() {
        db = DBHelper.getInstance().getAppDatabase(QXApplication.getContext(),"QX_DB");
        userModel = new MutableLiveData<>();
        msgModel = new MutableLiveData<>();
        conversationModel = new MutableLiveData<>();
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

    public void initDataSource(){
        msgDataSource = new MessageListenDataSource();
    }


    public synchronized void processNewMessage(MsgModel msgModel) {
        this.msgModel.postValue(msgModel);
        write2DB(msgModel);
        Log.e("qiaoxi",msgModel.content);
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
}
