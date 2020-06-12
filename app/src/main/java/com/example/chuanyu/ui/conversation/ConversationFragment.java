package com.example.chuanyu.ui.conversation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chuanyu.R;
import com.example.chuanyu.adapter.MessageAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class ConversationFragment extends Fragment {

    private ConversationViewModel conversationViewModel;
    private Disposable mDisposable;
    private String textTest;
    private RecyclerView mRecycler;
    private List<EMMessage> emMessageList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        conversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        emMessageList = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_conversation, container, false);
        mRecycler = root.findViewById(R.id.conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);

        final EditText editText = root.findViewById(R.id.send_text);
        final Button btn = root.findViewById(R.id.send_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                if (!s.isEmpty()) {
                    sendMessage(s,"wus6");
                }
            }
        });
        conversationViewModel.getMessageList().observe(getViewLifecycleOwner(), new Observer<List<EMMessage>>() {
            @Override
            public void onChanged(@Nullable List<EMMessage> list) {
                if (list != null) {
                    emMessageList.clear();
                    emMessageList.addAll(list);
                    Objects.requireNonNull(mRecycler.getAdapter()).notifyDataSetChanged();
                }
            }
        });
        return root;
    }
    public void sendMessage( String text,String userName) {
        EMMessage a = EMMessage.createTxtSendMessage(text,userName);
        EMClient.getInstance().chatManager().sendMessage(a);
//        TextView tv = findViewById(R.id.main_tv);
    }
}
