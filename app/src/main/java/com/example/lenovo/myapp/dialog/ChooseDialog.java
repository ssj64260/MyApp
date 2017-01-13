package com.example.lenovo.myapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cxb.tools.utils.DisplayUtil;
import com.example.lenovo.myapp.R;

/**
 * 05-我的-02个人资料_01修改头像
 * 05-我的-02个人资料-03修改性别_01选择性别
 */
public class ChooseDialog {

    private AlertDialog dialog;
    private Window window;
    private int screenWidth;

    private View outsize;
    private TextView btnFirst;
    private TextView btnSecond;
    private TextView btnCancel;

    private OnCancelListener mOnCancelListener;
    private OnFirstButtonListener mOnFirstButtonListener;
    private OnSecondButtonListener mOnSecondButtonListener;

    private boolean finish = false;

    private boolean backCanDismiss = true;
    private boolean cancelCanDismiss = true;
    private boolean confirmCanDismiss = true;

    public ChooseDialog(final Context context) {

        screenWidth = DisplayUtil.getScreenWidth(context);

        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        setView();

        setListener(context);

    }

    private void setView() {
        window = dialog.getWindow();
        window.setContentView(R.layout.dialog_choose);
//        window.setWindowAnimations(R.style.dialoganimstyle);  //添加动画
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置不被虚拟键盘遮挡
        window.setBackgroundDrawableResource(R.color.transparent);//设置对话框以外的背景颜色

        //设置对话框内容填充整个窗口
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = screenWidth;
        window.setAttributes(lp);

        outsize = window.findViewById(R.id.view_outsize);
        btnFirst = (TextView) window.findViewById(R.id.btn_first);
        btnSecond = (TextView) window.findViewById(R.id.btn_second);
        btnCancel = (TextView) window.findViewById(R.id.btn_cancel);
    }

    //设置是否强制在dismiss对话框时 销毁Activity
    public void setisFinishActivity(boolean finish) {
        this.finish = finish;
    }

    //设置点击返回键是否dismiss对话框
    public void setCanDismissByBackPressed(boolean canDismiss) {
        this.backCanDismiss = canDismiss;
        dialog.setCancelable(canDismiss);
    }

    //设置取消按钮是否dismiss对话框
    public void setCancelCanDismiss(boolean canDismiss) {
        cancelCanDismiss = canDismiss;
    }

    //设置确定按钮是否dismiss对话框
    public void setConfirmCanDismiss(boolean canDismiss) {
        confirmCanDismiss = canDismiss;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void setListener(final Context context) {
        outsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelCanDismiss) {
                    dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCancelListener != null) {
                    mOnCancelListener.OnCancelListener(v);
                }
                if (cancelCanDismiss) {
                    dismiss();
                }
            }
        });
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFirstButtonListener != null) {
                    mOnFirstButtonListener.OnFirstButtonListener(v);
                }
                dismiss();
            }
        });
        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSecondButtonListener != null) {
                    mOnSecondButtonListener.OnSecondButtonListener(v);
                }
                dismiss();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {//返回键监听
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (backCanDismiss) {
                        dismiss();
                    }
                    if (finish) {
                        ((Activity) context).finish();
                    }
                }
                return false;
            }
        });
    }

    //设置取消按钮点击后的回调事件
    public void setOnCancelListener(String txt, OnCancelListener mOnCancelListener) {
        btnCancel.setText(txt);
        this.mOnCancelListener = mOnCancelListener;
    }

    //设置第一个按钮点击后的回调事件
    public void setOnFirstButtonListener(String txt, OnFirstButtonListener mOnFirstButtonListener) {
        btnFirst.setText(txt);
        btnFirst.setVisibility(View.VISIBLE);
        this.mOnFirstButtonListener = mOnFirstButtonListener;
    }

    //设置第二个按钮点击后的回调事件
    public void setOnSecondButtonListener(String txt, OnSecondButtonListener mOnSecondButtonListener) {
        btnSecond.setText(txt);
        btnSecond.setVisibility(View.VISIBLE);
        this.mOnSecondButtonListener = mOnSecondButtonListener;
    }


    public interface OnCancelListener {
        void OnCancelListener(View v);
    }

    public interface OnFirstButtonListener {
        void OnFirstButtonListener(View v);
    }

    public interface OnSecondButtonListener {
        void OnSecondButtonListener(View v);
    }

}
