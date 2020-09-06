package com.example.qiaoxi.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public List<String> itemNames = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView1;
        TextView textView;
        ImageView imageView2;


        public ViewHolder(View view){
            super(view);
            imageView1 = view.findViewById(R.id.item_home_iv);
            textView = view.findViewById(R.id.item_home_tv);
            imageView2 = view.findViewById(R.id.item_home_iv2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qiaoxi","send message");
                EMMessage message = EMMessage.createTxtSendMessage("lalalala","zf");
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemName = itemNames.get(position);
        holder.textView.setText(itemName);
        switch (itemName) {
            case "钱包":
                holder.imageView1.setImageResource(R.mipmap.wallet);
                break;
            case "朋友圈":
                holder.imageView1.setImageResource(R.mipmap.shexiangji);
                break;
            case "主页":
                holder.imageView1.setImageResource(R.mipmap.jilu);
                break;
            case "二维码":
                holder.imageView1.setImageResource(R.mipmap.qrcde);
                break;
            case "收藏夹":
                holder.imageView1.setImageResource(R.mipmap.shoucang);
                break;
            case "备份账号":
                holder.imageView1.setImageResource(R.mipmap.shangchuan);
                break;
            case "关于我们":
                holder.imageView1.setImageResource(R.mipmap.aboutus);
        }
    }

    @Override
    public int getItemCount() {
        return itemNames.size();
    }
}
