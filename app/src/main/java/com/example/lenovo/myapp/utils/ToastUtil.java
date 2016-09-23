package com.example.lenovo.myapp.utils;

import android.widget.Toast;

import com.example.lenovo.myapp.MyApplication;

/**
 * 即时刷新内容toast
 */
public abstract class ToastUtil {
    /**
     * 显示toast
     * @param content  内容
     * @param duration  持续时间
     */
    public static void toast(String content , int duration){
        if (content != null) {
            MyApplication.ToastMgr.builder.display(content, duration);
        }
    }
    /**
     * 显示默认持续时间为short的Toast
     * @param content  内容
     */
    public static void toast(String content){
        if (content != null) {
            MyApplication.ToastMgr.builder.display(content, Toast.LENGTH_SHORT);
        }
    }

}
