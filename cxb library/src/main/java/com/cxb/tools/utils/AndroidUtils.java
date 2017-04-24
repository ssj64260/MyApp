package com.cxb.tools.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

public class AndroidUtils {

    //获取手机型号
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    //获取系统Android版本号
    public static String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }

    //是否debug模式
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

}
