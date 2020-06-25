package com.example.qiaoxi.activity.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.UserModel;

public class LoginViewModel extends ViewModel {
    String mCurrentUserName;
    public MutableLiveData<UserModel> userModelLiveData = new MutableLiveData<>();

    public LoginViewModel(String currentUserName, Context context) {
        AppDatabase db = DBHelper.getInstance().getAppDatabase(context,"QX_DB");
        userModelLiveData.setValue(db.userModelDao().getCurrentUserModel(currentUserName));
    }
}
