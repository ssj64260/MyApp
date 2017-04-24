package com.example.lenovo.myapp.ui.intefaces;

/**
 * BaseActivity 功能监听
 */

public interface ActivityListener {

    //键盘显示或隐藏回调
    public void onkeyboardChange(boolean isShow);

    //请求权限成功后的回调
    public void onPermissionSuccess();

}
