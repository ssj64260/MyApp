package com.example.lenovo.myapp.okhttp;

import com.cxb.tools.network.okhttp.OkHttpBaseApi;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.utils.PreferencesUtil;

import static com.example.lenovo.myapp.utils.Constants.DEBUG_URL;
import static com.example.lenovo.myapp.utils.Constants.OFFICIAL_URL;
import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_BASE_URL;

/**
 * 请求链接设置
 */

public class URLSetting {

    private volatile static URLSetting instance;
    private String baseUrl;

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
    public OkHttpBaseApi.Protocol getBaseProtocol(){
        return OkHttpBaseApi.Protocol.HTTP;
    }

    public String getBaseUrl() {
        if (MyApplication.isApkDebugable()) {
            baseUrl = PreferencesUtil.getString(APP_SETTING, KEY_BASE_URL, DEBUG_URL);
        } else {
            baseUrl = OFFICIAL_URL;
        }
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        PreferencesUtil.setData(APP_SETTING, KEY_BASE_URL, baseUrl);
    }
}
