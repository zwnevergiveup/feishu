package com.example.qiaoxi.activity.login;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.model.ResultModel;
import com.example.qiaoxi.model.UserModel;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;


public class LoginViewModel extends ViewModel {
    String mCurrentUserName;
    public MutableLiveData<UserModel> userModelLiveData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userPassword = new MutableLiveData<>();
    public MutableLiveData<ResultModel> result = new MutableLiveData<>();
    public MutableLiveData<Integer> lastUserIconVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> nameEditVisible = new MutableLiveData<>();

    private AppDatabase db;

    public LoginViewModel( Context context) {
        Log.e("qiaoxi","init loginViewModel");
        logout();
        db = DBHelper.getInstance().getAppDatabase(context, "QX_DB");
        String a = (String)SPHelper.getInstance(context).readObject("lastLoginName",new TypeToken<String>(){}.getType());
        if (a != null) {
            UserModel userModel = db.userModelDao().getCurrentUserModel(a);
            if (userModel != null) {
                userModelLiveData.setValue(userModel);
                userName.setValue(userModel.userId);
                lastUserIconVisible.setValue(View.VISIBLE);
                nameEditVisible.setValue(View.GONE);
                return;
            }
        }
        lastUserIconVisible.setValue(View.GONE);
        nameEditVisible.setValue(View.VISIBLE);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private Context mContext;
        public Factory(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LoginViewModel( mContext);
        }
    }

    public void login() {
        if (userName.getValue() == null || userPassword.getValue() == null) {
            result.setValue(new ResultModel(false,"请输入用户名和密码"));
            return;
        }
        String name = userName.getValue().trim();
        String secret = userPassword.getValue().trim();
        EMClient.getInstance().login(name, secret, new EMCallBack() {
            @Override
            public void onSuccess() {
                QXApplication.currentUser = name;
                result.postValue(new ResultModel(true,name));
                try{
                    List<String> friends = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    db.userModelDao().insert(new UserModel(name,friends,""));
                }catch (HyphenateException e) {
                    db.userModelDao().insert(new UserModel(name,new ArrayList<>(),""));
                }
            }

            @Override
            public void onError(int i, String s) {
                result.postValue(new ResultModel(false,s));
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    public void logout(){
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qiaoxi","logout success");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qiaoxi","logout error");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
