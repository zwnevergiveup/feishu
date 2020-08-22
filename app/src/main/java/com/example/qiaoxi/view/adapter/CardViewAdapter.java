package com.example.qiaoxi.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.BannerModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class CardViewAdapter extends BannerAdapter<BannerModel, CardViewAdapter.BannerViewHolder> {

    private Context mContext;

    public CardViewAdapter(List<BannerModel> mData) {
        super(mData);
    }
    class BannerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public BannerViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.discover_banner_iv);
            this.textView = view.findViewById(R.id.discover_banner_tv);
        }
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner,parent,false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerModel data, int position, int size) {
        Glide.with(mContext)
                .load(data.imgPath)
                .into(holder.imageView);
        holder.textView.setText(data.abstractContent);
    }
}
