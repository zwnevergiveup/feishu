package com.example.qiaoxi.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public String TAGS = "qiaoxi";

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
