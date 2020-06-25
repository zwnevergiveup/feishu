package com.example.qiaoxi.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.qiaoxi.service.ObserverLiveData;

public abstract class BaseActivity extends AppCompatActivity {
    public String TAGS = "qiaoxi";
    protected static ObserverLiveData ob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDataBinding();
        setupView();
        setupEvent();
    }

    protected abstract void setupView();
    protected abstract void setupDataBinding();
    protected abstract void setupEvent();
}
