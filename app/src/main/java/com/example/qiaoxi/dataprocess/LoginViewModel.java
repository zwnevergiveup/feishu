package com.example.qiaoxi.dataprocess;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.datasource.CallBacker;
import com.example.qiaoxi.datasource.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.model.ResultModel;
import com.example.qiaoxi.datasource.model.UserModel;
import com.example.qiaoxi.datasource.db.AppDatabase;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.google.gson.reflect.TypeToken;

import io.netty.channel.ChannelFuture;


public class LoginViewModel extends BaseViewModel {
    String mCurrentUserName;
    public MutableLiveData<UserModel> userModelLiveData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userPassword = new MutableLiveData<>();
    public MutableLiveData<Integer> visibility = new MutableLiveData<>();
    public MutableLiveData<ResultModel> result = new MutableLiveData<>();
    public MutableLiveData<Integer> lastUserIconVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> nameEditVisible = new MutableLiveData<>();
    public ChannelFuture channelFuture;

    private AppDatabase db;

    public LoginViewModel( Context context) {
        Log.e("qiaoxi","init loginViewModel");
        logout();
        String a = (String)SPHelper.getInstance(context).readObject("lastLoginName",new TypeToken<String>(){}.getType());
        if (a != null) {
            userName.setValue(a);
        }
        visibility.setValue(View.INVISIBLE);
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
        if (userName.getValue() == null || userPassword.getValue() == null || userName.getValue().equals("") || userPassword.getValue().equals("")) {
            result.setValue(new ResultModel(false, "请填写用户名和密码"));
            return;
        }
//        visibility.setValue(View.VISIBLE);
        result.postValue(new ResultModel(true,"登录成功"));

    }

    public void logout(){
        DataSourceHelper.getInstance().logoutEM(new CallBacker() {
            @Override
            public void onSuccess(Object message) {
                Log.e("qiaoxi",(String) message);
            }

            @Override
            public void onFail(Object reason) {
                Log.e("qiaoxi",(String) reason);
            }
        });
    }
}
