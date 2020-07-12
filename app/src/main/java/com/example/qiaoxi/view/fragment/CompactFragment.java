package com.example.qiaoxi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.CompactViewModel;
import com.example.qiaoxi.view.activity.CurrentConversationsActivity;
import com.example.qiaoxi.view.adapter.FriendAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;

import java.util.ArrayList;
import java.util.List;

public class CompactFragment extends Fragment {

    private CompactViewModel compactViewModel;
    private List<String> mContactList = new ArrayList<>();
    private RecyclerView mRecycler;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        compactViewModel = ViewModelProviders.of(this).get(CompactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_compact, container, false);
        mRecycler = root.findViewById(R.id.friend_recy);
        mRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        FriendAdapter adapter = new FriendAdapter();
        adapter.setFriends(mContactList);
        adapter.setOnFriendItemClickListener((view, position) -> {
            String str = ((TextView)view.findViewById(R.id.item_friend_Name)).getText().toString();
            Intent intent = new Intent(getActivity(), CurrentConversationsActivity.class);
            intent.putExtra("title",str);
            getActivity().startActivity(intent);
        });
        mRecycler.setAdapter(adapter);


        QXToolbar toolbar = root.findViewById(R.id.friend_toolbar);
        toolbar.setTitleText("联系人",getResources().getColor(R.color.pure_black));
        root.findViewById(R.id.friend_add).setOnClickListener(v -> addContact());

        compactViewModel.getContactList().observe(getViewLifecycleOwner(), s -> {
            mContactList.clear();
            mContactList.addAll(s);
            mRecycler.getAdapter().notifyDataSetChanged();
        });

        return root;
    }

    private void addContact(){
        // 跳转到添加好友的页面
        Toast.makeText(this.getActivity(),"功能暂未开发，请期待",Toast.LENGTH_SHORT).show();
    }
}
