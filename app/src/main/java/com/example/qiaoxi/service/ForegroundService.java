package com.example.qiaoxi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.qiaoxi.R;
import com.example.qiaoxi.activity.main.MainActivity;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.model.MsgModel;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

public class ForegroundService extends BaseService implements ObserverLiveData {
    public static final int NOTICE_ID = 100;
    private AppDatabase db;
    private Vibrator mVibrator;
    private MutableLiveData<MsgModel> msgModelLiveData = new MutableLiveData<>();
    private Handler mHandler = new Handler();
    @Override
    public void onCreate() {
        super.onCreate();
        setMessageListen();
        setDatabase();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setForeground();
    }

    private void setForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "xxx";
            String CHANNEL_NAME = "zzz";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        msgModelLiveData.setValue(msgModel);
                    }
                });
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
        db = DBHelper.getInstance().getAppDatabase(getApplicationContext(),"QX_DB");
    }

    public class HandleBinder extends Binder {
        public Service getService(){
            return ForegroundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new HandleBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public void bind(AppCompatActivity activity, Observer observer) {
        msgModelLiveData.observe(activity , observer);
    }
}
