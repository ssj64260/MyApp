package com.example.lenovo.myapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;

/**
 * 欢迎界面
 */

public class GuideActivity extends BaseActivity {

    private static final long time = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        showGuide();
    }

    private void showGuide() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }
}
