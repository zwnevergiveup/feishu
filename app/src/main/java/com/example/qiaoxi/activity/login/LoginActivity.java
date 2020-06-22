package com.example.qiaoxi.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.main.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends BaseActivity {

    private EditText accountName;
    private EditText accountSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountName = findViewById(R.id.input_account);
        accountSecret = findViewById(R.id.input_secret);
    }
    @Override
    protected void setupView() {

    }

    @Override
    protected void setupDataBinding() {

    }

    @Override
    protected void setupEvent() {

    }

    public void loginOrRegister(View view) {
        String name = accountName.getText().toString().trim();
        String secret = accountSecret.getText().toString().trim();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(secret)){
            Toast.makeText(this,getText(R.string.input_name_secret),Toast.LENGTH_LONG).show();
        }
//         try {
//             EMClient.getInstance().kickAllDevices(name, secret);
//         }catch (HyphenateException ex) {
//             Log.e(TAGS,ex.toString());
//         }
        EMClient.getInstance().login(name, secret, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAGS,s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e(TAGS,"登陆中 " + i);
            }
        });
    }

}


