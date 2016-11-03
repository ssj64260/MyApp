package com.example.lenovo.myapp.okhttp;

import com.cxb.tools.utils.PreferencesUtil;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.utils.Constants;

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
        PreferencesUtil.setString(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL, baseUrl);
        setUrl();
    }

    public void resetBaseUrl() {
        PreferencesUtil.clearData(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL);
        setUrl();
    }

    private void setUrl() {
        if (MyApplication.isApkDebugable()) {
            baseUrl = PreferencesUtil.getString(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL, Constants.DEBUG_URL);
        } else {
            baseUrl = Constants.OFFICIAL_URL;
        }
    }

}
