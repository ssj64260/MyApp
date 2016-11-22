package com.cxb.tools.utils;

// dp，sp，px单位转换

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DisplayUtil {

    public static DisplayMetrics getMetrics(Context context){
        return context.getResources().getDisplayMetrics();
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
}
