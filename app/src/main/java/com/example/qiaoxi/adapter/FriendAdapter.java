package com.example.qiaoxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<String> friends;
    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView friendName ;
        public ImageView friendIcon;

        public ViewHolder(View view) {
            super(view);
            friendIcon = view.findViewById(R.id.item_friend_iv);
            friendName = view.findViewById(R.id.item_friend_Name);
        }
    }

    public void setFriends(List<String> list) {
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
        String name = friends.get(position);
        holder.friendName.setText(name);
        View item = holder.itemView;
        if (mOnFriendItemClickListener != null) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnFriendItemClickListener.onClick(holder.itemView, pos);
                }
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
