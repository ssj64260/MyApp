package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxb.tools.MyProgressBar.MyProgressBar;
import com.cxb.tools.PasswordLevel.PasswordLevelLayout;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_FIRST_START;

/**
 * 自定义控件展示
 */

public class CustomViewTestActivity extends BaseActivity {

    private EditText etPassword;
    private PasswordLevelLayout pllPwdLevel;

    private EditText etProgress;
    private Button btnConfirm;
    private MyProgressBar mpbProgress;

    private Button btnGetCode;
    private Button btnFirstStart;

    private int downTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);

        initView();

        setData();

    }

    private void initView() {
        etPassword = (EditText) findViewById(R.id.et_password);
        pllPwdLevel = (PasswordLevelLayout) findViewById(R.id.pll_password_level);

        etProgress = (EditText) findViewById(R.id.et_progress);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        mpbProgress = (MyProgressBar) findViewById(R.id.mpb_progress);

        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        btnFirstStart = (Button) findViewById(R.id.btn_first_start);
    }

    private void setData() {
        etPassword.addTextChangedListener(watcher);

        btnConfirm.setOnClickListener(click);

        btnGetCode.setOnClickListener(click);
        btnFirstStart.setOnClickListener(click);
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Logger.d(millisUntilFinished);
            downTime--;
            btnGetCode.setText(String.format("(%d) 再次倒数", downTime));
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("倒数60秒");
            btnGetCode.setClickable(true);
            timer.cancel();
        }
    };

    //输入框内容改变监听
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pllPwdLevel.setCurrentLevel(StringCheck.getPasswordLevel(s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            switch (v.getId()) {
                case R.id.btn_confirm:
                    String number = etProgress.getText().toString().trim();
                    if (StringCheck.isOnlyNumber(number)) {
                        hideKeyboard();
                        mpbProgress.setCurrentNumber(Float.parseFloat(number));
                        mpbProgress.showAnimation();
                    } else {
                        ToastUtil.toast("不是纯数字");
                    }
                    break;
                case R.id.btn_get_code:
                    btnGetCode.setClickable(false);
                    downTime = 60;
                    timer.start();
                    break;
                case R.id.btn_first_start:
                    PreferencesUtil.setData(APP_SETTING, KEY_FIRST_START, true);
                    ToastUtil.toast("设置成功");
                    break;
            }
        }
    };

}
