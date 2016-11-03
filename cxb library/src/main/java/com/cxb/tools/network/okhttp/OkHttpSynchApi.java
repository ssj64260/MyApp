package com.cxb.tools.network.okhttp;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 同步请求
 */
public class OkHttpSynchApi extends OkHttpBaseApi {

    public OkHttpSynchApi() {
        super();
    }

    public OkHttpSynchApi(File cacheDir, int cacheSize) {
        super(cacheDir, cacheSize);
    }

    public OkHttpSynchApi(final String username, final String password) {
        super(username, password);
    }

    @Override
    protected void doTheCall(Request request, String url, Type returnType) {
        if (callBack != null) {
            callBack.onBefore(requestId);
        }

        currentCall = client.newCall(request);

        try {
            Response response = currentCall.execute();

            if (response.isSuccessful()) {
                if (debug) {
                    Logger.d(url);
                }

                String json = response.body().string();
                Object bm;
                if (returnType != null) {
                    Gson gson = new Gson();
                    bm = gson.fromJson(json, returnType);
                } else {
                    bm = json;
                }

                if (debug) {
                    Logger.t(url.substring(url.lastIndexOf("/") + 1, url.contains("?") ? url.indexOf("?") : url.length())).json(json + "");
                }

                if (callBack != null) {
                    callBack.onResponse(requestId, bm, response.code());
                }
            } else {
                if (callBack != null) {
                    callBack.onFailure(requestId, OnRequestCallBack.FailureReason.OTHER);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onFailure(requestId, OnRequestCallBack.FailureReason.OTHER);
            }
        }
    }

    public OkHttpSynchApi addListener(OnRequestCallBack callBack) {
        super.addListener(callBack);
        return this;
    }

    public OkHttpSynchApi setCurrentProtocol(OkHttpBaseApi.Protocol currentProtocol) {
        super.setCurrentProtocol(currentProtocol);
        return this;
    }

    public OkHttpSynchApi setCurrentBaseUrl(String currentBaseUrl) {
        super.setCurrentBaseUrl(currentBaseUrl);
        return this;
    }

    public OkHttpSynchApi setRequestId(int requestId) {
        super.setRequestId(requestId);
        return this;
    }

    public OkHttpSynchApi isDebug(boolean debug) {
        super.isDebug(debug);
        return this;
    }
}
