package com.example.lenovo.myapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cxb.tools.utils.AppManager;
import com.cxb.tools.utils.DisplayUtil;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.ui.activity.ActivityListener;
import com.example.lenovo.myapp.ui.dialog.DefaultAlertDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类
 */

public class BaseActivity extends Activity implements ActivityListener {

    protected String[] permissions;
    protected String[] refuseTips;

    private InputMethodManager manager;

    private boolean curIsShow = false;

    private DefaultAlertDialog permissionDialog;//获取权限对话框

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
        if (permissionDialog != null) {
            permissionDialog.dismissDialog();
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

    //隐藏虚拟按键，并且全屏
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //键盘显示隐藏回调
    protected void setOnKeyboardChangeListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    //layout改变监听
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

            int screenHeight = DisplayUtil.getScreenHeight(MyApplication.getInstance());
            int heightDifference = screenHeight - (r.bottom - r.top);

            boolean isShow = heightDifference > screenHeight / 3;

            if (((!curIsShow && isShow) || curIsShow && !isShow)) {
                onkeyboardChange(isShow);
                curIsShow = isShow;
            }
        }
    };

    protected void setPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPermissionSuccess();
        } else {
            List<String> pTemp = new ArrayList<>();
            List<String> tTemp = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    pTemp.add(permission);
                    tTemp.add(refuseTips[i]);
                }
            }

            permissions = pTemp.toArray(new String[pTemp.size()]);
            refuseTips = tTemp.toArray(new String[tTemp.size()]);

            requestPermissions(0);
        }
    }

    private void requestPermissions(int index) {
        if (permissions.length > 0 && index >= 0 && index < permissions.length) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[index]}, index);
        } else if (permissions.length == 0) {
            onPermissionSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] p, int[] grantResults) {
        if (grantResults != null && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                permissionDialog = new DefaultAlertDialog(this);
                permissionDialog.setTitle("权限申请");
                permissionDialog.setMessage(refuseTips[requestCode]);
                permissionDialog.setCancelButton("取消");
                permissionDialog.setConfirmButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppManager.showInstalledAppDetails(BaseActivity.this, getPackageName());
                    }
                });
                permissionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                permissionDialog.showDialog();
            } else {
                requestCode++;
                if (requestCode == permissions.length) {
                    onPermissionSuccess();
                } else {
                    requestPermissions(requestCode);
                }
            }
        }
    }

    @Override
    public void onkeyboardChange(boolean isShow) {
        //键盘显示隐藏后的操作
    }

    @Override
    public void onPermissionSuccess() {
        //请求权限成功后的操作
    }
}
