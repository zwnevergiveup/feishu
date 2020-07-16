package com.example.qiaoxi.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.qiaoxi.R;
import com.example.qiaoxi.dataprocess.MainViewModel;
import com.example.qiaoxi.databinding.ActivityMainBinding;
import com.example.qiaoxi.view.adapter.MainActivityViewPagerAdapter;
import com.example.qiaoxi.view.fragment.CompactFragment;
import com.example.qiaoxi.view.fragment.ConversationsFragment;
import com.example.qiaoxi.view.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    private int isExit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        ViewPager vp = findViewById(R.id.nav_view_page);

        MainActivityViewPagerAdapter adapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConversationsFragment());
        adapter.addFragment(new CompactFragment());
        adapter.addFragment(new MineFragment());
        vp.setAdapter(adapter);

        navView.setOnNavigationItemReselectedListener(item -> { });
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemID = item.getItemId();
            switch (itemID) {
                case R.id.navigation_conversation:
                    vp.setCurrentItem(0);
                    break;
                case R.id.navigation_compact:
                    vp.setCurrentItem(1);
                    break;
                case R.id.navigation_mine:
                    vp.setCurrentItem(2);
                    break;
            }
            return true;
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void setupView() { }

    @Override
    protected void setupDataBinding() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void setupEvent() { }

    @Override
    public void onBackPressed() {
        isExit++ ;
        if (isExit > 1) {
            finish();
            System.exit(0);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExit = 0;
            }
        }, 1000);
    }
}
