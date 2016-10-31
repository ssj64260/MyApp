package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxb.tools.network.okhttp.OkHttpApi;
import com.cxb.tools.network.okhttp.OnRequestCallBack;
import com.cxb.tools.utils.ToastUtil;
import com.cxb.tools.utils.VersionUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.model.testbean.AdBean;
import com.example.lenovo.myapp.model.testbean.GithubBean;
import com.example.lenovo.myapp.model.testbean.TableBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.okhttp.call.MeishiyiTableCall;
import com.example.lenovo.myapp.utils.Constants;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * okhttp 请求框架
 */

public class OkhttpTestActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAd;
    private Button btnTable;
    private Button btnChange;
    private Button btnAuthenticator;
    private Button btnSynGet;
    private Button btnGetVersion;
    private Button btnCompareVersion;

    private EditText version1;
    private EditText version2;

    private MeishiyiTableCall tableCall = new MeishiyiTableCall();
    private OkHttpApi okHttpApi;
    private OkHttpApi okHttpAuthenticatior;

    private DefaultProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test);

        initView();
        setData();

    }

    private void initView() {
        progressDialog = new DefaultProgressDialog(this);
        progressDialog.setOnDismissListener(new DefaultProgressDialog.OnDismissListener() {
            @Override
            public void OnDismissListener() {
                okHttpApi.cancelRequest();
            }
        });
        progressDialog.setMessage("请求中...");

        btnChange = (Button) findViewById(R.id.btn_change_url);
        btnChange.setOnClickListener(this);

        btnAd = (Button) findViewById(R.id.btn_meishiyi_ad);
        btnAd.setOnClickListener(this);

        btnTable = (Button) findViewById(R.id.btn_meishiyi_table);
        btnTable.setOnClickListener(this);

        btnSynGet = (Button) findViewById(R.id.btn_syn_get);
        btnSynGet.setOnClickListener(this);

        btnAuthenticator = (Button) findViewById(R.id.btn_authenticator);
        btnAuthenticator.setOnClickListener(this);

        btnGetVersion = (Button) findViewById(R.id.btn_get_app_version);
        btnGetVersion.setOnClickListener(this);

        btnCompareVersion = (Button) findViewById(R.id.btn_compare_version);
        btnCompareVersion.setOnClickListener(this);

        version1 = (EditText) findViewById(R.id.et_version1);
        version2 = (EditText) findViewById(R.id.et_version2);
    }

    private void setData() {
        okHttpApi = new OkHttpApi()
                .addListener(callBack);
        okHttpAuthenticatior = new OkHttpApi("jesse", "password1")
                .addListener(callBack);

        tableCall.setParams("1");
        tableCall.addListener(callBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_url:
                Intent intent = new Intent();
                intent.setClass(this, SetPostUrlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_meishiyi_ad:
                Type returnType = new TypeToken<List<AdBean>>() {
                }.getType();
                Map<String, String> params = new HashMap<>();
                params.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
                params.put("user", "aiweitest");
                okHttpApi.setRequestId(Constants.REQUEST_ID_MSY_AD)
                        .setCurrentProtocol(OkHttpApi.Protocol.HTTP)
                        .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                        .getPath(Constants.URL_MSY_AD, params, returnType);
                break;
            case R.id.btn_meishiyi_table:
                tableCall.requestCall();
                break;
            case R.id.btn_syn_get:
                okHttpApi.setRequestId(9999)
                        .setCurrentProtocol(OkHttpApi.Protocol.HTTPS)
                        .setCurrentBaseUrl("api.github.com")
                        .getPath("gists/c2a7c39532239ff261be", GithubBean.class);
                break;
            case R.id.btn_authenticator:
                okHttpAuthenticatior.setRequestId(9998)
                        .setCurrentProtocol(OkHttpApi.Protocol.HTTP)
                        .setCurrentBaseUrl("publicobject.com")
                        .getPath("secrets/hellosecret.txt", null);
                break;
            case R.id.btn_get_app_version:
                ToastUtil.toast(VersionUtil.getVersionName(getPackageManager(), getApplication().getPackageName()));
                break;
            case R.id.btn_compare_version:
                String v1 = version1.getText().toString();
                String v2 = version2.getText().toString();
                if (VersionUtil.isNewVersion(v1, v2)) {
                    ToastUtil.toast("有更新，请下载");
                } else {
                    ToastUtil.toast("无更新");
                }
                break;
        }
    }

    private OnRequestCallBack callBack = new OnRequestCallBack() {
        @Override
        public void onBefore(int requestId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.showDialog();
                }
            });
        }

        @Override
        public void onFailure(final int requestId, final FailureReason reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismissDialog();

                    if (reason != FailureReason.OTHER) {
                        ToastUtil.toast(reason.getReason());
                    } else {
                        switch (requestId) {
                            case Constants.REQUEST_ID_MSY_AD:
                                ToastUtil.toast("请求美食易广告失败");
                                break;
                            case Constants.REQUEST_ID_MSY_TABLE:
                                ToastUtil.toast("请求美食易时间段失败");
                                break;
                            case 9999:
                                ToastUtil.toast("请求失败");
                                break;
                            case 9998:
                                ToastUtil.toast("验证请求失败");
                                break;
                        }
                    }
                }
            });
        }

        @Override
        public void onResponse(final int requestId, final Object dataObject, int networkCode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismissDialog();
                    String content = "";
                    switch (requestId) {
                        case Constants.REQUEST_ID_MSY_AD:
                            List<AdBean> adTemp = (List<AdBean>) dataObject;
                            for (AdBean ad : adTemp) {
                                content += ad.getPic_url() + "\n";
                            }
                            ToastUtil.toast("请求美食易广告成功");
                            break;
                        case Constants.REQUEST_ID_MSY_TABLE:
                            List<TableBean> timeTemp = (List<TableBean>) dataObject;
                            for (TableBean time : timeTemp) {
                                content += time.getTableName() + "\n";
                            }
                            ToastUtil.toast("请求美食易时间段成功");
                            break;
                        case 9999:
                            GithubBean github = (GithubBean) dataObject;
                            Map<String, GithubBean.OkhttpTxt> map = github.getFiles();
                            for (Map.Entry<String, GithubBean.OkhttpTxt> entry : map.entrySet()) {
                                content += entry.getKey() + "\n" + entry.getValue().content + "\n";
                            }
                            ToastUtil.toast("请求Github成功");
                            break;
                        case 9998:
                            if (dataObject != null) {
                                content += dataObject.toString();
                            }
                            ToastUtil.toast("验证请求成功");
                            break;
                    }
                    Logger.d(content);
                }
            });
        }
    };
}
