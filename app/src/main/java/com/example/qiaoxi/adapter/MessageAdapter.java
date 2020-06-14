package com.example.qiaoxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qiaoxi.R;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<String> emMessageTextList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ViewHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.message_tv);
        }
    }

    public void setEmMessageList(List<String> emMessageList) {
        this.emMessageTextList = emMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String emMessage = emMessageTextList.get(position);
        holder.messageText.setText( emMessage);
    }

    @Override
    public int getItemCount() {
        return emMessageTextList.size();
    }
}
