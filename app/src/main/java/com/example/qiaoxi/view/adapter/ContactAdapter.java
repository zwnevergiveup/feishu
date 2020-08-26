package com.example.qiaoxi.view.adapter;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.UserModel;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Pair<String, UserModel>> friends;
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

    public void setFriends(List<Pair<String, UserModel>> list) {
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
        UserModel user = friends.get(position).second;
        holder.friendName.setText(user.userName);
        if (user.privateFlag) {
            holder.privateFlag.setVisibility(View.VISIBLE);
        }
        if (position == 0 || (!friends.get(position - 1).first.equals(friends.get(position).first))) {
            holder.friendGroupName.setVisibility(View.VISIBLE);
            holder.friendGroupName.setText(friends.get(position).first);
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
