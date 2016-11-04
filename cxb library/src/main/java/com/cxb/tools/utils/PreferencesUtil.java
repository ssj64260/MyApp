package com.cxb.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedPreferences工具类
 */

public class PreferencesUtil {

    private static final String FILE_NAME = "app_info";
    public static final String KEY_BASE_URL = "key_base_url";

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void clearData(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.clear();
        et.apply();
    }

}