package com.example.qiaoxi.fragment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.ConversationModel;

import java.util.List;

public class ConversationsViewModel extends ViewModel {

    private AppDatabase db;
    public MutableLiveData<List<ConversationModel>> conversations = new MutableLiveData<>();

    public ConversationsViewModel() { }

    public void loadConversations() {
        Log.e("qiaoxi","reload conversation");
        db = DBHelper.getInstance().getAppDatabase(QXApplication.getContext(),"QX_DB");
        List<ConversationModel> models = db.conversationModelDao().getCurrentUserConversations(QXApplication.currentUser);
        if (models != null ){
            conversations.setValue(models);
        }
    }
}
