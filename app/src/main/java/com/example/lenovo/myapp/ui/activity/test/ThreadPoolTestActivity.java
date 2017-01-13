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
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.model.testbean.AdBean;
import com.example.lenovo.myapp.model.testbean.GithubBean;
import com.example.lenovo.myapp.model.testbean.UserInfoBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.lenovo.myapp.utils.Constants.ID_MSY_AD;
import static com.example.lenovo.myapp.utils.Constants.ID_BOSS_LOGIN;
import static com.example.lenovo.myapp.utils.Constants.URL_MSY_AD;
import static com.example.lenovo.myapp.utils.Constants.URL_BOSS_LOGIN;

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
    private OkHttpSynchApi getList;
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

        getList = new OkHttpSynchApi()
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
        final Type returnType = new TypeToken<List<AdBean>>() {
        }.getType();
        final Map<String, String> params = new HashMap<>();
        params.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        params.put("user", "aiweitest");

        ThreadPoolUtil.getInstache().scheduledRate(new Runnable() {

            @Override
            public void run() {
                getAd.setRequestId(ID_MSY_AD)
                        .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                        .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                        .getPath(URL_MSY_AD, params, returnType);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void sequence() {
        final Type adType = new TypeToken<List<AdBean>>() {
        }.getType();
        final Type tabType = new TypeToken<List<UserInfoBean>>() {
        }.getType();

        final Map<String, String> adParams = new HashMap<>();
        adParams.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        adParams.put("user", "aiweitest");

        final Map<String, String> tabParams = new HashMap<>();
        tabParams.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        tabParams.put("user", "aiweitest");
        tabParams.put("e_access_token", "sACu425r6r45740A4tjmwSD466108Gy0a3f7");
        tabParams.put("eeId", "97");
        tabParams.put("timesId", "2935");
        tabParams.put("date", "2016-11-23");
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
                            getAd.setRequestId(ID_MSY_AD)
                                    .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                                    .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                                    .getPath(URL_MSY_AD, adParams, adType);
                            break;
                        case 2:
                            getList.setRequestId(ID_BOSS_LOGIN)
                                    .setCurrentProtocol(OkHttpSynchApi.Protocol.HTTP)
                                    .setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl())
                                    .postParameters(URL_BOSS_LOGIN, tabParams, tabType);
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
                        String content = tvContent.getText().toString();
                        threadIndex++;
                        switch (requestId) {
                            case ID_MSY_AD:
                                tvContent.setText(content + threadIndex + "#请求美食易广告失败\n");
                                break;
                            case ID_BOSS_LOGIN:
                                tvContent.setText(content + threadIndex + "#请求美食易餐位失败\n");
                                break;
                            case 9999:
                                tvContent.setText(content + threadIndex + "#请求Github失败\n");
                                break;
                            case 9998:
                                tvContent.setText(content + threadIndex + "#验证请求失败\n");
                                break;
                        }
                        svBackground.fullScroll(ScrollView.FOCUS_DOWN);
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
                    String content = tvContent.getText().toString();
                    threadIndex++;
                    switch (requestId) {
                        case ID_MSY_AD:
                            tvContent.setText(content + threadIndex + "#请求美食易广告成功\n");
                            break;
                        case ID_BOSS_LOGIN:
                            tvContent.setText(content + threadIndex + "#请求美食易餐位成功\n");
                            break;
                        case 9999:
                            tvContent.setText(content + threadIndex + "#请求Github成功\n");
                            break;
                        case 9998:
                            tvContent.setText(content + threadIndex + "#验证请求成功\n");
                            break;
                    }
                    tvContent.post(new Runnable() {
                        @Override
                        public void run() {
                            svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
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
