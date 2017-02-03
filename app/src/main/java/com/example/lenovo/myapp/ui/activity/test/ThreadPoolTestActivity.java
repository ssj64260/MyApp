package com.example.lenovo.myapp.ui.activity.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cxb.tools.network.okhttp.OkHttpBaseApi;
import com.cxb.tools.network.okhttp.OkHttpSynchApi;
import com.cxb.tools.network.okhttp.OnRequestCallBack;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.GithubBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.lenovo.myapp.utils.Constants.HOST_GITHUB;
import static com.example.lenovo.myapp.utils.Constants.ID_GET_GITHUB_INFO;
import static com.example.lenovo.myapp.utils.Constants.ID_GET_WEATHER;
import static com.example.lenovo.myapp.utils.Constants.ID_POST_WEATHER;
import static com.example.lenovo.myapp.utils.Constants.URL_GET_GITHUB_INFO;
import static com.example.lenovo.myapp.utils.Constants.URL_WEATHER;

/**
 * 线程池测试
 */

public class ThreadPoolTestActivity extends BaseActivity {

    private Button btnAsyncTask;
    private Button btnHeartGet;
    private Button btnSequence;
    private Button btnCached;
    private Button btnFixed;
    private Button btnScheduled;
    private Button btnScheduledRate;
    private Button btnScheduledDelay;
    private Button btnSingle;
    private Button btnShutDown;

    private ScrollView svBackground;
    private Button btnClear;
    private TextView tvContent;

    private DefaultProgressDialog progressDialog;

    private OkHttpSynchApi getAd;
    private OkHttpSynchApi getWeather;
    private OkHttpSynchApi getGithub;

    private int threadIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_test);

        initVew();
        initData();

    }

    @Override
    protected void onDestroy() {
        shutDownAll();
        super.onDestroy();
    }

    private void initVew() {

        btnAsyncTask = (Button) findViewById(R.id.btn_async_task);
        btnAsyncTask.setOnClickListener(click);

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

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(click);

        svBackground = (ScrollView) findViewById(R.id.sv_background);

        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
        progressDialog = new DefaultProgressDialog(this);
        progressDialog.setMessage("请求中...");

        getAd = new OkHttpSynchApi()
                .addListener(callBack);

        getWeather = new OkHttpSynchApi()
                .addListener(callBack);

        getGithub = new OkHttpSynchApi()
                .addListener(callBack);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            threadIndex = 0;
            switch (v.getId()) {
                case R.id.btn_async_task:
                    asyncTaskTest();
                    break;
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
                case R.id.btn_clear:
                    tvContent.setText("");
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////
    private void heartGet() {
        final Type returnType = new TypeToken<String>() {
        }.getType();
        final Map<String, String> params = new HashMap<>();
        params.put("app", "weather.today");
        params.put("weaid", "101280800");
        params.put("appkey", "10003");
        params.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format", "json");

        ThreadPoolUtil.getInstache().scheduledRate(new Runnable() {

            @Override
            public void run() {
                getAd.setRequestId(ID_GET_WEATHER)
                        .setCurrentProtocol(URLSetting.getInstance().getBaseProtocol())
                        .setCurrentBaseUrl(URL_WEATHER)
                        .getPath("", params, null);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void sequence() {
        final Type todayWeatherType = new TypeToken<String>() {
        }.getType();

        final Map<String, String> todayWeatherParams = new HashMap<>();
        todayWeatherParams.put("app", "weather.today");
        todayWeatherParams.put("weaid", "101280800");
        todayWeatherParams.put("appkey", "10003");
        todayWeatherParams.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        todayWeatherParams.put("format", "json");

        final Map<String, String> weatherParams = new HashMap<>();
        weatherParams.put("app", "weather.future");
        weatherParams.put("weaid", "101280800");
        weatherParams.put("appkey", "10003");
        weatherParams.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        weatherParams.put("format", "json");

        for (int i = 0; i < 30; i++) {
            final int index = i;
            ThreadPoolUtil.getInstache().singleExecute(new Runnable() {

                @Override
                public void run() {
                    switch (index % 3) {
                        case 0:
                            getGithub.setRequestId(ID_GET_GITHUB_INFO)
                                    .setCurrentProtocol(OkHttpBaseApi.Protocol.HTTPS)
                                    .setCurrentBaseUrl(HOST_GITHUB)
                                    .getPath(URL_GET_GITHUB_INFO, GithubBean.class);
                            break;
                        case 1:
                            getAd.setRequestId(ID_GET_WEATHER)
                                    .setCurrentProtocol(URLSetting.getInstance().getBaseProtocol())
                                    .setCurrentBaseUrl(URL_WEATHER)
                                    .getPath("", todayWeatherParams, null);
                            break;
                        case 2:
                            getWeather.setRequestId(ID_POST_WEATHER)
                                    .setCurrentProtocol(URLSetting.getInstance().getBaseProtocol())
                                    .setCurrentBaseUrl(URL_WEATHER)
                                    .postParameters("", weatherParams, null);
                            break;
                    }
                }
            });
        }

    }

    private OnRequestCallBack callBack = new OnRequestCallBack() {
        @Override
        public void onFailure(final int requestId, final FailureReason reason) {
            progressDialog.dismissDialog();

            if (reason != FailureReason.OTHER) {
                ToastUtil.toast(reason.getReason());
            } else {
                String content = tvContent.getText().toString();
                threadIndex++;
                switch (requestId) {
                    case ID_GET_WEATHER:
                        tvContent.setText(content + threadIndex + "#请求今天天气失败\n");
                        break;
                    case ID_POST_WEATHER:
                        tvContent.setText(content + threadIndex + "#请求天气预报失败\n");
                        break;
                    case ID_GET_GITHUB_INFO:
                        tvContent.setText(content + threadIndex + "#请求Github失败\n");
                        break;
                }
                svBackground.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }

        @Override
        public void onResponse(final int requestId, final Object dataObject, int networkCode) {
            progressDialog.dismissDialog();
            String content = tvContent.getText().toString();
            threadIndex++;
            switch (requestId) {
                case ID_GET_WEATHER:
                    tvContent.setText(content + threadIndex + "#请求今天天气成功\n");
                    break;
                case ID_POST_WEATHER:
                    tvContent.setText(content + threadIndex + "#请求天气预报成功\n");
                    break;
                case ID_GET_GITHUB_INFO:
                    tvContent.setText(content + threadIndex + "#请求Github成功\n");
                    break;
            }
            tvContent.post(new Runnable() {
                @Override
                public void run() {
                    svBackground.fullScroll(ScrollView.FOCUS_DOWN);
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String content = tvContent.getText().toString();
                            tvContent.setText(content + "active count = " + Thread.activeCount()
                                    + " index = " + index + "\n");
                            svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String content = tvContent.getText().toString();
                                tvContent.setText(content + index + "\n");
                                svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String content = tvContent.getText().toString();
                        tvContent.setText(content + "延时3秒钟执行\n");
                        svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        }, 3, TimeUnit.SECONDS);
    }

    //延时1秒钟，之后每2秒执行一次（间隔时间和任务时间同步计算，最终等待时间为较长的那个时间）
    private void scheduledThreadPoolRate() {
        ThreadPoolUtil.getInstache().scheduledRate(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        threadIndex++;
                        String content = tvContent.getText().toString();
                        tvContent.setText(content + threadIndex + "#延时1秒钟，\n之后每2秒执行一次\n");
                        svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 2, TimeUnit.SECONDS);

    }

    //延时1秒钟，之后每次完成任务后延时1秒
    private void scheduledThreadPoolDelay() {
        ThreadPoolUtil.getInstache().scheduledDelay(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String content = tvContent.getText().toString();
                        tvContent.setText(content + threadIndex + "#延时1秒钟，\n之后每次完成任务后延时1秒\n");
                        svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    private void singleThreadPool() {
        for (int i = 0; i < 100; i++) {
            final int index = i;
            ThreadPoolUtil.getInstache().singleExecute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep((100 - index) * 10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String content = tvContent.getText().toString();
                                tvContent.setText(content + index + "\n");
                                svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
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

    private void asyncTaskTest() {
        new AsyncTask<Integer, String, String>() {
            @Override
            protected void onPreExecute() {
                tvContent.setText("开始下载...\n");
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                String con = tvContent.getText().toString();
                tvContent.setText(con + s + "\n");
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                tvContent.setText("下载中：" + values[0] + "%\n");
                super.onProgressUpdate(values);
            }

            @Override
            protected String doInBackground(Integer... params) {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = params[0]; i <= 100; i++) {
                    publishProgress(String.valueOf(i));
                    try {
                        long sec = (long) (Math.random() * 300);
                        Thread.sleep(sec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return "下载完成";
            }
        }.execute(0);
    }
}
