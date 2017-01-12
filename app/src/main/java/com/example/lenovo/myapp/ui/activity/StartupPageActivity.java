package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_FIRST_START;

/**
 * 启动页
 */

public class StartupPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startNextActivity();
                    }
                });
            }
        });

    }

    private void startNextActivity() {
        Intent main = new Intent();
        main.setClass(this, PokemonMainActivity.class);
        startActivity(main);

        boolean firstStart = PreferencesUtil.getBoolean(APP_SETTING, KEY_FIRST_START, true);
        if (firstStart) {
            PreferencesUtil.setData(APP_SETTING, KEY_FIRST_START, false);
            Intent ad = new Intent();
            ad.setClass(this, AdPagesActivity.class);
            startActivity(ad);
            overridePendingTransition(R.anim.alpha_0_to_1, R.anim.alpha_1_to_0);
        }

        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
