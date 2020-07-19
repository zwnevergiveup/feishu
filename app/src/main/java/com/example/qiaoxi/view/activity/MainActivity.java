package com.example.qiaoxi.view.activity;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.ConversationModel;
import com.example.qiaoxi.databinding.ActivityMainBinding;
import com.example.qiaoxi.dataprocess.ConversationsViewModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecycler = findViewById(R.id.conversations_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        setRecycler();
        QXToolbar toolbar = findViewById(R.id.conversations_toolbar);
        toolbar.setTitleText("悄息", getResources().getColor(R.color.pure_black));
        constraintLayout = findViewById(R.id.conversations_btn_group);
        constraintLayout.bringToFront();
        FloatingActionButton btn = findViewById(R.id.conversation_more_btn);
        btn.setOnClickListener(v -> startAnimation(constraintLayout));

        findViewById(R.id.conversation_search_btn).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompactActivity.class)));
    }

    @Override
    protected void setupView() { }

    @Override
    protected void setupDataBinding() {
        conversationsViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(conversationsViewModel);
        getLifecycle().addObserver(conversationsViewModel);
    }

    @Override
    protected void setupEvent() {
        conversationsViewModel.conversations.observe(this, conversationModels -> {
            Log.e("qiaoxi","received models: "+ conversationModels.size());
            mConversationModels.clear();
            mConversationModels.addAll(conversationModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(0);
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
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;
        float radius = (float)Math.hypot(cx,cy);
        Animator animator;
        if(view.getVisibility() == View.VISIBLE) {
            animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0f);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
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
            animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, radius);
            view.setVisibility(View.VISIBLE);
            animator.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        constraintLayout.setVisibility(View.INVISIBLE);
    }
}
