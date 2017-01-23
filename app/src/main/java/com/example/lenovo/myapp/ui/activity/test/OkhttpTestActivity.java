package com.example.lenovo.myapp.ui.activity.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cxb.tools.network.okhttp.OkHttpAsynchApi;
import com.cxb.tools.network.okhttp.OkHttpBaseApi;
import com.cxb.tools.network.okhttp.OnDownloadCallBack;
import com.cxb.tools.network.okhttp.OnRequestCallBack;
import com.cxb.tools.utils.FileUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.cxb.tools.utils.StringUtils;
import com.cxb.tools.utils.ToastUtil;
import com.cxb.tools.utils.VersionUtil;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.GithubBean;
import com.example.lenovo.myapp.model.testbean.WeatherInfo;
import com.example.lenovo.myapp.model.testbean.WeatherList;
import com.example.lenovo.myapp.model.testbean.WeatherToday;
import com.example.lenovo.myapp.okhttp.call.GetWeatherCall;
import com.example.lenovo.myapp.ui.activity.SetPostUrlActivity;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lenovo.myapp.utils.Constants.HOST_GITHUB;
import static com.example.lenovo.myapp.utils.Constants.HOST_PUBLIC_OBJECT;
import static com.example.lenovo.myapp.utils.Constants.ID_GET_GITHUB_INFO;
import static com.example.lenovo.myapp.utils.Constants.ID_GET_OKHTTP_INFO;
import static com.example.lenovo.myapp.utils.Constants.ID_GET_WEATHER;
import static com.example.lenovo.myapp.utils.Constants.ID_POST_WEATHER;
import static com.example.lenovo.myapp.utils.Constants.URL_GET_GITHUB_INFO;
import static com.example.lenovo.myapp.utils.Constants.URL_GET_OKHTTP_INFO;
import static com.example.lenovo.myapp.utils.Constants.URL_WEATHER;

/**
 * okhttp 请求框架
 */

public class OkhttpTestActivity extends BaseActivity {

    private Button btnTodayWeather;
    private Button btnGetWeather;
    private Button btnChange;
    private Button btnAuthenticator;
    private Button btnGetGitHub;
    private Button btnDownload;

    private TextView tvContent;

    private Button btnGetVersion;
    private Button btnCompareVersion;

    private EditText version1;
    private EditText version2;

    private GetWeatherCall getWeatherCall = new GetWeatherCall();
    private OkHttpAsynchApi getTodayWeather;
    private OkHttpAsynchApi getOkhttpAuthInfo;
    private OkHttpAsynchApi downloadCall;

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
        progressDialog.setOnkeyListener(backClick);
        progressDialog.setMessage("请求中...");

        btnChange = (Button) findViewById(R.id.btn_change_url);
        btnChange.setOnClickListener(btnClick);

        btnTodayWeather = (Button) findViewById(R.id.btn_get_today_weather);
        btnTodayWeather.setOnClickListener(btnClick);

        btnGetWeather = (Button) findViewById(R.id.btn_get_weather);
        btnGetWeather.setOnClickListener(btnClick);

        btnGetGitHub = (Button) findViewById(R.id.btn_get_github_info);
        btnGetGitHub.setOnClickListener(btnClick);

        btnAuthenticator = (Button) findViewById(R.id.btn_authenticator);
        btnAuthenticator.setOnClickListener(btnClick);

        btnDownload = (Button) findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(btnClick);

        tvContent = (TextView) findViewById(R.id.tv_content);

        btnGetVersion = (Button) findViewById(R.id.btn_get_app_version);
        btnGetVersion.setOnClickListener(btnClick);

        btnCompareVersion = (Button) findViewById(R.id.btn_compare_version);
        btnCompareVersion.setOnClickListener(btnClick);

        version1 = (EditText) findViewById(R.id.et_version1);
        version2 = (EditText) findViewById(R.id.et_version2);
    }

    private void setData() {
        getTodayWeather = new OkHttpAsynchApi()
                .addListener(callBack);
        getOkhttpAuthInfo = new OkHttpAsynchApi("jesse", "password1")
                .addListener(callBack);

        getWeatherCall.addListener(callBack);

        downloadCall = new OkHttpAsynchApi()
                .addDownloadListener(downloadCallBack);
    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_change_url:
                    startActivity(new Intent(OkhttpTestActivity.this, SetPostUrlActivity.class));
                    break;
                case R.id.btn_get_today_weather:
                    getTodayWeather();
                    break;
                case R.id.btn_get_weather:
                    getWeather();
                    break;
                case R.id.btn_get_github_info:
                    getGitHubInfo();
                    break;
                case R.id.btn_authenticator:
                    getAuthenticatorData();
                    break;
                case R.id.btn_download:
                    downloadFile();
                    break;
                case R.id.btn_get_app_version:
                    String version = VersionUtil.getVersionName(MyApplication.getInstance());
                    version1.setText(version);
                    ToastUtil.toast("版本号：" + version);
                    break;
                case R.id.btn_compare_version:
                    String v1 = version1.getText().toString();
                    String v2 = version2.getText().toString();
                    if (VersionUtil.isNewVersionName(v1, v2)) {
                        ToastUtil.toast("有更新，请下载");
                    } else {
                        ToastUtil.toast("无更新");
                    }
                    break;
            }
        }
    };

    //GET获取当天天气
    private void getTodayWeather() {
        progressDialog.setMessage("获取今天天气中...");
        progressDialog.showDialog();

        Type returnType = new TypeToken<WeatherToday>() {
        }.getType();
        Map<String, String> params = new HashMap<>();

        params.put("app", "weather.today");
        params.put("weaid", "101280800");
        params.put("appkey", "10003");
        params.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format", "json");
        getTodayWeather.setRequestId(ID_GET_WEATHER)
                .setCurrentProtocol(OkHttpBaseApi.Protocol.HTTP)
                .setCurrentBaseUrl(URL_WEATHER)
                .getPath("", params, returnType);
    }

    //POST获取天气预报
    private void getWeather() {
        progressDialog.setMessage("获取天气预报中...");
        progressDialog.showDialog();
        getWeatherCall.setParams("101280800");
        getWeatherCall.requestCall();
    }

    //获取GitHub信息
    private void getGitHubInfo() {
        progressDialog.setMessage("获取GitHub信息中...");
        progressDialog.showDialog();
        getTodayWeather.setRequestId(ID_GET_GITHUB_INFO)
                .setCurrentProtocol(OkHttpBaseApi.Protocol.HTTPS)
                .setCurrentBaseUrl(HOST_GITHUB)
                .getPath(URL_GET_GITHUB_INFO, GithubBean.class);
    }

    //获取需验证的数据
    private void getAuthenticatorData() {
        progressDialog.setMessage("获取需验证数据中...");
        progressDialog.showDialog();
        getOkhttpAuthInfo.setRequestId(ID_GET_OKHTTP_INFO)
                .setCurrentProtocol(OkHttpBaseApi.Protocol.HTTP)
                .setCurrentBaseUrl(HOST_PUBLIC_OBJECT)
                .getPath(URL_GET_OKHTTP_INFO, null);
    }

    private void downloadFile() {
        progressDialog.setMessage("下载中...");
        progressDialog.showDialog();

        String fileName = "duiduoduo.apk";
        String directoryUri = SDCardUtil.getAutoFilesPath(MyApplication.getInstance());
        File file = new File(directoryUri, fileName);

        downloadCall.setCurrentProtocol(OkHttpBaseApi.Protocol.HTTP)
                .setCurrentBaseUrl("121.201.74.114/duidouduo")
                .downloadFile("public/app-debug.apk_1.0.apk", file.getAbsolutePath());
    }

    private DialogInterface.OnKeyListener backClick = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (getWeatherCall != null) {
                getWeatherCall.cancelRequest();
            }
            if (getTodayWeather != null) {
                getTodayWeather.cancelRequest();
            }
            if (getOkhttpAuthInfo != null) {
                getOkhttpAuthInfo.cancelRequest();
            }
            if (downloadCall != null) {
                downloadCall.cancelRequest();
            }
            return false;
        }
    };

    private OnDownloadCallBack downloadCallBack = new OnDownloadCallBack() {
        @Override
        public void onPregrass(long curSize, long maxSize) {
            String curDownload = FileUtil.FormetFileSize(MyApplication.getInstance(), curSize);
            String totalDownload = FileUtil.FormetFileSize(MyApplication.getInstance(), maxSize);
            progressDialog.setMessage("下载中：" + curDownload + "/" + totalDownload);
        }

        @Override
        public void onSuccess(String fileUri) {
            progressDialog.dismissDialog();
            ToastUtil.toast("下载成功");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(fileUri)), "application/vnd.android.package-archive");
            startActivity(intent);
        }

        @Override
        public void onFailed(String fileUri) {
            progressDialog.dismissDialog();
            ToastUtil.toast("已取消下载，下载失败");
        }
    };

    private OnRequestCallBack callBack = new OnRequestCallBack() {
        @Override
        public void onFailure(final int requestId, final FailureReason reason) {
            progressDialog.dismissDialog();

            if (reason != FailureReason.OTHER) {
                ToastUtil.toast(reason.getReason());
            } else {
                switch (requestId) {
                    case ID_GET_WEATHER:
                        ToastUtil.toast("请求今天天气失败");
                        break;
                    case ID_POST_WEATHER:
                        ToastUtil.toast("请求天气预报失败");
                        break;
                    case ID_GET_GITHUB_INFO:
                        ToastUtil.toast("请求失败");
                        break;
                    case ID_GET_OKHTTP_INFO:
                        ToastUtil.toast("验证请求失败");
                        break;
                }
            }
        }

        @Override
        public void onResponse(final int requestId, final Object dataObject, int networkCode) {
            progressDialog.dismissDialog();
            String content = "";
            switch (requestId) {
                case ID_GET_WEATHER:
                    WeatherToday today = (WeatherToday) dataObject;
                    if (today != null) {
                        WeatherInfo info = today.getResult();
                        if (info != null) {
                            content += "日期：" + info.getDays() + "\n";
                            content += "城市：" + info.getCitynm() + "\n";
                            content += "天气：" + info.getWeather() + "\n";
                            content += "温度：" + info.getTemperature() + "\n";
                            content += "风力：" + info.getWind() + " " + info.getWinp() + "\n";
                            content += "湿度：" + info.getHumidity();
                        }
                    }
                    ToastUtil.toast(content);
                    tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    break;
                case ID_POST_WEATHER:
                    WeatherList wl = (WeatherList) dataObject;
                    if (wl != null) {
                        List<WeatherInfo> temp = wl.getResult();
                        if (temp != null) {
                            for (WeatherInfo info : temp) {
                                content += "\n\n";
                                content += "日期：" + info.getDays() + "\n";
                                content += "城市：" + info.getCitynm() + "\n";
                                content += "天气：" + info.getWeather() + "\n";
                                content += "温度：" + info.getTemperature() + "\n";
                                content += "风力：" + info.getWind() + " " + info.getWinp() + "\n";
                                content += "湿度：" + info.getHumidity();
                            }
                        }
                    }
                    ToastUtil.toast("请求天气预报成功");
                    tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    break;
                case ID_GET_GITHUB_INFO:
                    GithubBean github = (GithubBean) dataObject;
                    Map<String, GithubBean.OkhttpTxt> map = github.getFiles();
                    for (Map.Entry<String, GithubBean.OkhttpTxt> entry : map.entrySet()) {
                        content += entry.getKey() + "\n" + entry.getValue().content + "\n";
                    }
                    content = StringUtils.halfToFull(content);
                    ToastUtil.toast("请求Github成功");
                    tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 3);
                    break;
                case ID_GET_OKHTTP_INFO:
                    if (dataObject != null) {
                        content += dataObject.toString();
                    }
                    content = StringUtils.halfToFull(content);
                    ToastUtil.toast("验证请求成功");
                    tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 3);
                    break;
            }
            Logger.d(content);
            tvContent.setText(content);
        }
    };
}
