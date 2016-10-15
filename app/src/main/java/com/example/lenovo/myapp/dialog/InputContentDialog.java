package com.example.lenovo.myapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.myapp.R;

/**
 * 内容输入对话框
 */
public class InputContentDialog {

    private AlertDialog dialog;
    private Window window;

    private TextView title;
    private EditText content;
    private TextView cancel, confirm;

    private OnConfirmListener mOnConfirmListener;
    private OnCancelListener mOnCancelListener;

    private boolean finish = false;

    private boolean backCanDismiss = true;//系统返回键能销毁对话框
    private boolean cancelCanDismiss = true;//取消按钮能销毁对话框
    private boolean confirmCanDismiss = true;//确定按钮能销毁对话框

    public InputContentDialog(final Context context) {
        dialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_input_content, null);
        dialog.setView(layout);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        setView();

        setListener(context);
    }

    private void setView() {
        window = dialog.getWindow();
        window.setContentView(R.layout.dialog_input_content);
//        window.setWindowAnimations(R.style.dialoganimstyle);  //添加动画
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置不被虚拟键盘遮挡
        window.setBackgroundDrawableResource(R.color.transparent);//设置对话框以外的背景颜色


        title = (TextView) window.findViewById(R.id.tv_title);
        content = (EditText) window.findViewById(R.id.et_content);
        cancel = (TextView) window.findViewById(R.id.btn_cancel);
        confirm = (TextView) window.findViewById(R.id.btn_confirm);
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

    //设置取消按钮显示隐藏
    public void setCancelButtonVisibility(int v) {
        if (cancel != null) {
            cancel.setVisibility(v);
        }
    }

    //设置确定按钮显示隐藏
    public void setConfirmButtonVisibility(int v) {
        if (confirm != null) {
            confirm.setVisibility(v);
        }
    }

    //设置取消按钮是否dismiss对话框
    public void setCancelCanDismiss(boolean canDismiss) {
        cancelCanDismiss = canDismiss;
    }

    //设置确定按钮是否dismiss对话框
    public void setConfirmCanDismiss(boolean canDismiss) {
        confirmCanDismiss = canDismiss;
    }

    //设置标题
    public void setTitleText(String tit) {
        title.setText(tit);
    }

    //设置确定按钮文字
    public void setConfirmText(String con) {
        confirm.setText(con);
    }

    //设置取消按钮文字
    public void setCancelText(String can) {
        cancel.setText(can);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void setListener(final Context context) {
        cancel.setOnClickListener(new View.OnClickListener() {
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnConfirmListener != null) {
                    String con = content.getText().toString();
                    mOnConfirmListener.OnConfirmListener(v, con);
                }
                if (confirmCanDismiss) {
                    dismiss();
                }
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

    //设置确定按钮点击后的回调事件
    public void setOnConfirmListener(OnConfirmListener mOnConfirmListener) {
        this.mOnConfirmListener = mOnConfirmListener;
    }

    public interface OnConfirmListener {
        void OnConfirmListener(View v, String content);
    }

    //设置取消按钮点击后的回调事件
    public void setOnCancelListener(OnCancelListener mOnCancelListener) {
        this.mOnCancelListener = mOnCancelListener;
    }

    public interface OnCancelListener {
        void OnCancelListener(View v);
    }

}
