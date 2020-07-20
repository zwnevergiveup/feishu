package com.example.qiaoxi.view.activity;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.databinding.ActivityMainBinding;
import com.example.qiaoxi.dataprocess.ConversationsViewModel;
import com.example.qiaoxi.helper.viewhelper.DisplayHelper;
import com.example.qiaoxi.view.adapter.ConversationAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private int isExit = 0;
    private RecyclerView mRecycler;
    private List<ConversationModel> mConversationModels = new ArrayList<>();
    private ConversationsViewModel conversationsViewModel;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton btn;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void setupDataBinding() {
        getWindow().setNavigationBarColor(getColor(R.color.pure_black));
        conversationsViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(conversationsViewModel);
        getLifecycle().addObserver(conversationsViewModel);
    }

    @Override
    protected void setupView() {
        mRecycler = findViewById(R.id.conversations_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        setRecycler();
        QXToolbar toolbar = findViewById(R.id.conversations_toolbar);
        toolbar.setTitleText("悄息", getResources().getColor(R.color.pure_black));
        btn = findViewById(R.id.conversation_more_btn);
        constraintLayout = findViewById(R.id.conversations_btn_group);
        mDrawerLayout = findViewById(R.id.conversations_drawer);
    }

    @Override
    protected void setupEvent() {
        btn.setOnClickListener(v -> startAnimation(constraintLayout));

        findViewById(R.id.goto_compact).setOnClickListener(v -> {
            startAnimation(constraintLayout);
            startActivity(new Intent(MainActivity.this, CompactActivity.class));
        });
        findViewById(R.id.conversation_clean_btn).setOnClickListener(v -> {
            startAnimation(constraintLayout);
        });
        findViewById(R.id.conversation_find_btn).setOnClickListener(v -> {
            startAnimation(constraintLayout);
        });

        conversationsViewModel.conversations.observe(this, conversationModels -> {
            Log.e("qiaoxi","received models: "+ conversationModels.size());
            mConversationModels.clear();
            mConversationModels.addAll(conversationModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(0);
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (constraintLayout.getVisibility() == View.VISIBLE) {
                    startAnimation(constraintLayout);
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        isExit++ ;
        if (isExit > 1) {
            finish();
            System.exit(0);
        }
        new Handler().postDelayed(() -> isExit = 0, 1000);
    }
    private void setRecycler(){
        ConversationAdapter adapter = new ConversationAdapter();
        adapter.setConversationModels(mConversationModels);
        adapter.setOnConversationItemClickListener((view, position) -> {
            String str = ((TextView)view.findViewById(R.id.conversation_title)).getText().toString();
            Intent intent = new Intent(this, CurrentConversationsActivity.class);
            intent.putExtra("title",str);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
        mRecycler.setAdapter(adapter);
    }

    private void startAnimation(View view) {
        float radius = view.getMeasuredHeight();
        Animator animator;
        if(view.getVisibility() == View.VISIBLE) {

            animator = ViewAnimationUtils.createCircularReveal(view,  (int) btn.getX() + 64, (int) btn.getY(), radius, 0f);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                    hiddenMoreClick();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        } else if (view.getVisibility() == View.INVISIBLE) {
            showMoreClick();
            animator = ViewAnimationUtils.createCircularReveal(view, (int) btn.getX() + 64, (int) btn.getY(), 0f, radius);
            view.setVisibility(View.VISIBLE);
            animator.start();
        }
    }

    private void showMoreClick() {
        RotateAnimation rotateAnimation = new RotateAnimation(0f,45f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setDuration(200);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(0);
        btn.setAnimation(rotateAnimation);
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }

    private void hiddenMoreClick() {
        RotateAnimation rotateAnimation = new RotateAnimation(45f,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setDuration(200);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(0);
        btn.setAnimation(rotateAnimation);
        btn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.main_green)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        startAnimation(constraintLayout);
    }
}
