package com.example.qiaoxi.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.conversation.CurrentConversationsActivity;
import com.example.qiaoxi.adapter.FriendAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

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
        adapter.setOnFriendItemClickListener(new FriendAdapter.onFriendItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String str = ((TextView)view.findViewById(R.id.item_friend_Name)).getText().toString();
                Intent intent = new Intent(getActivity(), CurrentConversationsActivity.class);
                intent.putExtra("title",str);
                getActivity().startActivity(intent);
            }
        });
        mRecycler.setAdapter(adapter);
        root.findViewById(R.id.btn_new_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
        compactViewModel.getContactList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> s) {
                mContactList.clear();
                mContactList.addAll(s);
                mRecycler.getAdapter().notifyDataSetChanged();
            }
        });

        return root;
    }

    private void addContact(){
        if (EMClient.getInstance().getCurrentUser().equals("wus6")) return;
        EMClient.getInstance().contactManager().aysncAddContact("wus6", "justForTest", new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qiaoxi","发送成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qiaoxi",s);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
