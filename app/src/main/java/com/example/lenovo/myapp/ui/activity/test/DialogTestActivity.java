package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.dialog.EvaluationDialog;
import com.example.lenovo.myapp.dialog.InputContentDialog;
import com.example.lenovo.myapp.dialog.TipsActionDialog;

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

    private void initView(){
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
            switch (v.getId()){
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
        dialog.setTitleText("确定要这样？");
        dialog.setConfirmText("确定");
        dialog.setCancelText("取消");
        dialog.setOnConfirmListener(new TipsActionDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v) {
                ToastUtil.toast("你不能这样的！");
            }
        });
    }

    private void showProgressDialog() {
        progressDialog.setMessage("加载中...");
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
        dialog.setTitleText("请填写发送的内容");
        dialog.setConfirmText("发送");
        dialog.setCancelText("关闭");
        dialog.setOnConfirmListener(new InputContentDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v, String content) {
                hideKeyboard();
                if (StringCheck.isEmpty(content)) {
                    ToastUtil.toast("内容不能为空");
                } else {
                    ToastUtil.toast(content);
                }
            }
        });
    }

    private void showEvaluationDialog() {
        EvaluationDialog evaDialog = new EvaluationDialog(this);
        evaDialog.setTitle("给这个APP评价一次");
        evaDialog.setConfirmDismiss(true);
        evaDialog.setOnSelectedListener(new EvaluationDialog.OnSelectedListener() {
            @Override
            public void OnselectedListener(View v, String content, float score) {
                ToastUtil.toast("评价内容:" + content + "\n评分:" + score);
            }
        });
    }
}
