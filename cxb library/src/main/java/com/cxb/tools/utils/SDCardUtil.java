package com.cxb.tools.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by Administrator on 15/9/16.
 */
public class SDCardUtil {

    private static SDCardUtil INSTANCE;

    public static SDCardUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (SDCardUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SDCardUtil();
                }
            }
        }
        return INSTANCE;
    }

    //  /storage/emulated/0
    public String getSDCardDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    //  /data/data/<application package>/cache
    public String getCacheDir(Context context) {
        return context.getCacheDir().getPath();
    }

    //  /data/data/<application package>/files
    public String getFilesDir(Context context) {
        return context.getFilesDir().getPath();
    }

    //  /storage/emulated/0/Android/data/你的应用包名/cache/（APP卸载后，数据会被删除）
    public String getExternalCacheDir(Context context) {
        return context.getExternalCacheDir().getPath();
    }

    //  /storage/emulated/0/Android/data/你的应用的包名/files/
    public String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null).getPath();
    }

    //自动选择Flies路径，若SD卡存在并且不能移除则用SD卡存储
    public String getAutoFilesPath(Context context){
        String filesPath;
        if (ExistSDCard() && !SDCardRemovable()) {
            filesPath = getExternalFilesDir(context);
        } else {
            filesPath = getFilesDir(context);
        }
        return filesPath;
    }

    //自动选择Cache路径，若SD卡存在并且不能移除则用SD卡存储
    public String getAutoCachePath(Context context) {
        String cachePath;
        if (ExistSDCard() && !SDCardRemovable()) {
            cachePath = getExternalCacheDir(context);
        } else {
            cachePath = getCacheDir(context);
        }
        return cachePath;
    }

    //检查SD卡是否存在
    public boolean ExistSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //检查SD卡是否能被移除
    public boolean SDCardRemovable(){
        return Environment.isExternalStorageRemovable();
    }

}
