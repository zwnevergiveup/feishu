package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.view.adapter.CompactAdapter;

import java.util.ArrayList;
import java.util.List;

public class CompactFragment extends Fragment {

    private List<UserModel> compacts = new ArrayList<>();
    private RecyclerView mRecycler;
    private View root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_compact, container, false);
            mRecycler = root.findViewById(R.id.friend_recy);
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            CompactAdapter adapter = new CompactAdapter();

            compacts.add(new UserModel("二狗", new ArrayList<>(), "", true));
            compacts.add(new UserModel("三毛", new ArrayList<>(), "", false));
            compacts.add(new UserModel("赵铁柱", new ArrayList<>(), "", false));
            compacts.add(new UserModel("李大海", new ArrayList<>(), "", false));
            compacts.add(new UserModel("狗剩", new ArrayList<>(), "", false));
            compacts.add(new UserModel("铁蛋", new ArrayList<>(), "", false));
            compacts.add(new UserModel("丑娃", new ArrayList<>(), "", false));
            compacts.add(new UserModel("臭猪", new ArrayList<>(), "", true));
            compacts.add(new UserModel("淑芬", new ArrayList<>(), "", false));
            compacts.add(new UserModel("菜蛋", new ArrayList<>(), "", false));
            compacts.add(new UserModel("二愣子", new ArrayList<>(), "", false));
            compacts.add(new UserModel("李狗子", new ArrayList<>(), "", false));

            adapter.setFriends(compacts);
            mRecycler.setAdapter(adapter);
        }
        return root;
    }
}
