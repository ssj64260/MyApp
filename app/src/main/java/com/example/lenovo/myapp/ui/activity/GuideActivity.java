package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;

/**
 * 欢迎界面
 */

public class GuideActivity extends BaseActivity {

    private int time = 3;
    private TextView tvSkip;
    private boolean isNoFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        tvSkip = (TextView) findViewById(R.id.tv_skip);

        showGuide();
    }

    private void showGuide() {
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSkip.setText("跳过" + time);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    time--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toMain();
                    }
                });
            }
        }).start();
    }

    private void toMain() {
        if (!isNoFirst) {
            isNoFirst = true;
            Intent intent = new Intent();
            intent.setClass(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
