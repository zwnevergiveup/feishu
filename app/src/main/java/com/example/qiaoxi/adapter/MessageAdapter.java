package com.example.qiaoxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qiaoxi.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<EMMessage> emMessageList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        private ViewHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.message_tv);
        }
    }

    public void setEmMessageList(List<EMMessage> emMessageList) {
        this.emMessageList = emMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EMMessage emMessage = emMessageList.get(position);

        holder.messageText.setText( ((EMTextMessageBody)emMessage.getBody()).getMessage());
    }

    @Override
    public int getItemCount() {
        return emMessageList.size();
    }
}
