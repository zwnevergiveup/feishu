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
import com.example.qiaoxi.view.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View root = null;
    RecyclerView mRecycler = null;
    List<String> itemNames = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_home, container, false);
            mRecycler = root.findViewById(R.id.home_recycler);
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            HomeAdapter adapter = new HomeAdapter();
            setItemNames();
            adapter.itemNames = itemNames;
            mRecycler.setAdapter(adapter);

        }

        return root;
    }

    private void setItemNames() {
        itemNames.add("钱包");
        itemNames.add("朋友圈");
        itemNames.add("主页");
        itemNames.add("二维码");
        itemNames.add("收藏夹");
        itemNames.add("备份账号");
        itemNames.add("关于我们");

    }
}
