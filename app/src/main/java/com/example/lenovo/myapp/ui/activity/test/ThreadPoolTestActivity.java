package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cxb.tools.network.okhttp.OkHttpBaseApi;
import com.cxb.tools.network.okhttp.OkHttpSynchApi;
import com.cxb.tools.network.okhttp.OnRequestCallBack;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.model.testbean.AdBean;
import com.example.lenovo.myapp.model.testbean.GithubBean;
import com.example.lenovo.myapp.model.testbean.TableBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.utils.Constants;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试
 */

public class ThreadPoolTestActivity extends BaseActivity {

    private Button btnHeartGet;
    private Button btnSequence;
    private Button btnCached;
    private Button btnFixed;
    private Button btnScheduled;
    private Button btnScheduledRate;
    private Button btnScheduledDelay;
    private Button btnSingle;
    private Button btnShutDown;

    private DefaultProgressDialog progressDialog;

    private OkHttpSynchApi getAd;
    private OkHttpSynchApi getList;
    private OkHttpSynchApi getGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_test);

        initVew();
        initData();

    }

    private void initVew() {

        btnHeartGet = (Button) findViewById(R.id.btn_heart_get);
        btnHeartGet.setOnClickListener(click);

        btnSequence = (Button) findViewById(R.id.btn_sequence);
        btnSequence.setOnClickListener(click);

        btnCached = (Button) findViewById(R.id.btn_cached_thread_pool);
        btnCached.setOnClickListener(click);

        btnFixed = (Button) findViewById(R.id.btn_fixed_thread_pool);
        btnFixed.setOnClickListener(click);

        btnScheduled = (Button) findViewById(R.id.btn_scheduled_thread_pool);
        btnScheduled.setOnClickListener(click);

        btnScheduledRate = (Button) findViewById(R.id.btn_scheduled_thread_pool_rate);
        btnScheduledRate.setOnClickListener(click);

        btnScheduledDelay = (Button) findViewById(R.id.btn_scheduled_thread_pool_delay);
        btnScheduledDelay.setOnClickListener(click);

        btnSingle = (Button) findViewById(R.id.btn_single_thread_pool);
        btnSingle.setOnClickListener(click);

        btnShutDown = (Button) findViewById(R.id.btn_shut_down);
        btnShutDown.setOnClickListener(click);
    }

    private void initData() {
        progressDialog = new DefaultProgressDialog(this);
        progressDialog.setMessage("请求中...");

        getAd = new OkHttpSynchApi()
                .addListener(callBack);

        getList = new OkHttpSynchApi()
                .addListener(callBack);

        getGithub = new OkHttpSynchApi()
                .addListener(callBack);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_heart_get:
                    heartGet();
                    break;
                case R.id.btn_sequence:
                    sequence();
                    break;
                case R.id.btn_cached_thread_pool:
                    cachedThreadPool();
                    break;
                case R.id.btn_fixed_thread_pool:
                    fixedThreadPool();
                    break;
                case R.id.btn_scheduled_thread_pool:
                    scheduledThreadPool();
                    break;
                case R.id.btn_scheduled_thread_pool_rate:
                    scheduledThreadPoolRate();
                    break;
                case R.id.btn_scheduled_thread_pool_delay:
                    scheduledThreadPoolDelay();
                    break;
                case R.id.btn_single_thread_pool:
                    singleThreadPool();
                    break;
                case R.id.btn_shut_down:
                    shutDownAll();
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////
    private void heartGet() {
        final Type returnType = new TypeToken<List<AdBean>>() {
        }.getType();
        final Map<String, String> params = new HashMap<>();
        params.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        params.put("user", "aiweitest");

        ThreadPoolUtil.getInstache().scheduledRate(new Runnable() {

            @Override
            public void run() {
                getAd.setRequestId(Constants.REQUEST_ID_MSY_AD)
                        .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                        .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                        .getPath(Constants.URL_MSY_AD, params, returnType);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    private void sequence() {
        final Type adType = new TypeToken<List<AdBean>>() {
        }.getType();
        final Type tabType = new TypeToken<List<TableBean>>() {
        }.getType();

        final Map<String, String> adParams = new HashMap<>();
        adParams.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        adParams.put("user", "aiweitest");

        final Map<String, String> tabParams = new HashMap<>();
        tabParams.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        tabParams.put("user", "aiweitest");
        tabParams.put("memberId", "1");

        for (int i = 0; i < 30; i++) {
            final int index = i;
            ThreadPoolUtil.getInstache().singleExecute(new Runnable() {

                @Override
                public void run() {
                    switch (index % 3) {
                        case 0:
                            getGithub.setRequestId(9999)
                                    .setCurrentProtocol(OkHttpBaseApi.Protocol.HTTPS)
                                    .setCurrentBaseUrl("api.github.com")
                                    .getPath("gists/c2a7c39532239ff261be", GithubBean.class);
                            break;
                        case 1:
                            getAd.setRequestId(Constants.REQUEST_ID_MSY_AD)
                                    .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                                    .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                                    .getPath(Constants.URL_MSY_AD, adParams, adType);
                            break;
                        case 2:
                            getList.setRequestId(Constants.REQUEST_ID_MSY_TABLE)
                                    .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                                    .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                                    .postParameters(Constants.URL_MSY_TABLE, tabParams, tabType);
                            break;
                    }
                }
            });
        }

    }

    private OnRequestCallBack callBack = new OnRequestCallBack() {

        @Override
        public void onBefore(int requestId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    progressDialog.showDialog();
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
                                Logger.d("请求美食易广告失败");
                                break;
                            case Constants.REQUEST_ID_MSY_TABLE:
                                Logger.d("请求美食易餐位失败");
                                break;
                            case 9999:
                                Logger.d("请求Github失败");
                                break;
                            case 9998:
                                Logger.d("验证请求失败");
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
                    switch (requestId) {
                        case Constants.REQUEST_ID_MSY_AD:
                            Logger.d("请求美食易广告成功");
                            break;
                        case Constants.REQUEST_ID_MSY_TABLE:
                            Logger.d("请求美食易餐位成功");
                            break;
                        case 9999:
                            Logger.d("请求Github成功");
                            break;
                        case 9998:
                            Logger.d("验证请求成功");
                            break;
                    }
                }
            });
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // 模拟线程操作
    ///////////////////////////////////////////////////////////////////////////
    private void cachedThreadPool() {
        for (int i = 0; i < 50; i++) {
            final int index = i;
            try {
                Thread.sleep(100); // 休眠时间越短创建的线程数越多
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Logger.d("active count = " + Thread.activeCount()
                            + " index = " + index);
                }
            });
        }
    }

    private void fixedThreadPool() {
        for (int i = 0; i < 30; i++) {
            final int index = i;
            ThreadPoolUtil.getInstache().fixedExecute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Logger.d(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //延时3秒钟执行
    private void scheduledThreadPool() {
        ThreadPoolUtil.getInstache().scheduled(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);
    }

    //延时1秒钟，之后每3秒执行一次（间隔时间和任务时间同步计算，最终等待时间为较长的那个时间）
    private void scheduledThreadPoolRate() {
        ThreadPoolUtil.getInstache().scheduledRate(new Runnable() {

            @Override
            public void run() {
                Logger.d("delay 1 seconds, and excute every 3 seconds");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 3, TimeUnit.SECONDS);

    }

    //延时1秒钟，之后每次完成任务后延时3秒
    private void scheduledThreadPoolDelay() {
        ThreadPoolUtil.getInstache().scheduledDelay(new Runnable() {

            @Override
            public void run() {
                Logger.d("delay 1 seconds, and excute every 3 seconds");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 3, TimeUnit.SECONDS);

    }

    private void singleThreadPool() {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            ThreadPoolUtil.getInstache().singleExecute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep((10 - index) * 1000);
                        Logger.d(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void shutDownAll() {
        ThreadPoolUtil.getInstache().cachedShutDown(0);
        ThreadPoolUtil.getInstache().fixedShutDown(0);
        ThreadPoolUtil.getInstache().scheduledShutDown(0);
        ThreadPoolUtil.getInstache().singleShutDown(0);
    }
}
