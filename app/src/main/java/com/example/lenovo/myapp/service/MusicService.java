package com.example.lenovo.myapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.utils.ToastMaster;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

/**
 * 播放音乐服务
 */

public class MusicService extends Service {

    private static final int REQUEST_ID_MUSIC_PLAYER = 2333;//音乐播放器通知栏请求ID
    public static final String ACTION_MUSIC_PLAYER = "action_music_player";//音乐播放器事件

    public static final int ACTION_PLAY_MUSIC = 0;//播放音乐
    public static final int ACTION_PAUSE_MUSIC = 1;//暂停音乐
    public static final int ACTION_STOP_MUSIC = 2;//停止音乐
    public static final int ACTION_PREVIOUS_MUSIC = 3;//上一首
    public static final int ACTION_NEXT_MUSIC = 4;//下一首

    private MediaPlayer mMediaPlayer;
    private int startId;

    private NotificationManager mNotificationManager;

    private MusicBinder musicBinder = new MusicBinder();

    public class MusicBinder extends Binder {

        public void iPlayMusic() {
            playMusic();
        }

        public void iPauseMusic() {
            pauseMusic();
        }

        public void iPreviousMusic() {
            ToastMaster.toast("上一首");
        }

        public void iNextMusic() {
            ToastMaster.toast("下一首");
        }
    }

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        IntentFilter musicIntent = new IntentFilter();
        musicIntent.addAction(MusicService.ACTION_MUSIC_PLAYER);
        registerReceiver(notificationBroadcast, musicIntent);

        if (mMediaPlayer == null) {
            try {
                String path = "/storage/emulated/0/Music/01. Hello,world!.mp3";
                File file = new File(path);
                if (!file.exists()) {
                    path = "/storage/emulated/0/netease/cloudmusic/Music/Megan Nicole - Escape.mp3";
                }

                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepare();
                mMediaPlayer.setLooping(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(notificationBroadcast);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        Logger.d("Service onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int musicAction = bundle.getInt(ACTION_MUSIC_PLAYER);
                switch (musicAction) {
                    case ACTION_PLAY_MUSIC:
                        playMusic();
                        break;
                    case ACTION_PAUSE_MUSIC:
                        pauseMusic();
                        break;
                    case ACTION_STOP_MUSIC:
                        stopMusic();
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void playMusic() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            sendCustomMusicNotification(true);
        }
    }

    private void pauseMusic() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            sendCustomMusicNotification(false);
        }
    }

    private void stopMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
        mNotificationManager.cancel(REQUEST_ID_MUSIC_PLAYER);
        stopSelf(startId);
    }

    private BroadcastReceiver notificationBroadcast =   new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicService.ACTION_MUSIC_PLAYER)) {
                int musicAction = intent.getIntExtra(MusicService.ACTION_MUSIC_PLAYER, 0);
                switch (musicAction) {
                    case MusicService.ACTION_PLAY_MUSIC:
                        playMusic();
                        break;
                    case MusicService.ACTION_PAUSE_MUSIC:
                        pauseMusic();
                        break;
                    case MusicService.ACTION_STOP_MUSIC:
                        stopMusic();
                        break;
                    case MusicService.ACTION_PREVIOUS_MUSIC:
                        ToastMaster.toast("上一首");
                        break;
                    case MusicService.ACTION_NEXT_MUSIC:
                        ToastMaster.toast("下一首");
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void sendCustomMusicNotification(boolean isPlaying) {
        Notification.Builder mBuilder = new Notification.Builder(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.include_custom_music_notification);
        remoteViews.setImageViewResource(R.id.iv_music_icon, R.mipmap.app_icon);
        remoteViews.setTextViewText(R.id.tv_music_name, "Hello,world!");
        remoteViews.setTextViewText(R.id.tv_singer_name, "BUMP OF CHICKEN");

        Intent intent = new Intent(MusicService.ACTION_MUSIC_PLAYER);

        intent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_PREVIOUS_MUSIC);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_previous_song, pendingIntent);

        if (isPlaying) {
            intent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_PAUSE_MUSIC);
            pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.iv_play_song, pendingIntent);
            remoteViews.setImageViewResource(R.id.iv_play_song, R.drawable.ic_music_pause);
        } else {
            intent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_PLAY_MUSIC);
            pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.iv_play_song, pendingIntent);
            remoteViews.setImageViewResource(R.id.iv_play_song, R.drawable.ic_play);
        }

        intent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_NEXT_MUSIC);
        pendingIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_next_song, pendingIntent);

        intent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_STOP_MUSIC);
        pendingIntent = PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_music_close, pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.app_icon)
                .setTicker("音乐播放")
                .setOngoing(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBuilder.setCustomContentView(remoteViews);
            Notification notification = mBuilder.build();
            startForeground(REQUEST_ID_MUSIC_PLAYER, notification);
        } else {
            Notification notification = mBuilder.build();
            notification.contentView = remoteViews;
            startForeground(REQUEST_ID_MUSIC_PLAYER, notification);
        }
    }

}
