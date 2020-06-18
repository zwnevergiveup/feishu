package com.example.qiaoxi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.main.MainActivity;

public class ForegroundService extends BaseService {
    public static final int NOTICE_ID = 100;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"service create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        setForeground();
        return START_STICKY;
    }

    private void setForeground() {
        String CHANNEL_ID = "xxx";
        String CHANNEL_NAME = "zzz";
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("悄息")
                .setContentText("")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.qiaoxi_icon))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        startForeground(1,notification);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
