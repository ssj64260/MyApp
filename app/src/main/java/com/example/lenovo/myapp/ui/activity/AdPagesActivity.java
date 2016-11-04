package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;

/**
 * 欢迎界面
 */

public class AdPagesActivity extends BaseActivity {

    private int time = 5;
    private TextView tvSkip;
    private boolean isNoFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_pages);

        tvSkip = (TextView) findViewById(R.id.tv_skip);

        showGuide();
    }

    private void showGuide() {
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFinish();
            }
        });

        tvSkip.setText("跳过" + time);

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                while (!isNoFirst) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    time--;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSkip.setText("跳过" + time);
                        }
                    });

                    if (time == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toFinish();
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    private void toFinish() {
        if (!isNoFirst) {
            isNoFirst = true;
            finish();
            overridePendingTransition(R.anim.alpha_0_to_1, R.anim.alpha_1_to_0);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
