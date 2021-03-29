package com.example.qiaoxi.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.qiaoxi.R;
import com.example.qiaoxi.view.customerview.CustomerVideoView;

public class ChooseLoginOrLogonActivity extends BaseActivity{

    CustomerVideoView vv ;
    @Override
    protected void afterViews() {
        setContentView(R.layout.activity_logon_or_login);
        vv = findViewById(R.id.choose_login_logon_bg);
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.choose_bg));
        vv.setOnCompletionListener(mp -> vv.start());
        vv.start();
        TextView tv = findViewById(R.id.choose_logon);
        tv.setText(Html.fromHtml(getString(R.string.logon)));
    }

    @Override
    protected void setupEvent() {

    }

    @Override
    protected void initViews() {

    }


    public void chooseLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, Pair.create(findViewById(R.id.choose_login), "shared_login_btn"));
        // start the new activity
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        vv.start();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        vv.stopPlayback();
        super.onStop();
    }
}
