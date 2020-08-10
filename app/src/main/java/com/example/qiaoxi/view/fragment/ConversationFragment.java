package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.data.model.MsgModel;
import com.example.qiaoxi.view.adapter.ConversationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment {
    private RecyclerView mRecycler;
    private List<ConversationModel> conversationModels = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_conversations,container, false);
        mRecycler = root.findViewById(R.id.conversations_recycler);
        ConversationAdapter adapter = new ConversationAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(adapter);


        for (int i = 0 ; i< 20; i ++) {
            MsgModel msg = new MsgModel();
            msg.content = "这是一条测试消息" + i;
            msg.sendTime = "昨天";
            msg.receive = "测试"+ i;
            if (i % 2 == 0) {
                conversationModels.add(new ConversationModel("zw", "测试" + i,msg, R.mipmap.chunfen,i + 1));
            }else if (i % 3 == 0) {
                conversationModels.add(new ConversationModel("zw", "测试" + i,msg, R.mipmap.dahan,i + 1));
            }else if (i % 5 == 0) {
                conversationModels.add(new ConversationModel("zw", "测试" + i,msg, R.mipmap.qiufen,i + 1));
            } else {
                conversationModels.add(new ConversationModel("zw", "测试" + i,msg, R.mipmap.xiazhi,i + 1));
            }
        }




        adapter.setConversationModels(conversationModels);

        return root;
    }


}
