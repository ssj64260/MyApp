package com.example.lenovo.myapp.okhttp;

import com.cxb.tools.utils.PreferencesUtil;
import com.example.lenovo.myapp.MyApplication;

/**
 * 请求链接设置
 */

public class URLSetting {

    private static final String DEBUG_URL = "itest.meishiyi.cn/index.php/";//测试链接
    private static final String OFFICIAL_URL = "i.meishiyi.cn/index.php/";//正式链接

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
        PreferencesUtil.getInstance().setString(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL, baseUrl);
        setUrl();
    }

    public void resetBaseUrl() {
        PreferencesUtil.getInstance().clearData(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL);
        setUrl();
    }

    private void setUrl(){
        if (MyApplication.isApkDebugable()) {
            baseUrl = PreferencesUtil.getInstance().getString(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL, DEBUG_URL);
        } else {
            baseUrl = OFFICIAL_URL;
        }
    }

}
