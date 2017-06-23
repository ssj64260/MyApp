package com.example.lenovo.myapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxb.tools.R;
import com.cxb.tools.utils.DisplayUtil;
import com.example.lenovo.myapp.MyApplication;

/**
 * 自定义toast
 */
public class CustomToast {

    public static void toast(String content) {
        toast(content, Toast.LENGTH_SHORT);
    }

    public static void toast(String content, int duration) {
        toast(MyApplication.getInstance(), content, duration);
    }

    public static void toast(Context context, String content, int duration) {
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        TextView tv = (TextView) toastView.findViewById(R.id.toast_textview);
        tv.setText(content);
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(toastView);
        ToastMaster.showToast(toast);
    }

    public static void toastView(View view) {
        toastView(MyApplication.getInstance(), view, Toast.LENGTH_SHORT);
    }

    public static void toastView(Context context, View view, int duration) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);
        int height = DisplayUtil.getMetrics().heightPixels;
        toast.setGravity(Gravity.BOTTOM, 0, height / 2);

        ToastMaster.showToast(toast);
    }

}
