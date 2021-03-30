package com.example.qiaoxi.view.activity;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.model.ContactModel;
import com.example.qiaoxi.dataprocess.CurrentConversationsViewModel;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.helper.viewhelper.DisplayHelper;
import com.example.qiaoxi.view.adapter.MessageAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.example.qiaoxi.datasource.model.MsgModel;
import com.example.qiaoxi.widget.QXApplication;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public final class CurrentConversationsActivity extends BaseActivity<CurrentConversationsViewModel> {

    private RecyclerView mRecycler;
    private List<MsgModel> emMessageList =new ArrayList<>();
    public static String FLAG = "UPDATE";
    private ContactModel mModel;
    private EditText editText;
    private int mWindowHeight = 0;
    private ViewGroup inputVG;
    private ViewGroup current_conversation_more_operation;
    private int devHeight = 0;

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            //获取当前窗口实际的可见区域
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            int height = r.height();
            if (mWindowHeight == 0) {
                //第一次进来，记录下原始的可见窗口高度。
                mWindowHeight = height;
            } else {
//                inputVG.offsetTopAndBottom(height-mWindowHeight);
                Log.e("qiaoxi","run onGlobalLayout");
                if (devHeight != (height - mWindowHeight)) {
                    Log.e("qiaoxi","run set layout");
                    ConstraintSet set = new ConstraintSet();
                    ConstraintLayout layout = findViewById(R.id.current_conversation_top);
                    set.clone(layout);
                    set.connect(R.id.input_group,ConstraintSet.BOTTOM,R.id.current_conversation_top,ConstraintSet.BOTTOM, mWindowHeight-height - (mWindowHeight - height == 0? 0: DisplayHelper.dip2Px(QXApplication.getContext(),20)));
                    set.applyTo(layout);
                    mRecycler.scrollToPosition(emMessageList.size() - 1);
                }
            }
            devHeight = height - mWindowHeight;
        }
    };

    protected void initViews() {
        mModel = JsonHelper.getInstance().getObject(getIntent().getStringExtra("contactModel"),new TypeToken<ContactModel>(){}.getType());
        String withWho = "";
        if (mModel != null) withWho = mModel.friendName ;
        setupViewModel(CurrentConversationsViewModel.class,new CurrentConversationsViewModel.Factory(withWho),this,R.layout.activity_current_conversation);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

    protected void afterViews() {
        getWindow().setNavigationBarColor(getColor(R.color.rice_yellow));
        mRecycler = findViewById(R.id.current_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter();
        messageAdapter.setEmMessageList(emMessageList);
        mRecycler.setAdapter(messageAdapter);

        current_conversation_more_operation = findViewById(R.id.current_conversation_more_operation);

        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        if (mModel != null) {
            toolbar.setTitleText(mModel.friendNickName, getResources().getColor(R.color.pure_black), false);
        }
        editText = findViewById(R.id.current_conversation_send_text);
        inputVG = findViewById(R.id.input_group);

        mRecycler.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> Log.e("qiaoxi","recy layout changed"));

        inputVG.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> Log.e("qiaoxi","inputVG layout changed"));
    }

    @Override
    protected void setupEvent() {
        QXToolbar toolbar = findViewById(R.id.current_conversation_toolbar);
        toolbar.mLeftIcon.setOnClickListener(v -> finish());
        mViewModel.lastMessages.observe(this, msgModels -> {
            emMessageList.clear();
            emMessageList.addAll(msgModels);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
        });

        mViewModel.msgModelMutableLiveData.observe(this, msgModel -> {
            emMessageList.add(msgModel);
            mRecycler.getAdapter().notifyDataSetChanged();
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
        });

        editText = findViewById(R.id.current_conversation_send_text);
        editText.setOnFocusChangeListener((v, hasFocus) -> {
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mViewModel.sendMessage();
            }
            return false;
        });

    }

    public void moreOperationClicked(View v) {
        current_conversation_more_operation.setVisibility(current_conversation_more_operation.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (editText.hasFocus()) {
            editText.clearFocus();
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
        super.onDestroy();
    }
}
