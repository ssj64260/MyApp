package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.myapp.utils.PreferencesUtil;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_BASE_URL;
import static com.example.lenovo.myapp.utils.PreferencesUtil.USER_INFO;
import static com.example.lenovo.myapp.utils.Constants.CUSTOM_URL;
import static com.example.lenovo.myapp.utils.Constants.DEBUG_URL;
import static com.example.lenovo.myapp.utils.Constants.OFFICIAL_URL;

/**
 * 设置接口url
 */
public class SetPostUrlActivity extends BaseActivity {

    private EditText urlText;

    private String curUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_post_url);

        curUrl = PreferencesUtil.getString(APP_SETTING, KEY_BASE_URL, DEBUG_URL);

        urlText = (EditText) findViewById(R.id.et_set_url);

        urlText.setText(curUrl);
        findViewById(R.id.btn_use_default_url).setOnClickListener(btnClick);
        findViewById(R.id.btn_use_true_url).setOnClickListener(btnClick);
        findViewById(R.id.btn_use_custom_url).setOnClickListener(btnClick);
        findViewById(R.id.btn_set_confirm).setOnClickListener(btnClick);
        findViewById(R.id.btn_clear_all_data).setOnClickListener(btnClick);
        findViewById(R.id.btn_clear_db).setOnClickListener(btnClick);
        findViewById(R.id.btn_clear_share).setOnClickListener(btnClick);

        findViewById(R.id.btn_test).setOnClickListener(btnClick);

        setOnKeyboardChangeListener();
    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_use_default_url:
                    urlText.setText(DEBUG_URL);
                    break;
                case R.id.btn_use_true_url:
                    urlText.setText(OFFICIAL_URL);
                    break;
                case R.id.btn_use_custom_url:
                    urlText.setText(CUSTOM_URL);
                    break;
                case R.id.btn_set_confirm:
                    String url = urlText.getText().toString();
                    if (StringCheck.isEmpty(url)) {
                        ToastUtil.toast("url不能为空");
                    } else {
                        if (url.equals(curUrl)) {
                            ToastUtil.toast("设置成功");
                            finish();
                        } else {
                            curUrl = url;
                            deleteAllData(true);
                        }
                    }
                    break;
                case R.id.btn_clear_all_data:
                    deleteAllData(true);
                    break;
                case R.id.btn_clear_db:
                    deleteAllData(false);
                    break;
                case R.id.btn_clear_share:
                    deleteAllData(true);
                    break;
                case R.id.btn_test:

                    break;
            }
        }
    };

    private void deleteAllData(final boolean clearShare) {
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                if (clearShare) {
                    PreferencesUtil.clearAll(APP_SETTING);
                    PreferencesUtil.clearAll(USER_INFO);
                }

                PreferencesUtil.setData(APP_SETTING, KEY_BASE_URL, curUrl);
                URLSetting.getInstance().setUrl();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toast("操作成功，请重新打开APP");
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onkeyboardChange(boolean isShow) {
        ToastUtil.toast(isShow ? "键盘弹起" : "键盘收起");
    }
}
