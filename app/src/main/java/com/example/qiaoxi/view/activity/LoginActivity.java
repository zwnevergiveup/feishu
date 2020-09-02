package com.example.qiaoxi.view.activity;

import android.animation.Animator;
import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.LoginViewModel;
import com.example.qiaoxi.databinding.ActivityLoginBinding;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.example.qiaoxi.data.model.ResultModel;
import com.example.qiaoxi.data.model.UserModel;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity {
    private ImageView catBg;

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
    }

    @Override
    protected void setupDataBinding() {
        LoginViewModel loginViewModel = new ViewModelProvider(this,new LoginViewModel.Factory(getApplicationContext())).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(loginViewModel);

        loginViewModel.result.observe(this, resultModel -> {
                if (resultModel.status) {
                    SPHelper.getInstance(getApplicationContext()).writeObject(loginViewModel.userName.getValue(),"lastLoginName");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),resultModel.reason,Toast.LENGTH_SHORT).show();
                }
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        });
        loginViewModel.userModelLiveData.observe(this, new Observer<UserModel>() {
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

    }



}


