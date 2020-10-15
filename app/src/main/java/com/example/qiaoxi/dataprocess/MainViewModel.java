package com.example.qiaoxi.dataprocess;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.qiaoxi.R;
import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.datasource.ListenRepositoryData;
import com.example.qiaoxi.datasource.MsgModel;
import com.example.qiaoxi.widget.QXApplication;

import java.util.Random;

public final class MainViewModel extends BaseViewModel implements ListenRepositoryData<MsgModel> {
    public MainViewModel() {

    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().registerMessageListen(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DataSourceHelper.getInstance().unregisterMessageListen(this);
    }

    @Override
    public void sendNewModel(MsgModel msgModel) {
        NotificationManager manager = (NotificationManager) QXApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(QXApplication.getContext(), "chat")
                .setAutoCancel(true)
                .setContentTitle(msgModel.send)
                .setContentText(msgModel.content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("chat", "chat", importance);
            manager.createNotificationChannel(channel);
        }

        manager.notify(1, notification);

    }
}
