package com.example.lenovo.myapp.ui.activity.test;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.Locale;

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

    private void setLanguage(Locale language){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = language;
        resources.updateConfiguration(config, dm);
        ToastUtil.toast("设置成功，请重启APP");
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
