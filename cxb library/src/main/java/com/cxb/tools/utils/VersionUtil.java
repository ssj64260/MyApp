package com.cxb.tools.utils;

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
     * @param curVersion 当前app版本号
     * @param netVersion 网络获取的版本号
     * @return true为有新版本，false为没有新版本
     */
    public static boolean isNewVersionName(String curVersion, String netVersion) {

        if (!StringCheck.isEmpty(curVersion) && !StringCheck.isEmpty(netVersion)) {

            if (curVersion.equals(netVersion)) {
                return false;
            }

            try {
                String[] curVersions = curVersion.split("\\.");
                String[] netVersions = netVersion.split("\\.");

                for (int i = 0; i < curVersions.length; i++) {
                    if (i < netVersions.length) {
                        int cVersion = Integer.parseInt(curVersions[i]);
                        int nVersion = Integer.parseInt(netVersions[i]);
                        if (cVersion > nVersion) {
                            return false;
                        } else if (cVersion < nVersion) {
                            return true;
                        }
                    }
                }

                return curVersions.length < netVersions.length;

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
    public static String getVersionName(PackageManager pm, String packageName) {
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    //获取version code
    public static int getVersionCode(PackageManager pm, String packageName) {
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

}
