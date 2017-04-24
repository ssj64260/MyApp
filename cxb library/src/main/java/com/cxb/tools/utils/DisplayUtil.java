package com.cxb.tools.utils;

// dp，sp，px单位转换

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class DisplayUtil {

    public static DisplayMetrics getMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    //获取屏幕密度
    public static float getDensity(Context context) {
        return getMetrics(context).density;
    }

    //获取屏幕高度像素
    public static int getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }

    //获取屏幕宽度像素
    public static int getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    //获取屏幕密度DPI
    public static int getDensityDpi(Context context) {
        return getMetrics(context).densityDpi;
    }

    public static String getScreenSize(Context context) {
        String screenSizeText = "";

        int screenSize = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            screenSizeText = "small";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            screenSizeText = "normal";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            screenSizeText = "large";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            screenSizeText = "xlarge";
        }

        return screenSizeText;
    }

    public static String getScreenDensity(Context context) {
        String screenDensityText = "";

        int screenDensity = getDensityDpi(context);
        if (DisplayMetrics.DENSITY_LOW == screenDensity) {
            screenDensityText = "ldpi";
        } else if (DisplayMetrics.DENSITY_MEDIUM == screenDensity) {
            screenDensityText = "mdpi";
        } else if (DisplayMetrics.DENSITY_TV == screenDensity) {
            screenDensityText = "tvdpi";
        } else if (DisplayMetrics.DENSITY_HIGH == screenDensity) {
            screenDensityText = "hdpi";
        } else if (DisplayMetrics.DENSITY_280 == screenDensity) {
            screenDensityText = "280dpi";
        } else if (DisplayMetrics.DENSITY_XHIGH == screenDensity) {
            screenDensityText = "xhdpi";
        } else if (DisplayMetrics.DENSITY_360 == screenDensity) {
            screenDensityText = "360dpi";
        } else if (DisplayMetrics.DENSITY_400 == screenDensity) {
            screenDensityText = "400dpi";
        } else if (DisplayMetrics.DENSITY_420 == screenDensity) {
            screenDensityText = "420dpi";
        } else if (DisplayMetrics.DENSITY_XXHIGH == screenDensity) {
            screenDensityText = "xxhdpi";
        } else if (DisplayMetrics.DENSITY_560 == screenDensity) {
            screenDensityText = "560dpi";
        } else if (DisplayMetrics.DENSITY_XXXHIGH == screenDensity) {
            screenDensityText = "xxxhdpi";
        }
        return screenDensityText;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getMetrics(context).density + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * getMetrics(context).density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / getMetrics(context).density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * getMetrics(context).density + 0.5f);
    }

    /**
     * 将传进来的数转化为dp
     */
    public static int convertToDp(Context context, int num) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getMetrics(context));
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
