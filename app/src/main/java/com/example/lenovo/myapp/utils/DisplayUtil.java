package com.example.lenovo.myapp.utils;

// dp，sp，px单位转换

import com.example.lenovo.myapp.MyApplication;

public class DisplayUtil {

    private static DisplayUtil INSTANCE;

    private static float scale;

    private DisplayUtil(){

    }

    public static DisplayUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (DisplayUtil.class){
                if (INSTANCE == null) {
                    INSTANCE = new DisplayUtil();
                    scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public int px2sp(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        return (int) (spValue * scale + 0.5f);
    } 
}
