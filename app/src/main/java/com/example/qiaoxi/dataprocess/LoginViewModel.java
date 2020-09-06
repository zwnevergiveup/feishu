package com.example.qiaoxi.dataprocess;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.data.model.ChatMsg;
import com.example.qiaoxi.data.model.network.LoginBean;
import com.example.qiaoxi.data.model.network.LogonBean;
import com.example.qiaoxi.data.model.network.ResponseModel;
import com.example.qiaoxi.data.model.ResultModel;
import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.helper.retrofit.RetrofitHelper;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.widget.QXApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Map;

import io.netty.channel.ChannelFuture;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginViewModel extends BaseViewModel {
    String mCurrentUserName;
    public MutableLiveData<UserModel> userModelLiveData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userPassword = new MutableLiveData<>();
    public MutableLiveData<ResultModel> result = new MutableLiveData<>();
    public MutableLiveData<Integer> lastUserIconVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> nameEditVisible = new MutableLiveData<>();
    public ChannelFuture channelFuture;

    private AppDatabase db;

    public LoginViewModel( Context context) {
        Log.e("qiaoxi","init loginViewModel");
        logout();
        db = DBHelper.getInstance().getAppDatabase(context, "QX_DB");
        String a = (String)SPHelper.getInstance(context).readObject("lastLoginName",new TypeToken<String>(){}.getType());
        if (a != null) {
            userName.setValue(a);
        }
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
        try{
            EMClient.getInstance().login(userName.getValue(), userPassword.getValue(), new EMCallBack() {
                @Override
                public void onSuccess() {
                    QXApplication.currentUser = userName.getValue();
                    result.postValue(new ResultModel(true,"登录成功"));
                }

                @Override
                public void onError(int i, String s) {
                    result.postValue(new ResultModel(false, s));
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });

        }catch (Exception e) {
            e.printStackTrace();
            userName.setValue("");
            userPassword.setValue("");
            result.setValue(new ResultModel(false, "登陆失败，请重试"));
        }

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
