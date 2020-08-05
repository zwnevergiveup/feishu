package com.example.qiaoxi.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.qiaoxi.R;

public class ChooseLoginOrLogonActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_logon_or_login);
    }

    @Override
    protected void setupEvent() {

    }

    @Override
    protected void setupDataBinding() {

    }


    public void chooseLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, Pair.create(findViewById(R.id.choose_login), "shared_login_btn"),Pair.create(findViewById(R.id.choose_login_logon_bg),"shared_view_bg"));
        // start the new activity
        startActivity(intent, options.toBundle());
    }
}
