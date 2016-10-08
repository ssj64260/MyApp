package com.cxb.tools.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxb.tools.R;

/**
 * 即时刷新内容toast
 */
public abstract class ToastUtil {

    public static void initToast(Context context){
        if (ToastMgr.builder.toastView == null || ToastMgr.builder.toast == null || ToastMgr.builder.tv == null) {
            ToastMgr.builder.init(context);
        }
    }

    /**
     * 显示toast
     * @param content  内容
     * @param duration  持续时间
     */
    public static void toast(String content , int duration){
        if (content != null) {
            ToastMgr.builder.display(content, duration);
        }
    }
    /**
     * 显示默认持续时间为short的Toast
     * @param content  内容
     */
    public static void toast(String content){
        if (content != null) {
            ToastMgr.builder.display(content, Toast.LENGTH_SHORT);
        }
    }

    private enum ToastMgr {
        builder;
        private View toastView;
        private TextView tv;
        private Toast toast;
        private int height;

        /**
         * 初始化Toast
         */
        public void init(Context context) {
            toastView = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            tv = (TextView) toastView.findViewById(R.id.toast_textview);
            toast = new Toast(context);
            toast.setView(toastView);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            // 屏幕高度（像素）
            height = dm.heightPixels;
        }

        /**
         * 显示Toast
         *
         * @param duration Toast持续时间
         */
        public void display(String content, int duration) {
            if (!StringCheck.isEmpty(content) && tv != null) {
                tv.setText(content);
                toast.setDuration(duration);
                toast.setGravity(Gravity.BOTTOM, 0, height / 10);
                toast.show();
            }
        }
    }

}
