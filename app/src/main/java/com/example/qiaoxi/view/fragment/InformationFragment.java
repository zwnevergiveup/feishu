package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.BannerModel;
import com.example.qiaoxi.view.adapter.CardViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends Fragment {
    private View root;
    private Banner banner;
    private List<BannerModel> mData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_infomation,container,false);
            banner = root.findViewById(R.id.discover_information_banner);

            setEvent();
        }
        return root;
    }

    private void setEvent() {
        mData.add(new BannerModel(R.mipmap.jingyu,"越南总理和王毅委员"));


        banner.addBannerLifecycleObserver(getActivity())
                .setAdapter(new CardViewAdapter(mData))
                .setIndicator(new RoundLinesIndicator(getActivity()));
    }
}
