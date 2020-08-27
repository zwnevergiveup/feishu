package com.example.qiaoxi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qiaoxi.R;
import com.example.qiaoxi.view.customerview.CustomerImgView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> imgPaths = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomerImgView imgView;

        public ViewHolder(View view) {
            super(view);
            imgView = view.findViewById(R.id.item_contact_comment_pic);
        }

    }
    public void setImgPaths(List<String> list) {
        imgPaths = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_comments_pic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().error(R.mipmap.default_pic);
        Glide.with(holder.itemView.getContext())
                .load(imgPaths.get(position))
                .apply(requestOptions)
                .into(holder.imgView);

    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }
}
