package com.example.qiaoxi.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.qiaoxi.model.MsgModel;

public class MessageBroadcast extends BroadcastReceiver {

    OnUpdateUI onUpdateUI;

    @Override
    public void onReceive(Context context, Intent intent) {
        MsgModel msgModel = intent.getParcelableExtra("lastMessage");
        onUpdateUI.updateUI(msgModel);
    }

    public void setOnUpdateUI(OnUpdateUI onUpdateUI) {
        this.onUpdateUI = onUpdateUI;
    }
}
