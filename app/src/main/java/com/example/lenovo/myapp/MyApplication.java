package com.example.lenovo.myapp;

import android.app.Application;

import com.cxb.tools.utils.AndroidUtils;
import com.cxb.tools.utils.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * 全局application
 */

public class MyApplication extends Application {

    private static MyApplication INStANCE;

    public MyApplication() {
        INStANCE = this;
    }

    public static MyApplication getInstance() {
        if (INStANCE == null) {
            synchronized (MyApplication.class) {
                if (INStANCE == null) {
                    INStANCE = new MyApplication();
                }
            }
        }
        return INStANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initToast();//初始化通用Toast

        Logger.init(getString(R.string.app_name));//初始化Log显示的TAG

        if (isApkDebugable()) {
            ToastUtil.toast("这是debug版本");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 判断apk是 debug版 或 release版
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isApkDebugable() {
        return AndroidUtils.isApkDebugable(getInstance());
    }

    ///////////////////////////////////////////////////////////////////////////
    // 即时刷新内容toast
    ///////////////////////////////////////////////////////////////////////////
    private void initToast() {
        ToastUtil.initToast(getInstance());
    }

}
