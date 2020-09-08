package com.example.qiaoxi.view.activity;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.ContactModel;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.view.adapter.MessageAdapter;
import com.example.qiaoxi.widget.QXApplication;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.example.qiaoxi.databinding.ActivityCurrentConversationBinding;
import com.example.qiaoxi.data.model.MsgModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public final class CurrentConversationsActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private List<MsgModel> emMessageList =new ArrayList<>();
    public static String FLAG = "UPDATE";
    private CurrentConversationsViewModel currentConversationsViewModel;
    private ViewGroup overAllGroup;
    private ViewGroup moreOperationGroup;
    private ContactModel mModel;


    protected void setupDataBinding() {
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mModel = JsonHelper.getInstance().getObject(getIntent().getStringExtra("contactModel"),new TypeToken<ContactModel>(){}.getType());
        String withWho = "";
        if (mModel != null) withWho = mModel.friendName;
        currentConversationsViewModel = new ViewModelProvider(this,new CurrentConversationsViewModel.Factory(withWho)).get(CurrentConversationsViewModel.class);
        ActivityCurrentConversationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_current_conversation);
        binding. setLifecycleOwner(this);
        binding.setViewModel(currentConversationsViewModel);
        getLifecycle().addObserver(currentConversationsViewModel);
    }

    protected void setupView() {
        overAllGroup = findViewById(R.id.current_conversation_overall);
        moreOperationGroup = findViewById(R.id.current_conversation_more_operation);

        getWindow().setNavigationBarColor(getColor(R.color.rice_yellow));
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);

        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        if (mModel != null) {
            toolbar.setTitleText(mModel.friendNickName, getResources().getColor(R.color.pure_black), false);
        }
    }

    @Override
    protected void setupEvent() {
        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        toolbar.mLeftIcon.setOnClickListener(v -> finish());
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
        });
    }

    public void moreOperationClicked(View view) {
        if (moreOperationGroup.getVisibility() == View.GONE) {
            overAllGroup.setBackgroundColor(getColor(R.color.pure_black));
            moreOperationGroup.setVisibility(View.VISIBLE);
        }else {
            overAllGroup.setBackgroundColor(getColor(R.color.pure_white));
            moreOperationGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (moreOperationGroup.getVisibility() == View.VISIBLE) {
            moreOperationClicked(overAllGroup);
            return;
        }
        super.onBackPressed();
    }

    public void onBackClicked(View view) {
        finish();
    }
}
