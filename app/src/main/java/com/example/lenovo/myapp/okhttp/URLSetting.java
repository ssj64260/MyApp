package com.example.lenovo.myapp.okhttp;

import com.example.lenovo.myapp.utils.PreferencesUtil;
import com.example.lenovo.myapp.MyApplication;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_BASE_URL;
import static com.example.lenovo.myapp.utils.Constants.DEBUG_URL;
import static com.example.lenovo.myapp.utils.Constants.OFFICIAL_URL;

/**
 * 请求链接设置
 */

public class URLSetting {

    private volatile static URLSetting instance;
    private String baseUrl;

    private URLSetting() {
        setUrl();
    }

    public static URLSetting getInstance() {
        if (instance == null) {
            synchronized (URLSetting.class) {
                if (instance == null) {
                    instance = new URLSetting();
                }
            }
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        PreferencesUtil.setData(APP_SETTING, KEY_BASE_URL, baseUrl);
        setUrl();
    }

    public void setUrl() {
        if (MyApplication.isApkDebugable()) {
            baseUrl = PreferencesUtil.getString(APP_SETTING, KEY_BASE_URL, DEBUG_URL);
        } else {
            baseUrl = OFFICIAL_URL;
        }
    }

}
