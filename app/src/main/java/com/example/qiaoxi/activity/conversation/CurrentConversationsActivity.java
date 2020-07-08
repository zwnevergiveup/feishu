package com.example.qiaoxi.activity.conversation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.adapter.MessageAdapter;
import com.example.qiaoxi.application.QXApplication;
import com.example.qiaoxi.databinding.ActivityCurrentConversationBinding;
import com.example.qiaoxi.model.MsgModel;

import java.util.ArrayList;
import java.util.List;

public final class CurrentConversationsActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private List<MsgModel> emMessageList =new ArrayList<>();
    private String withWho;
    public static String FLAG = "UPDATE";
    private CurrentConversationsViewModel currentConversationsViewModel;


    protected void setupDataBinding() {
        withWho = getIntent().getStringExtra("title");
        currentConversationsViewModel = new ViewModelProvider(this,new CurrentConversationsViewModel.Factory(withWho)).get(CurrentConversationsViewModel.class);
        ActivityCurrentConversationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_current_conversation);
        binding.setLifecycleOwner(this);
        binding.setViewModel(currentConversationsViewModel);

    }

    protected void setupView() {
        getWindow().setNavigationBarColor(getColor(R.color.rice_yellow));
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);
    }

    @Override
    protected void setupEvent() {
        currentConversationsViewModel.lastMessages.observe(this, msgModels -> {
            emMessageList.clear();
            emMessageList.addAll(msgModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
        });

        currentConversationsViewModel.msgModelMutableLiveData.observe(this,(Observer<MsgModel>) msgModel -> {
            emMessageList.add(msgModel);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
            if (!msgModel.send.equals(QXApplication.currentUser)) {
                createNormalNotification(withWho,msgModel.content);
            }
        });
    }
}
