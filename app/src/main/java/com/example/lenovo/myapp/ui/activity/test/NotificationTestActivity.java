package com.example.lenovo.myapp.ui.activity.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 通知
 */

public class NotificationTestActivity extends BaseActivity {

    private static final String ACTION_MUSIC_BUTTON = "action_music_button";//音乐按钮事件
    private static final String ACTION_PROGRESS = "action_progress";//进度条事件
    private static final int REQUEST_ID_MUSIC_PLAYER = 2333;//音乐播放器通知栏请求ID
    private static final int REQUEST_ID_PROGRESS = 666666;//进度条通知栏请求ID

    private static final int KEY_BUTTON_PREVIOUS = 0;//上一首
    private static final int KEY_BUTTON_PLAY = 1;//播放
    private static final int KEY_BUTTON_NEXT = 2;//下一首
    private static final int KEY_BUTTON_CLOSE = 3;//关闭

    private TextView tvCancelAll;
    private TextView tvSendCover;
    private TextView tvSendNotCover;
    private TextView tvSendPermanent;
    private TextView tvSendIntent;
    private TextView tvCustomMusic;
    private TextView tvProgress;

    private NotificationManager mNotificationManager;

    private int mNotificationId = 0;
    private boolean mProgressIsStop = false;//进度条释放停止
    private int mCurrentProgress = 0;//当前进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);

        initView();
        setData();

        IntentFilter musicIntent = new IntentFilter();
        musicIntent.addAction(ACTION_MUSIC_BUTTON);
        musicIntent.addAction(ACTION_PROGRESS);
        registerReceiver(notificationBroadcast, musicIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationBroadcast);
        if (progressThread.isAlive()) {
            progressThread.interrupt();
            mNotificationManager.cancel(REQUEST_ID_PROGRESS);
        }
    }

    private void initView() {
        tvCancelAll = (TextView) findViewById(R.id.tv_cancel_all);
        tvSendCover = (TextView) findViewById(R.id.tv_send_cover);
        tvSendNotCover = (TextView) findViewById(R.id.tv_send_not_cover);
        tvSendPermanent = (TextView) findViewById(R.id.tv_send_permanent);
        tvSendIntent = (TextView) findViewById(R.id.tv_send_intent);
        tvCustomMusic = (TextView) findViewById(R.id.tv_custom_music);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
    }

    private void setData() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        tvCancelAll.setOnClickListener(click);
        tvSendCover.setOnClickListener(click);
        tvSendNotCover.setOnClickListener(click);
        tvSendPermanent.setOnClickListener(click);
        tvSendIntent.setOnClickListener(click);
        tvCustomMusic.setOnClickListener(click);
        tvProgress.setOnClickListener(click);
    }

    //发送一条普通消息
    private void sendDefaultNotification(boolean isCover) {
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("收到了一条消息");
        if (isCover) {
            mNotificationId = 0;
        } else {
            mNotificationId++;
        }
        mBuilder.setContentText("消息内容：" + System.currentTimeMillis() + " ID:" + mNotificationId);
        mBuilder.setSmallIcon(R.mipmap.app_icon);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(mNotificationId, notification);
    }

    //发送一条常驻通知
    private void sendPermanentNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("常驻通知");
        mBuilder.setContentText("这是一条删除不掉的通知");
        mBuilder.setSmallIcon(R.mipmap.app_icon);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(0, notification);
    }

    private void sendIntentNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this);

        String packName = "com.cxb.qq";

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mBuilder.setSmallIcon(R.mipmap.app_icon);
        if (packageInfo != null) {
            mBuilder.setContentTitle("带Intent的通知");
            mBuilder.setContentText("点击后跳转到仿QQapp");
            Intent intent = packageManager.getLaunchIntentForPackage(packName);
            mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        } else {
            mBuilder.setContentTitle("带Intent的通知");
            mBuilder.setContentText("没有安装仿QQapp，点击无效");
        }

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, notification);
    }

    private void sendCustomMusicNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.include_custom_music_notification);
        remoteViews.setImageViewResource(R.id.iv_music_icon, R.mipmap.app_icon);
        remoteViews.setTextViewText(R.id.tv_music_name, "Hello,world!");
        remoteViews.setTextViewText(R.id.tv_singer_name, "BUMP OF CHICKEN");

        Intent intent = new Intent(ACTION_MUSIC_BUTTON);

        intent.putExtra(ACTION_MUSIC_BUTTON, KEY_BUTTON_PREVIOUS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_previous_song, pendingIntent);

        intent.putExtra(ACTION_MUSIC_BUTTON, KEY_BUTTON_PLAY);
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_play_song, pendingIntent);

        intent.putExtra(ACTION_MUSIC_BUTTON, KEY_BUTTON_NEXT);
        pendingIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_next_song, pendingIntent);

        intent.putExtra(ACTION_MUSIC_BUTTON, KEY_BUTTON_CLOSE);
        pendingIntent = PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_music_close, pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.app_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBuilder.setCustomContentView(remoteViews);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(REQUEST_ID_MUSIC_PLAYER, notification);
        } else {
            Notification notification = mBuilder.build();
            notification.contentView = remoteViews;
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(REQUEST_ID_MUSIC_PLAYER, notification);
        }
    }

    private void sendProgressNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.include_progress_notification);
        remoteViews.setImageViewResource(R.id.iv_app_icon, R.mipmap.app_icon);

        if (mProgressIsStop) {
            remoteViews.setTextViewText(R.id.tv_tips, "已暂停：" + mCurrentProgress + "%");
        } else {
            remoteViews.setTextViewText(R.id.tv_tips, "下载中：" + mCurrentProgress + "%");
        }

        remoteViews.setProgressBar(R.id.pb_progress, 100, mCurrentProgress, false);

        Intent intent = new Intent(ACTION_PROGRESS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ll_rootview, pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.app_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBuilder.setCustomContentView(remoteViews);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(REQUEST_ID_PROGRESS, notification);
        } else {
            Notification notification = mBuilder.build();
            notification.contentView = remoteViews;
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(REQUEST_ID_PROGRESS, notification);
        }
    }

    private Thread progressThread = new Thread(new Runnable() {
        @Override
        public void run() {
            mCurrentProgress = 0;
            try {
                while (mCurrentProgress < 100) {
                    if (!mProgressIsStop) {
                        mCurrentProgress += 1;
                        sendProgressNotification();
                    }
                    long sec = (long) (Math.random() * 300);
                    Thread.sleep(sec);
                }

                if (progressThread != null) {
                    mNotificationManager.cancel(REQUEST_ID_PROGRESS);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastMaster.toast("下载完成");
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                mNotificationManager.cancel(REQUEST_ID_PROGRESS);
            }
        }
    });

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel_all:
                    mNotificationManager.cancelAll();
                    break;
                case R.id.tv_send_cover:
                    sendDefaultNotification(true);
                    break;
                case R.id.tv_send_not_cover:
                    sendDefaultNotification(false);
                    break;
                case R.id.tv_send_permanent:
                    sendPermanentNotification();
                    break;
                case R.id.tv_send_intent:
                    sendIntentNotification();
                    break;
                case R.id.tv_custom_music:
                    sendCustomMusicNotification();
                    break;
                case R.id.tv_progress:
                    if (!progressThread.isAlive()) {
                        progressThread.start();
                    }
                    break;
            }
        }
    };

    private BroadcastReceiver notificationBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_MUSIC_BUTTON)) {
                int buttonId = intent.getIntExtra(ACTION_MUSIC_BUTTON, 0);
                switch (buttonId) {
                    case KEY_BUTTON_PREVIOUS:
                        ToastMaster.toast("上一首");
                        break;
                    case KEY_BUTTON_PLAY:
                        ToastMaster.toast("播放");
                        break;
                    case KEY_BUTTON_NEXT:
                        ToastMaster.toast("下一首");
                        break;
                    case KEY_BUTTON_CLOSE:
                        mNotificationManager.cancel(REQUEST_ID_MUSIC_PLAYER);
                        break;
                    default:
                        break;
                }
            } else if (action.equals(ACTION_PROGRESS)) {
                mProgressIsStop = !mProgressIsStop;
                sendProgressNotification();
            }
        }
    };
}
