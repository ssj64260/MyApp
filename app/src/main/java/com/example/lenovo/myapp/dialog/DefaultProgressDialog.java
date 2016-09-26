package com.example.lenovo.myapp.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 自定义loading对话框
 */
public class DefaultProgressDialog {

    private ProgressDialog progressDialog;

    public DefaultProgressDialog(Context ctx) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ctx);
        }
//        progressDialog.setCancelable(MyApplication.isApkDebugable());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void setCancelable(boolean cancelable){
        progressDialog.setCancelable(cancelable);
    }

    public void setMessage(String msg){
        progressDialog.setMessage(msg);
    }

    public void showDialog(){
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
