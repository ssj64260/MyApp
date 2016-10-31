package com.cxb.tools.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本号工具类
 */
public class VersionUtil {

    //比较两个版本号大小
    public static boolean isNewVersion(String curVersion, String netVersion) {

        if (!StringCheck.isEmpty(curVersion) && !StringCheck.isEmpty(netVersion)) {
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

    //获取版本号
    public static String getVersionName(PackageManager pm, String packageName) {
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

}
