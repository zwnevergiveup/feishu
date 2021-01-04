package com.example.qiaoxi.view.activity;

import android.os.Handler;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.dataprocess.MainViewModel;
import com.example.qiaoxi.datasource.ConversationModel;
import com.example.qiaoxi.dataprocess.ConversationsViewModel;
import com.example.qiaoxi.view.adapter.ViewPagerAdapter;
import com.example.qiaoxi.view.fragment.ContactFragment;
import com.example.qiaoxi.view.fragment.ConversationFragment;
import com.example.qiaoxi.view.fragment.DiscoverFragment;
import com.example.qiaoxi.view.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel> {

    private int isExit = 0;
    private RecyclerView mRecycler;
    private List<ConversationModel> mConversationModels = new ArrayList<>();
    private ConversationsViewModel conversationsViewModel;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton btn;
    private DrawerLayout mDrawerLayout;
    private ViewPager mPager;
    private BottomNavigationView mNavigation;


    @Override
    protected void setupDataBinding() {
        setupViewModel(MainViewModel.class,null);
        getLifecycle().addObserver(mViewModel);
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_main);
        mPager = findViewById(R.id.main_pager);
        mNavigation = findViewById(R.id.main_bottom_navigation);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new ConversationFragment(), new ContactFragment(), new DiscoverFragment(), new HomeFragment());
        mPager.setAdapter(adapter);
        mNavigation.setItemIconTintList(null);
        setItemIcon(0);
    }

    @Override
    protected void setupEvent() {
        mNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    mPager.setCurrentItem(0);
                    setItemIcon(0);
                    break;
                case R.id.navigation_2:
                    mPager.setCurrentItem(1);
                    setItemIcon(1);
                    break;
                case R.id.navigation_3:
                    mPager.setCurrentItem(2);
                    setItemIcon(2);
                    break;
                case R.id.navigation_4:
                    mPager.setCurrentItem(3);
                    setItemIcon(3);
                    break;
            }
            return true;
        });
        mNavigation.setOnNavigationItemReselectedListener(item -> { });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigation.getMenu().getItem(position).setChecked(true);
                setItemIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        isExit++ ;
        if (isExit > 1) {
            super.onBackPressed();
        }
        Toast.makeText(this,"再按一次退出", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> isExit = 0, 1000);
    }
    private void setItemIcon(int position) {
        mNavigation.getMenu().getItem(0).setIcon(position == 0 ? getDrawable(R.mipmap.conversations_selected): getDrawable(R.mipmap.conversations));
        mNavigation.getMenu().getItem(1).setIcon(position == 1 ? getDrawable(R.mipmap.contact_selected): getDrawable(R.mipmap.contact));
        mNavigation.getMenu().getItem(2).setIcon(position == 2 ? getDrawable(R.mipmap.faxian_selected): getDrawable(R.mipmap.faxian));
        mNavigation.getMenu().getItem(3).setIcon(position == 3 ? getDrawable(R.mipmap.wode_selected): getDrawable(R.mipmap.wode));
    }

}
