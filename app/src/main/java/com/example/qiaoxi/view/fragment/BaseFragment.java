package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.dataprocess.BaseViewModel;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    protected abstract void initViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract void afterViews();
    protected abstract void setupEvent();
    protected View root;
    protected V mViewModel;


    protected void setupViewModel(@NonNull Class<V> modelClass, @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull int layout) {
        root = inflater.inflate(layout,container,false);
        mViewModel = new ViewModelProvider(this).get(modelClass);
        getLifecycle().addObserver(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            initViews(inflater, container);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        afterViews();
        setupEvent();
    }

}
