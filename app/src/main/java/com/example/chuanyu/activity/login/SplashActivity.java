package com.example.chuanyu.activity.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chuanyu.activity.BaseActivity;
import com.example.chuanyu.activity.main.MainActivity;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
        turnToLogin();
    }

    private void requestPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[0]),1);
        }
    }
    private void turnToLogin() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        finish();
    }
}
