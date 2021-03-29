package com.example.qiaoxi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.DiscoverViewModel;
import com.example.qiaoxi.view.adapter.ViewPagerAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.google.android.material.tabs.TabLayout;

public class DiscoverFragment extends BaseFragment<DiscoverViewModel> {

    private View root;
    private ViewPager mPager;
    private QXToolbar mToolbar;
    private TabLayout mTablayout;

    @Override
    protected void initViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        setupViewModel(DiscoverViewModel.class, inflater, container,R.layout.fragment_discover);
        View index0 = inflater.inflate(R.layout.tab_layout,null,false);
        ((TextView) index0.findViewById(R.id.tab_tv)).setText("资讯");
        View index1 = inflater.inflate(R.layout.tab_layout,null,false);
        ((TextView) index1.findViewById(R.id.tab_tv)).setText("朋友圈");

        mTablayout.getTabAt(0).setCustomView(index0);
        mTablayout.getTabAt(1).setCustomView(index1);
    }

    @Override
    protected void afterViews() {
        mToolbar = root.findViewById(R.id.information_toolbar);
        mPager = root.findViewById(R.id.information_viewpage);
        mTablayout = root.findViewById(R.id.discover_tablayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragments(new InformationFragment(), new MomentsFragment());
        adapter.setTitles(new String[]{"资讯", "朋友圈"});
        mPager.setAdapter(adapter);
        mTablayout.setupWithViewPager(mPager);
        mTablayout.setBackgroundColor(getResources().getColor(R.color.pure_white));
        mTablayout.setSelectedTabIndicatorHeight(0);

    }

    @Override
    protected void setupEvent() {

    }
}
