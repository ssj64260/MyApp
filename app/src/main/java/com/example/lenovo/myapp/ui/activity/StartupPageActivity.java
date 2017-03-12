package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.FileUtil;
import com.cxb.tools.utils.LanguageUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_FIRST_START;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_LANGUAGE;

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

                String language = PreferencesUtil.getString(APP_SETTING, KEY_LANGUAGE, "");
                if (!StringCheck.isEmpty(language)) {
                    LanguageUtil.setLanguage(getResources(), language);
                }

                File txtFile = new File(SDCardUtil.getFilesDir(StartupPageActivity.this) + File.separator + "txt", "property.txt");

                if (!txtFile.exists()) {
                    InputStream is = AssetsUtil.getInputStream(StartupPageActivity.this, "property.txt");
                    FileUtil.copyFile(is, txtFile.getAbsolutePath());
                }

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
