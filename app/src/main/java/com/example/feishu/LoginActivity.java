package com.example.feishu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.feishu.Base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText accountName;
    private EditText accountSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountName = findViewById(R.id.input_account);
        accountSecret = findViewById(R.id.input_secret);
        requestPermission();
    }

    private void requestPermission() {
        List<String> permissonList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
        if (!permissonList.isEmpty()) {
            ActivityCompat.requestPermissions(this,permissonList.toArray(new String[0]),1);
        }
    }

    public void logout(View view) {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e(TAGS,"logout success");
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAGS,"logout error "+ s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e(TAGS,"logouting: "+i);
            }
        });
    }

    public void loginOrRegister(View view) {
        String name = accountName.getText().toString().trim();
        String secret = accountSecret.getText().toString().trim();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(secret)){
            Toast.makeText(this,"请输入用户名和密码",Toast.LENGTH_LONG).show();
        }

        EMClient.getInstance().login(name, secret, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAGS,s);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            EMClient.getInstance().createAccount(accountName.getText().toString(),accountSecret.getText().toString());
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    loginOrRegister(findViewById(R.id.login));
//                                }
//                            });
//                        }catch (HyphenateException e) {
//                            Log.e(TAGS,e.toString());
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(LoginActivity.this,"请检查网络连接",Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    }
//                }).start();
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e(TAGS,"登陆中 " + i);
            }
        });
    }

}


