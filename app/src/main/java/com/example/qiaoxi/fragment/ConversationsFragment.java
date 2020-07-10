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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.conversation.CurrentConversationsActivity;
import com.example.qiaoxi.adapter.ConversationAdapter;
import com.example.qiaoxi.model.ConversationModel;

import java.util.ArrayList;
import java.util.List;

public class ConversationsFragment extends Fragment {

    private RecyclerView mRecycler;
    private List<ConversationModel> mConversationModels = new ArrayList<>();
    private ConversationsViewModel conversationsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        conversationsViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conversations, container, false);
        getLifecycle().addObserver(conversationsViewModel);
        mRecycler = root.findViewById(R.id.conversations_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(root.getContext()));
        setRecycler();
        setEvent();
        return root;
    }
    public void setRecycler(){
        ConversationAdapter adapter = new ConversationAdapter();
        adapter.setConversationModels(mConversationModels);
        adapter.setOnConversationItemClickListener((view, position) -> {
            String str = ((TextView)view.findViewById(R.id.conversation_title)).getText().toString();
            Intent intent = new Intent(getActivity(), CurrentConversationsActivity.class);
            intent.putExtra("title",str);
            getActivity().startActivity(intent);
        });
        mRecycler.setAdapter(adapter);
    }

    private void setEvent() {
        conversationsViewModel.conversations.observe(getViewLifecycleOwner(), conversationModels -> {
            Log.e("qiaoxi","received models: "+ conversationModels.size());
            mConversationModels.clear();
            mConversationModels.addAll(conversationModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(0);
        });

    }

}
