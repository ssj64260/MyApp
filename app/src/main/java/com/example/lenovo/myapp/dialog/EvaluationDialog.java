package com.example.lenovo.myapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapp.R;

/**
 * 评价dialog
 */
public class EvaluationDialog {

    private AlertDialog dialog;

    private RelativeLayout rlDialog;
    private TextView title;
    private ImageView close;
    private RatingBar star;
    private EditText content;
    private Button btn;

    private String eva;
    private float score = 5;

    private InputMethodManager manager;
    private Window window;

    private boolean confirmDismiss = false;

    //功能介绍对话框
    public EvaluationDialog(final Context context) {
        dialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_evaluation, null);
        dialog.setView(layout);
        dialog.show();

        manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        window = dialog.getWindow();
        window.setContentView(R.layout.dialog_evaluation);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        window.setWindowAnimations(R.style.dialoganimstyle);  //添加动画
        window.setBackgroundDrawableResource(R.color.transparent);

        initView();
        initData();
        setData();

    }

    private void initView() {
        rlDialog = (RelativeLayout) window.findViewById(R.id.rl_dialog);
        title = (TextView) window.findViewById(R.id.tv_title);
        close = (ImageView) window.findViewById(R.id.iv_close);
        star = (RatingBar) window.findViewById(R.id.rb_evaluation);
        content = (EditText) window.findViewById(R.id.et_content);
        btn = (Button) window.findViewById(R.id.btn_evaluation);
    }

    private void initData() {

    }

    private void setData() {
        rlDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                dismiss();
            }
        });
        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    hideKeyboard();
                    score = rating;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (onSelectedListener != null) {
                    eva = content.getText().toString().trim();
                    onSelectedListener.OnselectedListener(v, eva, score);
                    if (confirmDismiss) {
                        dismiss();
                    }
                }
            }
        });

//        content.setFocusable(true);
//        content.setFocusableInTouchMode(true);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });
    }

    public OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        public void OnselectedListener(View v, String content, float score);
    }

    public void setClickAble(boolean clickAble) {
        btn.setClickable(clickAble);
    }

    public void setTitle(String t) {
        title.setText(t);
    }

    public void setEditTextHint(String hint) {
        content.setHint(hint);
    }

    public void setConfirmDismiss(boolean confirmDismiss) {
        this.confirmDismiss = confirmDismiss;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    private void hideKeyboard() {
        if (window.getCurrentFocus() != null && window.getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            content.clearFocus();
        }
    }
}
