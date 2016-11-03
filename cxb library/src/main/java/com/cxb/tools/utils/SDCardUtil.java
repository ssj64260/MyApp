package com.cxb.tools.utils;

import android.content.Context;
import android.os.Environment;

/**
 * sd卡路径
 */
public class SDCardUtil {

    //  /storage/emulated/0
    public static String getSDCardDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    //  /data/data/<application package>/cache
    public static String getCacheDir(Context context) {
        return context.getCacheDir().getPath();
    }

    //  /data/data/<application package>/files
    public static String getFilesDir(Context context) {
        return context.getFilesDir().getPath();
    }

    //  /storage/emulated/0/Android/data/你的应用包名/cache/（APP卸载后，数据会被删除）
    public static String getExternalCacheDir(Context context) {
        return context.getExternalCacheDir().getPath();
    }

    //  /storage/emulated/0/Android/data/你的应用的包名/files/
    public static String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null).getPath();
    }

    //自动选择Flies路径，若SD卡存在并且不能移除则用SD卡存储
    public static String getAutoFilesPath(Context context){
        String filesPath;
        if (ExistSDCard() && !SDCardRemovable()) {
            filesPath = getExternalFilesDir(context);
        } else {
            filesPath = getFilesDir(context);
        }
        return filesPath;
    }

    //自动选择Cache路径，若SD卡存在并且不能移除则用SD卡存储
    public static String getAutoCachePath(Context context) {
        String cachePath;
        if (ExistSDCard() && !SDCardRemovable()) {
            cachePath = getExternalCacheDir(context);
        } else {
            cachePath = getCacheDir(context);
        }
        return cachePath;
    }

    //检查SD卡是否存在
    public static boolean ExistSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //检查SD卡是否能被移除
    public static boolean SDCardRemovable(){
        return Environment.isExternalStorageRemovable();
    }

}
