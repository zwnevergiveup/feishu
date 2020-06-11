package com.example.feishu.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.feishu.Base.BaseActivity;
import com.example.feishu.R;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getConversation(View view) {

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation("", EMConversation.EMConversationType.Chat,true);
//        EMMessage a = EMMessage.createTxtSendMessage("testst","wus6");
//        EMClient.getInstance().chatManager().sendMessage(a);
        List<EMMessage> list = conversation.getAllMessages();
        TextView tv = findViewById(R.id.main_tv);
    }
}
