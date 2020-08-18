package com.example.qiaoxi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.UserModel;

import java.util.List;

public class CompactAdapter extends RecyclerView.Adapter<CompactAdapter.ViewHolder> {
    private List<UserModel> friends;
    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView friendName ;
        public ImageView friendIcon;
        public ImageView privateFlag;
        public TextView friendGroupName;
        public View bottomGap;

        public ViewHolder(View view) {
            super(view);
            friendIcon = view.findViewById(R.id.item_friend_iv);
            friendName = view.findViewById(R.id.item_friend_Name);
            privateFlag = view.findViewById(R.id.item_private_icon);
            friendGroupName = view.findViewById(R.id.item_friend_group_name);
            bottomGap = view.findViewById(R.id.bottom_gap);
        }
    }

    public void setFriends(List<UserModel> list) {
        this.friends = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = friends.get(position);
        holder.friendName.setText(user.userId);
        if (user.privateFlag) {
            holder.privateFlag.setVisibility(View.VISIBLE);
        }
        if (user.userId.equals("二狗") || user.userId.equals("臭猪")) {
            holder.friendGroupName.setVisibility(View.VISIBLE);
        }
        if (user.userId.equals("丑娃") || user.userId.equals("淑芬")) {
            holder.bottomGap.setVisibility(View.VISIBLE);
        }
        View item = holder.itemView;
        if (mOnFriendItemClickListener != null) {
            item.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnFriendItemClickListener.onClick(holder.itemView, pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public interface onFriendItemClickListener {
        void onClick(View view, int position);
    }

    private onFriendItemClickListener mOnFriendItemClickListener;

    public void setOnFriendItemClickListener(onFriendItemClickListener listener) {
        mOnFriendItemClickListener = listener;
    }

}
