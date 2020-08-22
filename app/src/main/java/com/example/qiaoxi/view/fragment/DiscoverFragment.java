package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.BannerModel;
import com.example.qiaoxi.view.adapter.CardViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private View root;
    private Banner banner;
    private List<BannerModel> mData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_discover,container,false);
            banner = root.findViewById(R.id.discover_banner);

            setEvent();
        }
        return root;
    }

    private void setEvent() {
        mData.add(new BannerModel("https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/hot_search/mark-icon-3@1x-fdb050afea.png","来自百度的一张图片"));
        mData.add(new BannerModel("https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/hot_search/mark-icon-3@1x-fdb050afea.png","来自百度的一张图片"));
        mData.add(new BannerModel("https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/hot_search/mark-icon-3@1x-fdb050afea.png","来自百度的一张图片"));


        banner.addBannerLifecycleObserver(getActivity())
                .setAdapter(new CardViewAdapter(mData))
                .setIndicator(new CircleIndicator(getActivity()));
    }
}
