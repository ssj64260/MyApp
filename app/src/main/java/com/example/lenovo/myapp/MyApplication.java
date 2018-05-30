package com.example.lenovo.myapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.cxb.tools.utils.AndroidUtils;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.model.DaoMaster;
import com.example.lenovo.myapp.model.DaoSession;
import com.example.lenovo.myapp.utils.ToastMaster;
import com.orhanobut.logger.Logger;

/**
 * 全局application
 */

public class MyApplication extends Application {

    private static MyApplication INStANCE;

    public DaoSession daoSession;
    public SQLiteDatabase basedb;
    public DaoMaster.DevOpenHelper helper;
    public DaoMaster daoMaster;

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
        Logger.init(getString(R.string.app_name));//初始化Log显示的TAG

        if (isApkDebugable()) {
            ToastMaster.toast("这是debug版本");
        }

        ThreadPoolUtil.init(5);//初始化线程池最大线程数


        helper = new DaoMaster.DevOpenHelper(this, "Pokemon-db", null);
        basedb = helper.getWritableDatabase();
        daoMaster = new DaoMaster(basedb);
        daoSession = daoMaster.newSession();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 判断apk是 debug版 或 release版
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isApkDebugable() {
        return AndroidUtils.isApkDebugable(getInstance());
    }

}
