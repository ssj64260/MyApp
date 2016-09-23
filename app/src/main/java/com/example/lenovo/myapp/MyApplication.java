package com.example.lenovo.myapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapp.utils.AndroidUtils;
import com.example.lenovo.myapp.utils.StringCheck;
import com.example.lenovo.myapp.utils.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * 全局application
 */

public class MyApplication extends Application {

    private static MyApplication INStANCE;

    public MyApplication(){
        INStANCE = this;
    }

    public static MyApplication getInstance() {
        if (INStANCE == null) {
            synchronized (MyApplication.class){
                if (INStANCE == null) {
                    INStANCE = new MyApplication();
                }
            }
        }
        return INStANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initToast();//初始化通用Toast

        Logger.init("有个APP").hideThreadInfo();//初始化Log显示的TAG

        if (isApkDebugable()) {
            ToastUtil.toast("这是debug版本");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 判断apk是 debug版 或 release版
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isApkDebugable(){
        return AndroidUtils.isApkDebugable(getInstance());
    }

    ///////////////////////////////////////////////////////////////////////////
    // 即时刷新内容toast
    ///////////////////////////////////////////////////////////////////////////
    private void initToast(){
        if (ToastMgr.builder.toastView == null || ToastMgr.builder.toast == null || ToastMgr.builder.tv == null) {
            ToastMgr.builder.init(getInstance());
        }
    }

    public enum ToastMgr {
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
