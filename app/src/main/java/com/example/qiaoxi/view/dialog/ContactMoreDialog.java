package com.example.qiaoxi.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.qiaoxi.R;

public class ContactMoreDialog extends BottomDialog {

    public ContactMoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int contentView() {
        return R.layout.dialog_friend_detail;
    }
}
