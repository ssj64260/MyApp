package com.example.lenovo.myapp.ui.activity.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cxb.tools.utils.NetworkUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * webview
 */

public class WebViewTestActivity extends BaseActivity {

    private final String APP_CACAHE_DIRNAME = "/webview_cache";
    private final String URL_HOME_PAGE = "http://www.gamersky.com/1";

    private LinearLayout llRootView;
    private EditText etTitle;
    private ImageButton ivRefresh;
    private ProgressBar pbProgress;
    private WebView wvWeb;

    private ImageButton btnLeft;
    private ImageButton btnRight;
    private ImageButton btnHome;
    private ImageButton btnPage;
    private ImageButton btnTools;

    private LinearLayout llBottom;

    private String curTitle;
    private String curUrl = URL_HOME_PAGE;

    private boolean isEditing = false;
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test);

        initView();
        setData();

    }

    @Override
    protected void onResume() {
        //激活WebView为活跃状态，能正常执行网页的响应
        wvWeb.onResume();

        //恢复pauseTimers状态
        wvWeb.resumeTimers();

        super.onResume();
    }

    @Override
    protected void onPause() {
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        wvWeb.onPause();

        //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        wvWeb.pauseTimers();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        wvWeb.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        wvWeb.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        wvWeb.clearFormData();

        //销毁Webview
        //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
        //但是注意：webview调用destory时,webview仍绑定在Activity上
        //这是由于自定义webview构建时传入了该Activity的context对象
        //因此需要先从父容器中移除webview,然后再销毁webview:
        llRootView.removeView(wvWeb);
        wvWeb.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && wvWeb.canGoBack()) {
            wvWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        llRootView = (LinearLayout) findViewById(R.id.ll_rootview);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivRefresh = (ImageButton) findViewById(R.id.ib_refresh);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        wvWeb = (WebView) findViewById(R.id.wv_web);

        btnLeft = (ImageButton) findViewById(R.id.btn_left);
        btnRight = (ImageButton) findViewById(R.id.btn_right);
        btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnPage = (ImageButton) findViewById(R.id.btn_page);
        btnTools = (ImageButton) findViewById(R.id.btn_tools);

        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    private void setData() {
        //声明WebSettings子类
        WebSettings webSettings = wvWeb.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

        String cacheDirPath = SDCardUtil.getFilesDir(getApplicationContext()) + APP_CACAHE_DIRNAME;
        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

        wvWeb.loadUrl(curUrl);
        wvWeb.setWebViewClient(client);
        wvWeb.setWebChromeClient(chromeClient);
        wvWeb.setOnTouchListener(rootTouch);

        pbProgress.setMax(100);

        etTitle.setOnFocusChangeListener(focusChange);
        llRootView.setOnTouchListener(rootTouch);
        llRootView.setFocusable(true);
        llRootView.setFocusableInTouchMode(true);
        llRootView.requestFocus();
        ivRefresh.setOnClickListener(click);
        btnLeft.setOnClickListener(click);
        btnRight.setOnClickListener(click);
        btnHome.setOnClickListener(click);
        btnPage.setOnClickListener(click);
        btnTools.setOnClickListener(click);

        setOnKeyboardChangeListener();
    }

    private void webViewGoBack() {
        WebBackForwardList history = wvWeb.copyBackForwardList();
        int curIndex = history.getCurrentIndex();
        WebView.HitTestResult result = wvWeb.getHitTestResult();
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            switch (v.getId()) {
                case R.id.ib_refresh:
                    if (isEditing) {
                        curUrl = etTitle.getText().toString();
                        wvWeb.loadUrl(curUrl);
                    } else {
                        wvWeb.reload();
                    }
                    break;
                case R.id.btn_left:
                    if (wvWeb.canGoBack()) {
                        wvWeb.goBack();

                    } else {
                        ToastUtil.toast("没有上一页了");
                    }
                    break;
                case R.id.btn_right:
                    if (isLoading) {
                        wvWeb.stopLoading();
                    } else {
                        if (wvWeb.canGoForward()) {
                            wvWeb.goForward();
                        } else {
                            ToastUtil.toast("没有下一页了");
                        }
                    }
                    break;
                case R.id.btn_home:
                    WebBackForwardList history = wvWeb.copyBackForwardList();
                    int curIndex = history.getCurrentIndex();
                    curUrl = history.getItemAtIndex(0).getUrl();
                    wvWeb.goBackOrForward(0 - curIndex);
                    break;
                case R.id.btn_page:

                    break;
                case R.id.btn_tools:

                    break;
            }

            llRootView.setFocusable(true);
            llRootView.setFocusableInTouchMode(true);
            llRootView.requestFocus();
        }
    };

    private View.OnTouchListener rootTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            hideKeyboard();
            llRootView.setFocusable(true);
            llRootView.setFocusableInTouchMode(true);
            llRootView.requestFocus();

            return false;
        }
    };

    private View.OnFocusChangeListener focusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isEditing = hasFocus;
            if (hasFocus) {
                etTitle.setText(curUrl);
                ivRefresh.setImageResource(R.mipmap.ic_via_go);
            } else {
                etTitle.setText(curTitle);
                ivRefresh.setImageResource(R.mipmap.ic_via_refresh);
            }
        }
    };

    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebView.HitTestResult result = view.getHitTestResult();
            isLoading = true;
            if (result == null) {
                curUrl = url;
                view.loadUrl(curUrl);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //设定加载开始的操作
            isLoading = true;
            pbProgress.setVisibility(View.VISIBLE);
            btnRight.setImageResource(R.mipmap.ic_via_cancel);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //设定加载结束的操作
//            pbProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
//            Logger.d("onLoadResource:\t" + url);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//            int code = errorResponse.getStatusCode();
//            switch (code) {
//                case 404:
//                    ToastUtil.toast("无法找到页面");
//                    break;
//            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            Logger.d("doUpdateVisitedHistory:\t" + url);
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pbProgress.setProgress(newProgress);
            if (newProgress >= 100) {
                pbProgress.setVisibility(View.INVISIBLE);
                btnRight.setImageResource(R.mipmap.ic_via_go);
                isLoading = false;
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            curTitle = title;
            etTitle.setText(curTitle);
        }
    };

    @Override
    public void onkeyboardChange(boolean isShow) {
        if (isShow) {
            llBottom.setVisibility(View.GONE);
        } else {
            llBottom.setVisibility(View.VISIBLE);
        }
    }
}
