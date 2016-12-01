package com.cxb.tools.network.okhttp;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

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
        if (callBack != null) {
            callBack.onBefore(requestId);
        }

        currentCall = client.newCall(request);

        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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

            @Override
            public void onResponse(Call call, Response response) {
                if (debug) {
                    Logger.d(url);
                }

                try {
                    String json = response.body().string();

                    if (debug) {
                        Logger.t(url.substring(url.lastIndexOf("/") + 1, url.contains("?") ? url.indexOf("?") : url.length())).json(json + "");
                    }

                    Object bm;
                    if (returnType != null) {
                        Gson gson = new Gson();
                        bm = gson.fromJson(json, returnType);
                    } else {
                        bm = json;
                    }

                    if (callBack != null) {
                        callBack.onResponse(requestId, bm, response.code());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailure(requestId, OnRequestCallBack.FailureReason.DATAANALYSIS);
                    }
                }
            }
        });
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
