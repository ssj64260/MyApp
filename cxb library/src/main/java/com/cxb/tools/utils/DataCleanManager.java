package com.cxb.tools.utils;

/*  * 文 件 名:  DataCleanManager.java  * 描    述:  主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录  */

import android.content.Context;
import android.os.Environment;

import java.io.File;

/** * 本应用数据清除管理器 */
public class DataCleanManager {

    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) */
    public static void cleanInternalCache(Context context) {
        deleteAllFiles(context.getCacheDir());
    }

    /** * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /** * 按名字清除本应用数据库 * * @param context * @param dbName */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /** * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /** * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/files) */
    public static void cleanExternalFlies(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalFilesDir(null));
        }
    }

    /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /** * 清除本应用所有的数据 * * @param context * @param filepath */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        cleanExternalFlies(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    //删除目录下所有文件包括子目录的文件
    private static void deleteAllFiles(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(!item.isDirectory()){
                    item.delete();
                }else{
                    deleteAllFiles(item);
                }
            }
        }
    }

    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(!item.isDirectory()){
                    item.delete();
                }
            }
        }
    }

    //根据关键字删除文件
    private static void deleteFilesByDirectory(File directory,String key) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(!item.isDirectory() && item.getName().indexOf(key) != -1){
                    item.delete();
                }
            }
        }
    }
}

