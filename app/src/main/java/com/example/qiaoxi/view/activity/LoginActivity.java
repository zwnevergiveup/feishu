package com.example.qiaoxi.view.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.LoginViewModel;
import com.example.qiaoxi.databinding.ActivityLoginBinding;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.data.model.ResultModel;
import com.example.qiaoxi.data.model.UserModel;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity {

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupDataBinding() {
        LoginViewModel loginViewModel = new ViewModelProvider(this,new LoginViewModel.Factory(getApplicationContext())).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(loginViewModel);

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
    protected void setupEvent() {

    }



}


