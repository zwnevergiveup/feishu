package com.example.qiaoxi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.model.ConversationModel;
import com.example.qiaoxi.view.customerview.CustomerImgView;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private List<ConversationModel> models;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CustomerImgView icon;
        public TextView title;
        public TextView lastMessage;
        public TextView lastMsgTime;
        public TextView waitReadCount;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.conversation_icon);
            title = view.findViewById(R.id.conversation_title);
            lastMessage = view.findViewById(R.id.conversation_last);
            lastMsgTime = view.findViewById(R.id.last_msg_time);
            waitReadCount = view.findViewById(R.id.conversation_wait_read);
        }
    }

    public void setConversationModels(List<ConversationModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConversationModel model = models.get(position);
        holder.lastMessage.setText(model.lastMessage.content);
        holder.title.setText(model.contactMan);
        holder.lastMsgTime.setText(model.lastMessage.sendTime.toString());
        holder.waitReadCount.setText(String.valueOf(model.unread));        holder.icon.setImageResource(model.temp_profile);
        RequestOptions requestOptions = new RequestOptions().error(R.mipmap.icon_people25);
        Glide.with(holder.itemView.getContext())
                .load(model.temp_profile)
                .apply(requestOptions)
                .into(holder.icon);

        View item = holder.itemView;
        if (mOnConversationItemClickListener != null) {
            item.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnConversationItemClickListener.onClick(holder.itemView,pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface onConversationItemClickListener {
        void onClick(View view, int position);
    }


    private ConversationAdapter.onConversationItemClickListener mOnConversationItemClickListener;

    public void setOnConversationItemClickListener(ConversationAdapter.onConversationItemClickListener listener) {
        mOnConversationItemClickListener = listener;
    }
}
