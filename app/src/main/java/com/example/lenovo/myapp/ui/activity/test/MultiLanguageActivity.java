package com.example.lenovo.myapp.ui.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cxb.tools.utils.LanguageUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.PokemonMainActivity;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;

import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_LANGUAGE;

/**
 * 多语言
 */

public class MultiLanguageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_language);

        initView();
        setData();

    }

    private void initView() {
        findViewById(R.id.btn_chinese).setOnClickListener(click);
        findViewById(R.id.btn_english).setOnClickListener(click);
        findViewById(R.id.btn_japanese).setOnClickListener(click);
        findViewById(R.id.btn_korean).setOnClickListener(click);
    }

    private void setData() {

    }

    private void setLanguage(Locale language) {
        LanguageUtil.setLanguage(getResources(), language.getLanguage());

        PreferencesUtil.setData(APP_SETTING, KEY_LANGUAGE, language.getLanguage());

        setResult(RESULT_OK);

        Intent intent = new Intent();
        intent.setClass(this, PokemonMainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_chinese:
                    setLanguage(Locale.CHINESE);
                    break;
                case R.id.btn_english:
                    setLanguage(Locale.ENGLISH);
                    break;
                case R.id.btn_japanese:
                    setLanguage(Locale.JAPANESE);
                    break;
                case R.id.btn_korean:
                    setLanguage(Locale.KOREAN);
                    break;
            }
        }
    };

}
