package com.example.qiaoxi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.conversation.CurrentConversationsActivity;
import com.example.qiaoxi.activity.main.MainActivity;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.MsgModel;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class ForegroundService extends BaseService {
    public static final int NOTICE_ID = 100;
    private AppDatabase db;
    private Vibrator mVibrator;
    private NotificationChannel notificationChannel;
    private NotificationManager notificationManager;
    private String CHANNEL_ID = "xxx";
    private String CHANNEL_NAME = "zzz";
    @Override
    public void onCreate() {
        super.onCreate();
        setMessageListen();
        setDatabase();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        setForeground();
        return START_STICKY;
    }

    private void setForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void setNotification(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this,"xxx")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.qiaoxi_icon))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent).build();
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        startForeground(1,notification);
    }

    private void setMessageListen(){
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {//not mainThread
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                EMMessage newMessage = list.get(0);
                MsgModel msgModel = new MsgModel(newMessage);
                db.msgModelDao().insertAll(msgModel);
                mVibrator.vibrate(300);
                setNotification(newMessage.getFrom(),((EMTextMessageBody)newMessage.getBody()).getMessage());

                Intent it = new Intent();
                it.setAction(CurrentConversationsActivity.FLAG);
                it.putExtra("lastMessage",msgModel);
                sendBroadcast(it);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }

    public void setDatabase() {
        db = DBHelper.getInstance().getAppDatabase(getApplicationContext(),"messageDB");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }


}
