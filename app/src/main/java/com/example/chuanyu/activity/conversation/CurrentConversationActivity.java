package com.example.chuanyu.activity.conversation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chuanyu.R;
import com.example.chuanyu.activity.BaseActivity;
import com.example.chuanyu.adapter.MessageAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CurrentConversationActivity extends BaseActivity {

    private CurrentConversationsViewModel currentConversationsViewModel;
    private RecyclerView mRecycler;
    private List<String> emMessageTextList;
    private EMMessage lastMessage;
    private EMConversation conversation;
    public Disposable mDisposable;
    private EMMessage emMessage;
    private String withWho;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        withWho = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_current_conversation);
        ((TextView)findViewById(R.id.current_conversation_title)).setText(withWho);
        currentConversationsViewModel = ViewModelProviders.of(this).get(CurrentConversationsViewModel.class);
        emMessageTextList = new ArrayList<>();
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageTextList);
        mRecycler.setAdapter(messageAdapter);

        final EditText editText = findViewById(R.id.current_conversation_send_text);
        final Button btn = findViewById(R.id.current_conversation_send_btn);
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
        observeGetChatDataThread();
//        currentConversationsViewModel.getLastMessageText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String str) {
//                if (str != null) {
//                    emMessageTextList.add(str);
//                    Objects.requireNonNull(mRecycler.getAdapter()).notifyDataSetChanged();
//                }
//            }
//        });
    }

    private void observeGetChatDataThread() {
        conversation = EMClient.getInstance().chatManager().getConversation(withWho, EMConversation.EMConversationType.Chat,true);
        mDisposable = Flowable.interval(3, 1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                emMessage = conversation.getLastMessage();
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (emMessage != null && (!emMessage.getFrom().equals( EMClient.getInstance().getCurrentUser()))) {
                            if (lastMessage == null) {
                                lastMessage = emMessage;
                                EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
                                emMessageTextList.add(textBody.getMessage());
                                mRecycler.getAdapter().notifyDataSetChanged();
                            }else {
                                if (emMessage != null  && (!emMessage.getMsgId().equals(lastMessage.getMsgId()))) {
                                    lastMessage = emMessage;
                                    EMTextMessageBody textBody = (EMTextMessageBody) emMessage.getBody();
                                    emMessageTextList.add(textBody.getMessage());
                                    mRecycler.getAdapter().notifyDataSetChanged();
                                }
                            }
                        }

                    }
                });
    }

    public void sendMessage( String text,String userName) {
        Log.e(TAGS,userName);
        EMMessage a = EMMessage.createTxtSendMessage(text,userName);
        EMClient.getInstance().chatManager().sendMessage(a);
//        TextView tv = findViewById(R.id.main_tv);
    }
}
