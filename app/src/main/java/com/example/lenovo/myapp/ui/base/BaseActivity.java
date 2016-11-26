package com.example.lenovo.myapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cxb.tools.utils.DisplayUtil;
import com.example.lenovo.myapp.MyApplication;
import com.orhanobut.logger.Logger;

/**
 * 基类
 */

public class BaseActivity extends Activity {

    private InputMethodManager manager;

    private OnKeyboardChangeListener mOnKeyboardChangeListener;

    private boolean curIsShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.i(this.getLocalClassName());

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setStatusBar();
    }

    @Override
    protected void onDestroy() {
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }
        super.onDestroy();
    }

    //沉浸式
    private void setStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 导航栏透明
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        window.getDecorView().setFitsSystemWindows(true);
    }

    //隐藏键盘
    protected void hideKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //键盘显示隐藏回调
    protected void setOnKeyboardChangeListener(OnKeyboardChangeListener mOnKeyboardChangeListener) {
        this.mOnKeyboardChangeListener = mOnKeyboardChangeListener;
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    protected interface OnKeyboardChangeListener {
        public void onkeyboardChangelistener(boolean isShow);
    }

    //layout改变监听
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

            int screenHeight = DisplayUtil.getScreenHeight(MyApplication.getInstance());
            int heightDifference = screenHeight - (r.bottom - r.top);

//            Log.d("有个", screenHeight + "-" + (r.bottom - r.top) + "=" + heightDifference);

            boolean isShow = heightDifference > screenHeight / 3;

            if (mOnKeyboardChangeListener != null && ((!curIsShow && isShow) || curIsShow && !isShow)) {
                mOnKeyboardChangeListener.onkeyboardChangelistener(isShow);
                curIsShow = isShow;
            }
        }
    };
}
