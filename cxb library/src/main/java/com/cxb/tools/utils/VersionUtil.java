package com.cxb.tools.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本号工具类
 *
 * 安装机制，迭代安装只能安装version name比较大的，version code不作限制
 */
public class VersionUtil {


    /**
     * 比较两个version name大小
     *
     * @param localVersion 当前app版本号
     * @param serverVersion 网络获取的版本号
     * @return true为有新版本，false为没有新版本
     */
    public static boolean isNewVersionName(String localVersion, String serverVersion) {

        if (!StringCheck.isEmpty(localVersion) && !StringCheck.isEmpty(serverVersion)) {

            if (localVersion.equals(serverVersion)) {
                return false;
            }

            try {
                String[] localVersions = localVersion.split("\\.");
                String[] serverVersions = serverVersion.split("\\.");

                for (int i = 0; i < localVersions.length; i++) {
                    if (i < serverVersions.length) {
                        int lVersion = Integer.parseInt(localVersions[i]);
                        int sVersion = Integer.parseInt(serverVersions[i]);
                        if (lVersion > sVersion) {
                            return false;
                        } else if (lVersion < sVersion) {
                            return true;
                        }
                    }
                }

                return localVersions.length < serverVersions.length;

            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    //比较两个version code
    public static boolean isNewVersionCode(int curVersion, int netVersion) {
        return curVersion != netVersion;
    }

    //获取version name
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    //获取version code
    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

}
