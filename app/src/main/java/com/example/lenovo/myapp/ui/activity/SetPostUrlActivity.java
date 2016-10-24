package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cxb.tools.utils.PreferencesUtil;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.okhttp.URLSetting;

/**
 * Created by CXB on 16/8/20.
 * <p/>
 * 设置接口url
 */
public class SetPostUrlActivity extends BaseActivity implements View.OnClickListener {

    private final String HOST = "itest.meishiyi.cn/index.php";
    private final String HOST_TRUE = "i.meishiyi.cn/index.php";
    private final String HOST_TEST = "192.168.199.173/meishiyi/api/index.php";

    private EditText urlText;

    private PreferencesUtil mPreferencesUtil;
    private String curUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_post_url);

        mPreferencesUtil = PreferencesUtil.getInstance();
        curUrl = mPreferencesUtil.getString(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL, HOST);

        urlText = (EditText) findViewById(R.id.et_set_url);

        urlText.setText(curUrl);
        findViewById(R.id.btn_use_default_url).setOnClickListener(this);
        findViewById(R.id.btn_use_true_url).setOnClickListener(this);
        findViewById(R.id.btn_use_custom_url).setOnClickListener(this);
        findViewById(R.id.btn_set_confirm).setOnClickListener(this);
        findViewById(R.id.btn_clear_all_data).setOnClickListener(this);
        findViewById(R.id.btn_clear_db).setOnClickListener(this);
        findViewById(R.id.btn_clear_share).setOnClickListener(this);

        findViewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_use_default_url:
                urlText.setText(HOST);
                break;
            case R.id.btn_use_true_url:
                urlText.setText(HOST_TRUE);
                break;
            case R.id.btn_use_custom_url:
                urlText.setText(HOST_TEST);
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
                        deleteAllData(true, true);
                    }
                }
                break;
            case R.id.btn_clear_all_data:
                deleteAllData(true, true);
                break;
            case R.id.btn_clear_db:
                deleteAllData(true, false);
                break;
            case R.id.btn_clear_share:
                deleteAllData(false, true);
                break;
            case R.id.btn_test:

                break;
        }
    }

    private void deleteAllData(final boolean clearDB, final boolean clearShare) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (clearShare) {
                    mPreferencesUtil.clearData(MyApplication.getInstance(), PreferencesUtil.KEY_BASE_URL);
                }

                URLSetting.getInstance().setBaseUrl(curUrl);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toast("操作成功，请重新打开APP");
                        finish();
                    }
                });
            }
        }).start();
    }
}
