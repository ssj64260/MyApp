package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;

import java.util.concurrent.TimeUnit;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_FIRST_START;

/**
 * 启动页
 */

public class StartupPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThreadPoolUtil.getInstache().scheduled(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startNextActivity();
                    }
                });
            }
        }, 1, TimeUnit.SECONDS);
    }

    private void startNextActivity() {
        boolean firstStart = PreferencesUtil.getBoolean(APP_SETTING, KEY_FIRST_START, true);
        if (firstStart) {
            PreferencesUtil.setData(APP_SETTING, KEY_FIRST_START, false);
            startActivity(new Intent(this, AdPagesActivity.class));
            overridePendingTransition(R.anim.alpha_0_to_1, R.anim.alpha_1_to_0);
        } else {
            startActivity(new Intent(this, PokemonMainActivity.class));
        }

        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
