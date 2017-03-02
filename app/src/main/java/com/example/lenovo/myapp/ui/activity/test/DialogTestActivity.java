package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.ui.dialog.EvaluationDialog;
import com.example.lenovo.myapp.ui.dialog.InputContentDialog;
import com.example.lenovo.myapp.ui.dialog.TipsActionDialog;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 对话框测试
 */

public class DialogTestActivity extends BaseActivity {

    private Button btnClear;
    private Button btnPrograss;
    private Button btnInput;
    private Button btnEvaluation;

    private DefaultProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        initView();
    }

    private void initView() {
        progressDialog = new DefaultProgressDialog(this);

        btnClear = (Button) findViewById(R.id.btn_clear_cache);
        btnClear.setOnClickListener(click);

        btnPrograss = (Button) findViewById(R.id.btn_prograss);
        btnPrograss.setOnClickListener(click);

        btnInput = (Button) findViewById(R.id.btn_input);
        btnInput.setOnClickListener(click);

        btnEvaluation = (Button) findViewById(R.id.btn_input_evaluation);
        btnEvaluation.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_clear_cache:
                    showTipsActionDialog();
                    break;
                case R.id.btn_prograss:
                    showProgressDialog();
                    break;
                case R.id.btn_input:
                    showInputContentDialog();
                    break;
                case R.id.btn_input_evaluation:
                    showEvaluationDialog();
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // show dialog method
    ///////////////////////////////////////////////////////////////////////////
    private void showTipsActionDialog() {
        TipsActionDialog dialog = new TipsActionDialog(this);
        dialog.setTitleText(getString(R.string.text_tips_dialog_title));
        dialog.setConfirmText(getString(R.string.btn_tips_dialog_confirm));
        dialog.setCancelText(getString(R.string.btn_tips_dialog_cancel));
        dialog.setOnConfirmListener(new TipsActionDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v) {
                ToastMaster.toast(getString(R.string.toast_you_can_not_do_that));
            }
        });
    }

    private void showProgressDialog() {
        progressDialog.setMessage(getString(R.string.text_loading));
        progressDialog.showDialog();
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismissDialog();
                    }
                });
            }
        });
    }

    private void showInputContentDialog() {
        InputContentDialog dialog = new InputContentDialog(this);
        dialog.setCanDismissByBackPressed(false);
        dialog.setTitleText(getString(R.string.text_input_dialog_title));
        dialog.setConfirmText(getString(R.string.text_input_dialog_send));
        dialog.setCancelText(getString(R.string.text_input_dialog_close));
        dialog.setOnConfirmListener(new InputContentDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v, String content) {
                hideKeyboard();
                if (StringCheck.isEmpty(content)) {
                    ToastMaster.toast(getString(R.string.toast_contant_can_not_null));
                } else {
                    ToastMaster.toast(content);
                }
            }
        });
    }

    private void showEvaluationDialog() {
        EvaluationDialog evaDialog = new EvaluationDialog(this);
        evaDialog.setTitle(getString(R.string.text_evalutation_dialog_title));
        evaDialog.setConfirmDismiss(true);
        evaDialog.setOnSelectedListener(new EvaluationDialog.OnSelectedListener() {
            @Override
            public void OnselectedListener(View v, String content, float score) {
                ToastMaster.toast(String.format(getString(R.string.toast_contant_and_score), content, score));
            }
        });
    }
}
