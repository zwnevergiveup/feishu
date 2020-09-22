package com.example.qiaoxi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.MsgModel;
import com.hyphenate.chat.EMClient;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<MsgModel> msgModels;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText_left;
        TextView messageText_right;
        ImageView icon_left;
        ImageView icon_right;

        private ViewHolder(View view) {
            super(view);
            messageText_left = view.findViewById(R.id.message_tv_left);
            messageText_right = view.findViewById(R.id.message_tv_right);
            icon_left = view.findViewById(R.id.icon_conversation_left);
            icon_right = view.findViewById(R.id.icon_conversation_right);
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
        holder.messageText_right.setVisibility(View.VISIBLE);
        holder.messageText_left.setVisibility(View.VISIBLE);
        holder.icon_left.setVisibility(View.VISIBLE);
        holder.icon_right.setVisibility(View.VISIBLE);
        MsgModel msgModel = msgModels.get(position);
        if (msgModel.send.equals(EMClient.getInstance().getCurrentUser())){
            holder.messageText_right.setText( msgModel.content);
            holder.messageText_left.setVisibility(View.GONE);
            holder.icon_left.setVisibility(View.GONE);
        }else {
            holder.messageText_left.setText( msgModel.content);
            holder.messageText_right.setVisibility(View.GONE);
            holder.icon_right.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return msgModels.size();
    }
}
