package com.example.qiaoxi.view;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.helper.viewhelper.DisplayHelper;
import com.example.qiaoxi.view.activity.BaseActivity;
import com.example.qiaoxi.view.adapter.ImageAdapter;
import com.example.qiaoxi.view.customerview.QXToolbar;
import com.example.qiaoxi.view.dialog.BottomDialog;
import com.example.qiaoxi.view.dialog.ContactMoreDialog;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private QXToolbar mToolbar;
    private List<String> imgPaths = new ArrayList(){{
        add("https://ww4.sinaimg.cn/bmiddle/44ab7e85gy1gi5m761so4j220w31cqv2.jpg");
        add("https://ww4.sinaimg.cn/bmiddle/44ab7e85gy1gi5m8kalauj222o340qv5.jpg");
        add("https://ww3.sinaimg.cn/bmiddle/44ab7e85gy1gi5m7ff0kgj23344moqv5.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598553201346&di=066b0d21e47467db52df8bbb4f6075a3&imgtype=0&src=http%3A%2F%2Fimage.lelezone.com%2Fuploads%2F201701%2F7b%2F9a%2F7b9a90a741bedfcb3558b8b5cc46291c.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598553201346&di=066b0d21e47467db52df8bbb4f6075a3&imgtype=0&src=http%3A%2F%2Fimage.lelezone.com%2Fuploads%2F201701%2F7b%2F9a%2F7b9a90a741bedfcb3558b8b5cc46291c.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598553201345&di=1bf914461af79b56eecfc7b1a9b004e1&imgtype=0&src=http%3A%2F%2Fimg.kalaxing.com%2F5bc0698d720415167de67f79.jpeg");
    }};
    private UserModel model;
    @Override
    protected void setupDataBinding() {
        setContentView(R.layout.activity_contact_detail);
        String objJson = getIntent().getStringExtra("userModel");
        model = JsonHelper.getInstance().getObject(objJson,new TypeToken<UserModel>(){}.getType());
    }

    @Override
    protected void setupView() {
        mRecyclerView = findViewById(R.id.contact_detail_recy);
        mToolbar = findViewById(R.id.contact_detail_toolbar);

        LinearLayoutManager manager =  new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        ImageAdapter adapter = new ImageAdapter();
        adapter.setImgPaths(imgPaths);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = DisplayHelper.dip2Px(ContactDetailActivity.this,15);
            }
        });
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        if (model!=null) {
            mToolbar.setTitleText(model.userName,getColor(R.color.pure_black),false);
            ((TextView) findViewById(R.id.contact_detail_name)).setText("Name："+model.userName);
            ((TextView) findViewById(R.id.contact_detail_id)).setText("ID："+model.userId);


        }
    }

    @Override
    protected void setupEvent() {
        mToolbar.mRightIcon.setOnClickListener(view -> {
            ContactMoreDialog dialog = new ContactMoreDialog(this);
            dialog.show();
        });
        mToolbar.mLeftIcon.setOnClickListener(view -> {
            finish();
        });
    }


}
