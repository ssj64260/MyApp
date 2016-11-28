package com.example.lenovo.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lenovo.myapp.MyApplication;

/**
 * sharedPreferences工具类
 */

public class PreferencesUtil {

    //公用部分
    public static final String APP_SETTING = "app_setting";//app设置
    public static final String USER_INFO = "user_info";//用户信息
    public static final String KEY_BASE_URL = "base_url";//接口链接基础部分

    public static void setData(String fileName, String key, Object object) {
        String obj = String.valueOf(object);
        if (object instanceof String) {
            setString(fileName, key, obj);
        } else if (object instanceof Boolean) {
            setBoolean(fileName, key, Boolean.valueOf(obj));
        } else if (object instanceof Integer) {
            setInteger(fileName, key, Integer.valueOf(obj));
        } else if (object instanceof Long) {
            setLong(fileName, key, Long.valueOf(obj));
        } else if (object instanceof Float) {
            setFloat(fileName, key, Float.valueOf(obj));
        }
    }

    private static void setString(String fileName, String key, String value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.apply();
    }

    private static void setBoolean(String fileName, String key, boolean value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean(key, value);
        et.apply();
    }

    private static void setInteger(String fileName, String key, int value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.apply();
    }

    private static void setLong(String fileName, String key, long value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putLong(key, value);
        et.apply();
    }

    private static void setFloat(String fileName, String key, float value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putFloat(key, value);
        et.apply();
    }

    public static String getString(String fileName, String key, String defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static boolean getBoolean(String fileName, String key, boolean defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static int getInteger(String fileName, String key, int defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static long getLong(String fileName, String key, long defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static float getFloat(String fileName, String key, float defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static void clearAll(String fileName) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
