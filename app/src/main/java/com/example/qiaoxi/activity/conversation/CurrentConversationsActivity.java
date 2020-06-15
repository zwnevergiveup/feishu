package com.example.qiaoxi.activity.conversation;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.BaseActivity;
import com.example.qiaoxi.adapter.MessageAdapter;
import com.example.qiaoxi.databinding.ActivityCurrentConversationBinding;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

public class CurrentConversationsActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private EditText editText;
    private Button btn;
    private List<String> emMessageTextList =new ArrayList<>();
    private String withWho;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupDataBinding() {
        withWho = getIntent().getStringExtra("title");
        CurrentConversationsViewModel currentConversationsViewModel = ViewModelProviders.of(this).get(CurrentConversationsViewModel.class);
        currentConversationsViewModel.conversationName = withWho;
        currentConversationsViewModel.loopReceiveConversation();
        ActivityCurrentConversationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_current_conversation);
        binding.setLifecycleOwner(this);
        binding.setViewModel(currentConversationsViewModel);
    }

    protected void setupView() {
        ((TextView)findViewById(R.id.current_conversation_title)).setText(withWho);

        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageTextList);
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
            Toast.makeText(this,"登录过期用户",Toast.LENGTH_LONG).show();
        }
        Log.e(TAGS,userName);
        EMMessage a = EMMessage.createTxtSendMessage(text,userName);
        EMClient.getInstance().chatManager().sendMessage(a);
    }
}
