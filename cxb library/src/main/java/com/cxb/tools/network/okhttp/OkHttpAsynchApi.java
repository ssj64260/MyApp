package com.cxb.tools.network.okhttp;

import com.cxb.tools.utils.StringUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 异步请求
 */
public class OkHttpAsynchApi extends OkHttpBaseApi {

    public OkHttpAsynchApi() {
        super();
    }

    public OkHttpAsynchApi(File cacheDir, int cacheSize) {
        super(cacheDir, cacheSize);
    }

    public OkHttpAsynchApi(final String username, final String password) {
        super(username, password);
    }

    @Override
    protected void doTheCall(final Request request, final String url, final Type returnType) {

        currentCall = client.newCall(request);

        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            if ("Canceled".equals(e.getMessage()) || "Socket closed".equals(e.getMessage())) {
                                callBack.onFailure(requestId, OnRequestCallBack.FailureReason.CANCELED);
                            } else if (e instanceof SocketTimeoutException) {
                                callBack.onFailure(requestId, OnRequestCallBack.FailureReason.TIMEOUT);
                            } else {
                                callBack.onFailure(requestId, OnRequestCallBack.FailureReason.OTHER);
                            }
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                showLog("", url);

                try {
                    String json = response.body().string();

                    String tag = StringUtils.getUrlTag(url);
                    showLog(tag, json + "");

                    final Object bm;
                    if (returnType != null) {
                        Gson gson = new Gson();
                        bm = gson.fromJson(json, returnType);
                    } else {
                        bm = json;
                    }

                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null && !call.isCanceled()) {
                                callBack.onResponse(requestId, bm, response.code());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null && !call.isCanceled()) {
                                callBack.onFailure(requestId, OnRequestCallBack.FailureReason.DATAANALYSIS);
                            }
                        }
                    });
                }
            }
        });
    }

    public OkHttpAsynchApi addDownloadListener(OnDownloadCallBack downloadCallBack){
        this.downloadCallBack = downloadCallBack;
        return this;
    }

    public OkHttpAsynchApi addListener(OnRequestCallBack callBack) {
        super.addListener(callBack);
        return this;
    }

    public OkHttpAsynchApi setCurrentProtocol(OkHttpBaseApi.Protocol currentProtocol) {
        super.setCurrentProtocol(currentProtocol);
        return this;
    }

    public OkHttpAsynchApi setCurrentBaseUrl(String currentBaseUrl) {
        super.setCurrentBaseUrl(currentBaseUrl);
        return this;
    }

    public OkHttpAsynchApi setRequestId(int requestId) {
        super.setRequestId(requestId);
        return this;
    }

    public OkHttpAsynchApi isDebug(boolean debug) {
        super.isDebug(debug);
        return this;
    }
}
