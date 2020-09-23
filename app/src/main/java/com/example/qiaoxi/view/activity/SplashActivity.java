package com.example.qiaoxi.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.qiaoxi.datasource.DataSourceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onResume() {
        super.onResume();
        if (requestPermission()){
            turnToLogin();
        }
    }

    private boolean requestPermission() {
        List<String> need = new ArrayList<>();
        List<String> permissionList = new ArrayList<>();
        need.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        need.add(Manifest.permission.FOREGROUND_SERVICE);
        need.forEach(s -> {
            if (ContextCompat.checkSelfPermission(SplashActivity.this,s) !=PackageManager.PERMISSION_GRANTED){
                permissionList.add(s);
            }
        });
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }

        if (!permissionList.isEmpty()) {
            String[] arr = new String[permissionList.size()];
            permissionList.toArray(arr);
            permissionList.forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    ActivityCompat.requestPermissions(SplashActivity.this,arr,1);
                }
            });
            return false;
        }
        return true;
    }

    private void turnToLogin() {
        if (DataSourceHelper.getInstance().isConnectedEM()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this,ChooseLoginOrLogonActivity.class));
        }
        finish();
    }
    @Override
    protected void setupView() { }

    @Override
    protected void setupDataBinding() { }

    @Override
    protected void setupEvent() { }
}
