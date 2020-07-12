package com.example.qiaoxi.view.activity;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.view.adapter.MessageAdapter;
import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.example.qiaoxi.databinding.ActivityCurrentConversationBinding;
import com.example.qiaoxi.data.model.MsgModel;

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
        binding. setLifecycleOwner(this);
        binding.setViewModel(currentConversationsViewModel);
        getLifecycle().addObserver(currentConversationsViewModel);
    }

    protected void setupView() {
        getWindow().setNavigationBarColor(getColor(R.color.rice_yellow));
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);

        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        toolbar.setTitleText(withWho,getResources().getColor(R.color.pure_black));
    }

    @Override
    protected void setupEvent() {
        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        toolbar.mLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        currentConversationsViewModel.lastMessages.observe(this, msgModels -> {
            emMessageList.clear();
            emMessageList.addAll(msgModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
        });

        currentConversationsViewModel.msgModelMutableLiveData.observe(this, msgModel -> {
            emMessageList.add(msgModel);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
            if (!msgModel.send.equals(QXApplication.currentUser)) {
                createConversationNotification(withWho,msgModel.content);
            }
        });
    }

    public void onBackClicked(View view) {
        finish();
    }
}
