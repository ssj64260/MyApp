package com.example.lenovo.myapp.ui.activity.test;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 系统相关
 */

public class SystemRelatedActivity extends BaseActivity {

    private static final int REQUEST_CODE = 1000;//请求settings权限

    private ImageView ivBack;

    private Button btnSwitch;
    private TextView tvScreen;
    private SeekBar sbScreen;

    private TextView tvRing;
    private SeekBar sbRing;

    private TextView tvMusic;
    private SeekBar sbMusic;

    private TextView tvAlarm;
    private SeekBar sbAlarm;

    private TextView tvVoiceCall;
    private SeekBar sbVoiceCall;

    private TextView tvSystem;
    private SeekBar sbSystem;

    private TextView tvNotification;
    private SeekBar sbNotification;

    private boolean autoBrightness = false;//是否开启亮度自动调节
    private int screenMaxBrightness = 255;

    private AudioManager audioManager;

    private int ringMaxVolume = 0;
    private int musicMaxVolume = 0;
    private int alarmMaxVolume = 0;
    private int voiceMaxVolume = 0;
    private int systemMaxVolume = 0;
    private int notifiMaxVolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_related);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
                return;
            }
        }

        initView();
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                    initView();
                    setData();
                } else {
                    ToastMaster.toast(getString(R.string.toast_get_settings_permission_failure));
                    finish();
                }
            }
        }
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnSwitch = (Button) findViewById(R.id.btn_switch);
        tvScreen = (TextView) findViewById(R.id.tv_screen);
        sbScreen = (SeekBar) findViewById(R.id.sb_screen);
        tvRing = (TextView) findViewById(R.id.tv_ring);
        sbRing = (SeekBar) findViewById(R.id.sb_ring);
        tvMusic = (TextView) findViewById(R.id.tv_music);
        sbMusic = (SeekBar) findViewById(R.id.sb_music);
        tvAlarm = (TextView) findViewById(R.id.tv_alarm);
        sbAlarm = (SeekBar) findViewById(R.id.sb_alarm);
        tvVoiceCall = (TextView) findViewById(R.id.tv_voice_call);
        sbVoiceCall = (SeekBar) findViewById(R.id.sb_voice_call);
        tvSystem = (TextView) findViewById(R.id.tv_system);
        sbSystem = (SeekBar) findViewById(R.id.sb_system);
        tvNotification = (TextView) findViewById(R.id.tv_notification);
        sbNotification = (SeekBar) findViewById(R.id.sb_notification);
    }

    private void setData() {
        ivBack.setOnClickListener(click);
        btnSwitch.setOnClickListener(click);

        int screenCurBrightness = 0;
        try {
            screenCurBrightness = android.provider.Settings.System.getInt(
                    getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            autoBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            e.printStackTrace();
        }

        sbScreen.setMax(screenMaxBrightness);
        sbScreen.setProgress(screenCurBrightness);
        sbScreen.setOnSeekBarChangeListener(barChange);

        btnSwitch.setText(autoBrightness ? getString(R.string.btn_auto_brightness_off) : getString(R.string.btn_auto_brightness_on));
        tvScreen.setText(String.format(getString(R.string.text_screen_brightness), screenCurBrightness, screenMaxBrightness));

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ringMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        musicMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        alarmMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        voiceMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        systemMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        notifiMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);

        int ringCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int musicCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int alarmCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int voiceCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        int systemCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        int notifiCurVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

        sbRing.setMax(ringMaxVolume);
        sbMusic.setMax(musicMaxVolume);
        sbAlarm.setMax(alarmMaxVolume);
        sbVoiceCall.setMax(voiceMaxVolume);
        sbSystem.setMax(systemMaxVolume);
        sbNotification.setMax(notifiMaxVolume);

        sbRing.setProgress(ringCurVolume);
        sbMusic.setProgress(musicCurVolume);
        sbAlarm.setProgress(alarmCurVolume);
        sbVoiceCall.setProgress(voiceCurVolume);
        sbSystem.setProgress(systemCurVolume);
        sbNotification.setProgress(notifiCurVolume);

        sbRing.setOnSeekBarChangeListener(barChange);
        sbMusic.setOnSeekBarChangeListener(barChange);
        sbAlarm.setOnSeekBarChangeListener(barChange);
        sbVoiceCall.setOnSeekBarChangeListener(barChange);
        sbSystem.setOnSeekBarChangeListener(barChange);
        sbNotification.setOnSeekBarChangeListener(barChange);

        tvRing.setText(String.format(getString(R.string.text_ring_volume), ringCurVolume, ringMaxVolume));
        tvMusic.setText(String.format(getString(R.string.text_music_volume), musicCurVolume, musicMaxVolume));
        tvAlarm.setText(String.format(getString(R.string.text_alarm_volume), alarmCurVolume, alarmMaxVolume));
        tvVoiceCall.setText(String.format(getString(R.string.text_voice_call_volume), voiceCurVolume, voiceMaxVolume));
        tvSystem.setText(String.format(getString(R.string.text_system_volume), systemCurVolume, systemMaxVolume));
        tvNotification.setText(String.format(getString(R.string.text_notification_volume), notifiCurVolume, notifiMaxVolume));

    }

    private void offAuto() {
        if (autoBrightness) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } else {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
        autoBrightness = !autoBrightness;
        btnSwitch.setText(autoBrightness ? getString(R.string.btn_auto_brightness_off) : getString(R.string.btn_auto_brightness_on));
    }

    private void setBrightness(int brightness) {
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        autoBrightness = false;
        btnSwitch.setText(getString(R.string.btn_auto_brightness_on));

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = (float) brightness * (1f / 255f);
        getWindow().setAttributes(lp);
    }

    private SeekBar.OnSeekBarChangeListener barChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.sb_screen:
                    setBrightness(progress);
                    tvScreen.setText(String.format(getString(R.string.text_screen_brightness), progress, screenMaxBrightness));
                    break;
                case R.id.sb_ring:
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvRing.setText(String.format(getString(R.string.text_ring_volume), progress, ringMaxVolume));
                    break;
                case R.id.sb_music:
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvMusic.setText(String.format(getString(R.string.text_music_volume), progress, musicMaxVolume));
                    break;
                case R.id.sb_alarm:
                    audioManager.setStreamVolume(AudioManager.STREAM_ALARM, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvAlarm.setText(String.format(getString(R.string.text_alarm_volume), progress, alarmMaxVolume));
                    break;
                case R.id.sb_voice_call:
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvVoiceCall.setText(String.format(getString(R.string.text_voice_call_volume), progress, voiceMaxVolume));
                    break;
                case R.id.sb_system:
                    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvSystem.setText(String.format(getString(R.string.text_system_volume), progress, systemMaxVolume));
                    break;
                case R.id.sb_notification:
                    audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, AudioManager.FLAG_PLAY_SOUND);
                    tvNotification.setText(String.format(getString(R.string.text_notification_volume), progress, notifiMaxVolume));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_switch:
                    offAuto();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            ToastMaster.toast(getString(R.string.toast_key_code_volume_down));
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            ToastMaster.toast(getString(R.string.toast_key_code_volume_up));
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            ToastMaster.toast(getString(R.string.toast_key_code_back));
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
