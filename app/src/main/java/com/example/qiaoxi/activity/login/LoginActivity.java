package com.example.qiaoxi.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.main.MainActivity;
import com.example.qiaoxi.databinding.ActivityLoginBinding;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.model.ResultModel;
import com.example.qiaoxi.model.UserModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends BaseActivity {

    @Override
    protected void setupView() {
        LoginViewModel loginViewModel = new ViewModelProvider(this,new LoginViewModel.Factory(getApplicationContext())).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(loginViewModel);

        TextView t = findViewById(R.id.account_name);
        loginViewModel.result.observe(this, new Observer<ResultModel>() {
            @Override
            public void onChanged(ResultModel resultModel) {
                if (resultModel.status) {
                    SPHelper.getInstance(getApplicationContext()).writeObject(resultModel.reason,"lastLoginName");
                    Log.e(TAGS,"write lastLoginName: "+ resultModel.reason);
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),resultModel.reason,Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginViewModel.userModelLiveData.observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {

            }
        });
    }

    @Override
    protected void setupDataBinding() {

    }

    @Override
    protected void setupEvent() {

    }



}


