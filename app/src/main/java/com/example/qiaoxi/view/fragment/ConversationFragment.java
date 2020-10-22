package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.ConversationsViewModel;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.view.adapter.ConversationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment {
    private RecyclerView mRecycler;
    private List<ConversationModel> conversationModels = new ArrayList<>();
    private ConversationsViewModel conversationsViewModel;
    private View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_conversations, container, false);
            mRecycler = root.findViewById(R.id.conversations_recycler);
            ConversationAdapter adapter = new ConversationAdapter();
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecycler.setAdapter(adapter);
            adapter.setConversationModels(conversationModels);
            conversationsViewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);

            getLifecycle().addObserver(conversationsViewModel);
        }
        return root;
    }
}
