package com.example.qiaoxi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.MineViewModel;
import com.example.qiaoxi.view.activity.LoginActivity;
import com.example.qiaoxi.view.activity.TestActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Objects;

public class MineFragment extends Fragment {

    private MineViewModel mineViewModel;
    private String TAGS = "qiaoxi";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mineViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mineViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        root.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return root;
    }


    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e(TAGS,"logout success");
                getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
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
}
