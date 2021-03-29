package com.example.qiaoxi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.ConversationsViewModel;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.datasource.ContactModel;
import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.datasource.MsgModel;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.view.activity.CurrentConversationsActivity;
import com.example.qiaoxi.view.adapter.ConversationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends BaseFragment<ConversationsViewModel> {
    private RecyclerView mRecycler;
    private List<ConversationModel> conversationModels = new ArrayList<>();


    @Override
    protected void initViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        setupViewModel(ConversationsViewModel.class,inflater,container,R.layout.fragment_conversations);
    }

    @Override
    protected void afterViews() {
        mRecycler = root.findViewById(R.id.conversations_recycler);
        ConversationAdapter adapter = new ConversationAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(adapter);
        adapter.setConversationModels(conversationModels);
        adapter.setOnConversationItemClickListener((view, position) -> {
            startActivity(new Intent(getActivity(), CurrentConversationsActivity.class){{
                ContactModel model = new ContactModel();
                model.friendNickName = conversationModels.get(position).contactMan;
                model.friendName = conversationModels.get(position).contactMan;
                putExtra("contactModel", JsonHelper.getInstance().toJson(model));
            }});
        });
    }

    @Override
    protected void setupEvent() {
        mViewModel.conversations.observe(getActivity(), models -> {
            conversationModels.clear();
            conversationModels.addAll(models);
            mRecycler.getAdapter().notifyDataSetChanged();
        });
    }

}
