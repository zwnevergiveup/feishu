package com.example.qiaoxi.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.databinding.ActivityCompactBinding;
import com.example.qiaoxi.dataprocess.CompactViewModel;
import com.example.qiaoxi.view.activity.BaseActivity;
import com.example.qiaoxi.view.activity.CurrentConversationsActivity;
import com.example.qiaoxi.view.adapter.FriendAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;

import java.util.ArrayList;
import java.util.List;

public class CompactActivity extends BaseActivity {

    private CompactViewModel compactViewModel;
    private List<String> mContactList = new ArrayList<>();
    private RecyclerView mRecycler;


    @Override
    protected void setupDataBinding() {
        compactViewModel = ViewModelProviders.of(this).get(CompactViewModel.class);
        ActivityCompactBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_compact);
        binding.setLifecycleOwner(this);
        binding.setViewModel(compactViewModel);
        getLifecycle().addObserver(compactViewModel);
    }

    @Override
    protected void setupView() {
        mRecycler = findViewById(R.id.friend_recy);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        FriendAdapter adapter = new FriendAdapter();
        adapter.setFriends(mContactList);
        adapter.setOnFriendItemClickListener((view, position) -> {
            String str = ((TextView)view.findViewById(R.id.item_friend_Name)).getText().toString();
            Intent intent = new Intent(this, CurrentConversationsActivity.class);
            intent.putExtra("title",str);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
        mRecycler.setAdapter(adapter);

        QXToolbar toolbar = findViewById(R.id.friend_toolbar);
        toolbar.setTitleText("联系人",getResources().getColor(R.color.pure_black));
        findViewById(R.id.friend_add).setOnClickListener(v -> addContact());
    }

    @Override
    protected void setupEvent() {
        compactViewModel.getContactList().observe(this, s -> {
            mContactList.clear();
            mContactList.addAll(s);
            mRecycler.getAdapter().notifyDataSetChanged();
        });
    }


    private void addContact(){
        // 跳转到添加好友的页面
        Toast.makeText(this,"功能暂未开发，请期待",Toast.LENGTH_SHORT).show();
    }
}
