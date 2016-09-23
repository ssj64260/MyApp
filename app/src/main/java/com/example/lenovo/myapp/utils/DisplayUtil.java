package com.example.lenovo.myapp.utils;

// dp，sp，px单位转换

public class DisplayUtil { 
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int px2dip(float scale, float pxValue) { 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int dip2px(float scale, float dipValue) { 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(float fontScale, float pxValue) { 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(float fontScale, float spValue) { 
        return (int) (spValue * fontScale + 0.5f); 
    } 
}
