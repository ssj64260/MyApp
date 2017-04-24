package com.example.lenovo.myapp.ui.activity.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.service.MusicService;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 服务
 */

public class ServiceTestActivity extends BaseActivity {

    private TextView tvStartService;
    private TextView tvStopService;
    private TextView tvBindService;
    private TextView tvUnbindService;

    private TextView tvPlayMusic;
    private TextView tvPauseMusic;
    private TextView tvPreviousMusic;
    private TextView tvNextMusic;

    private Intent mMusicIntent;

    private MusicService.MusicBinder mMusicBinder;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        initView();
        setData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindMusicService();
    }

    private void initView() {
        tvStartService = (TextView) findViewById(R.id.tv_start_service);
        tvStopService = (TextView) findViewById(R.id.tv_stop_service);
        tvBindService = (TextView) findViewById(R.id.tv_bind_service);
        tvUnbindService = (TextView) findViewById(R.id.tv_unbind_service);

        tvPlayMusic = (TextView) findViewById(R.id.tv_play_music);
        tvPauseMusic = (TextView) findViewById(R.id.tv_pause_music);
        tvPreviousMusic = (TextView) findViewById(R.id.tv_previous_music);
        tvNextMusic = (TextView) findViewById(R.id.tv_next_music);
    }

    private void setData() {
        mMusicIntent = new Intent(ServiceTestActivity.this, MusicService.class);
        mMusicIntent.putExtra(MusicService.ACTION_MUSIC_PLAYER, MusicService.ACTION_PLAY_MUSIC);

        tvStartService.setOnClickListener(click);
        tvStopService.setOnClickListener(click);
        tvBindService.setOnClickListener(click);
        tvUnbindService.setOnClickListener(click);

        tvPlayMusic.setOnClickListener(click);
        tvPauseMusic.setOnClickListener(click);
        tvPreviousMusic.setOnClickListener(click);
        tvNextMusic.setOnClickListener(click);
    }

    private void bindMusicService() {
        bindService(mMusicIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindMusicService() {
        if (mBound) {
            unbindService(mConnection);
            mMusicBinder = null;
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBinder = (MusicService.MusicBinder) service;
            mBound = true;
            ToastMaster.toast("绑定成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            ToastMaster.toast("与服务断开连接");
        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_start_service:
                    startService(mMusicIntent);
                    break;
                case R.id.tv_stop_service:
                    unBindMusicService();
                    stopService(mMusicIntent);
                    break;
                case R.id.tv_bind_service:
                    bindMusicService();
                    break;
                case R.id.tv_unbind_service:
                    unBindMusicService();
                    break;
                case R.id.tv_play_music:
                    if (mMusicBinder != null) {
                        mMusicBinder.iPlayMusic();
                    } else {
                        ToastMaster.toast("未绑定服务");
                    }
                    break;
                case R.id.tv_pause_music:
                    if (mMusicBinder != null) {
                        mMusicBinder.iPauseMusic();
                    } else {
                        ToastMaster.toast("未绑定服务");
                    }
                    break;
                case R.id.tv_previous_music:
                    if (mMusicBinder != null) {
                        mMusicBinder.iPreviousMusic();
                    } else {
                        ToastMaster.toast("未绑定服务");
                    }
                    break;
                case R.id.tv_next_music:
                    if (mMusicBinder != null) {
                        mMusicBinder.iNextMusic();
                    } else {
                        ToastMaster.toast("未绑定服务");
                    }
                    break;
            }
        }
    };
}
