package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cxb.tools.network.okhttp.OkHttpApi;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.model.meishiyi.AdBean;
import com.example.lenovo.myapp.model.meishiyi.TableBean;
import com.example.lenovo.myapp.okhttp.call.MeishiyiAdCall;
import com.example.lenovo.myapp.okhttp.call.MeishiyiTableCall;
import com.example.lenovo.myapp.utils.Constants;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * okhttp 请求框架
 */

public class OkhttpTestActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAd;
    private Button btnTable;
    private Button btnChange;

    private MeishiyiAdCall adCall = new MeishiyiAdCall();
    private MeishiyiTableCall tableCall = new MeishiyiTableCall();

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
        progressDialog.setMessage("请求中...");

        btnChange = (Button) findViewById(R.id.btn_change_url);
        btnChange.setOnClickListener(this);

        btnAd = (Button) findViewById(R.id.btn_meishiyi_ad);
        btnAd.setOnClickListener(this);

        btnTable = (Button) findViewById(R.id.btn_meishiyi_table);
        btnTable.setOnClickListener(this);
    }

    private void setData() {
        adCall.setParams("70q2K29N2c8910p827M6Gff1Td1YIo", "aiweitest");
        adCall.addListener(callBack);
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
                adCall.requestCall();
                break;
            case R.id.btn_meishiyi_table:
                tableCall.requestCall();
                break;
        }
    }

    private OkHttpApi.OnRequestCallBack callBack = new OkHttpApi.OnRequestCallBack() {
        @Override
        public void onBefore(int requestId) {
            progressDialog.showDialog();
        }

        @Override
        public void onFailure(final int requestId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismissDialog();
                    switch (requestId) {
                        case Constants.MSY_AD:
                            ToastUtil.toast("请求美食易广告失败");
                            break;
                        case Constants.MSY_TABLE:
                            ToastUtil.toast("请求美食易时间段失败");
                            break;
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
                    switch (requestId) {
                        case Constants.MSY_AD:
                            List<AdBean> adTemp = (List<AdBean>) dataObject;
                            for (AdBean ad : adTemp) {
                                Logger.d(ad.getPic_url());
                            }
                            ToastUtil.toast("请求美食易广告成功");
                            break;
                        case Constants.MSY_TABLE:
                            List<TableBean> timeTemp = (List<TableBean>) dataObject;
                            for (TableBean time : timeTemp) {
                                Logger.d(time.getTableName());
                            }
                            ToastUtil.toast("请求美食易时1间段成功");
                            break;
                    }
                }
            });
        }
    };
}
