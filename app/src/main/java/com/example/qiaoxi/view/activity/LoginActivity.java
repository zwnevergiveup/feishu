package com.example.qiaoxi.view.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.LoginViewModel;
import com.example.qiaoxi.databinding.ActivityLoginBinding;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.datasource.UserModel;

public class LoginActivity extends BaseActivity<LoginViewModel> {
    private ImageView catBg;
    private ViewGroup loadingView;

    @Override
    protected void setupView() {
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.login_anim);
//        animationView.playAnimation();
//        animationView.addAnimatorListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                animationView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        TextView tv = findViewById(R.id.logon_account);
        tv.setText(Html.fromHtml(getString(R.string.logon)));
        catBg = findViewById(R.id.cat_bg);
        loadingView = findViewById(R.id.login_loading_group);
    }

    @Override
    protected void setupDataBinding() {
        setupViewModel(LoginViewModel.class,new LoginViewModel.Factory(getApplicationContext()));
       ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(mViewModel);

        mViewModel.result.observe(this, resultModel -> {
                if (resultModel.status) {
                    SPHelper.getInstance(getApplicationContext()).writeObject(mViewModel.userName.getValue(),"lastLoginName");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),resultModel.reason,Toast.LENGTH_SHORT).show();
                }
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        });
        mViewModel.userModelLiveData.observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
            }
        });
    }

    @Override
    protected void setupEvent() {
        EditText password = findViewById(R.id.input_secret);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("qiaoxi","click");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    catBg.setImageDrawable(getDrawable(R.mipmap.cat_close_eye));
                }else {
                    catBg.setImageDrawable(getDrawable(R.mipmap.cat1));
                }
            }
        });
        loadingView.setOnClickListener(v -> { });
    }



}


