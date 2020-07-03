package com.example.qiaoxi.activity.main;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.databinding.ActivityMainBinding;
import com.example.qiaoxi.service.ForegroundService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    private int isExit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mine, R.id.navigation_compact, R.id.navigation_conversation)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemReselectedListener(item -> { });

    }

    @Override
    protected void setupView() { }

    @Override
    protected void setupDataBinding() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void setupEvent() { }

    @Override
    public void onBackPressed() {
        isExit++ ;
        if (isExit > 1) {
            finish();
            System.exit(0);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExit = 0;
            }
        }, 1000);
    }
}
