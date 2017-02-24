package com.cxb.tools.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 多语言工具类
 */

public class LanguageUtil {

    public static void setLanguage(Resources resources, String tag) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = new Locale(tag);
        resources.updateConfiguration(config, dm);
    }

}
