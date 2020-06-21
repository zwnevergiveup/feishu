package com.example.qiaoxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qiaoxi.R;
import com.example.qiaoxi.model.MsgModel;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<MsgModel> msgModels;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        private ViewHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.message_tv);
        }
    }

    public void setEmMessageList(List<MsgModel> emMessageList) {
        this.msgModels = emMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MsgModel msgModel = msgModels.get(position);

        holder.messageText.setText( msgModel.content);
    }

    @Override
    public int getItemCount() {
        return msgModels.size();
    }
}
