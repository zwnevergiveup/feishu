package com.example.qiaoxi.activity.conversation;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.adapter.MessageAdapter;
import com.example.qiaoxi.databinding.ActivityCurrentConversationBinding;
import com.example.qiaoxi.model.MsgModel;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

public final class CurrentConversationsActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private EditText editText;
    private Button btn;
    private List<MsgModel> emMessageList =new ArrayList<>();
    private String withWho;
    public static String FLAG = "UPDATE";


    protected void setupDataBinding() {
        withWho = getIntent().getStringExtra("title");
        CurrentConversationsViewModel currentConversationsViewModel = new ViewModelProvider(this,new CurrentConversationsViewModel.Factory(withWho,getApplicationContext())).get(CurrentConversationsViewModel.class);
        ActivityCurrentConversationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_current_conversation);
        binding.setLifecycleOwner(this);
        binding.setViewModel(currentConversationsViewModel);
        currentConversationsViewModel.msgModels.observe(this, new Observer<List<MsgModel>>() {
            @Override
            public void onChanged(List<MsgModel> msgModels) {
                emMessageList.clear();
                emMessageList.addAll(msgModels);
                mRecycler.getAdapter().notifyDataSetChanged();
                mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
            }
        });


        BaseActivity.ob.bind(this,new Observer<MsgModel>() {
            @Override
            public void onChanged(MsgModel msgModel) {
                if (msgModel.send.equals(withWho)) {
                    emMessageList.add(msgModel);
                    mRecycler.getAdapter().notifyDataSetChanged();
                    mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
                }
            }
        });
    }

    protected void setupView() {
        getWindow().setNavigationBarColor(getColor(R.color.rice_yellow));
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);

        editText = findViewById(R.id.current_conversation_send_text);
        btn = findViewById(R.id.current_conversation_send_btn);
    }

    @Override
    protected void setupEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                if (!s.isEmpty()) {
                    sendMessage(s,withWho);
                    editText.setText("");
                }
            }
        });
    }
    public void sendMessage(String text, String userName) {
        String me = EMClient.getInstance().getCurrentUser();
        if (me ==null || me.isEmpty()) {
            Toast.makeText(this,"请重新登录",Toast.LENGTH_LONG).show();
        }
        Log.e(TAGS,userName);
        EMMessage a = EMMessage.createTxtSendMessage(text,userName);
        MsgModel msgModel = new MsgModel(a);
        emMessageList.add(msgModel);
        mRecycler.getAdapter().notifyDataSetChanged();
        mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
        EMClient.getInstance().chatManager().sendMessage(a);
    }
}
