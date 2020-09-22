package com.example.qiaoxi.view.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.ContactModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Pair<String, ContactModel>> friends;
    private Map<String, Pair<String, ContactModel>> showLetter = new HashMap<>();


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView friendName ;
        public ImageView friendIcon;
        public TextView friendGroupName;
        public View bottomGap;

        public ViewHolder(View view) {
            super(view);
            friendIcon = view.findViewById(R.id.item_friend_iv);
            friendName = view.findViewById(R.id.item_friend_Name);
            friendGroupName = view.findViewById(R.id.item_friend_group_name);
            bottomGap = view.findViewById(R.id.bottom_gap);
        }
    }

    public void setFriends(List<Pair<String, ContactModel>> list) {
        this.friends = list;
        list.forEach(pair -> {
            showLetter.putIfAbsent(pair.first,pair);
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contact = friends.get(position).second;
        holder.friendName.setText(contact.friendNickName);
        if (friends.get(position) == showLetter.get(friends.get(position).first)) {
            holder.friendGroupName.setVisibility(View.VISIBLE);
            holder.friendGroupName.setText(friends.get(position).first);
        }else {
            holder.friendGroupName.setVisibility(View.GONE);
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
