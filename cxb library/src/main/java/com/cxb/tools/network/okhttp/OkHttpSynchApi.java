package com.cxb.tools.network.okhttp;

import com.cxb.tools.utils.StringUtils;
import com.google.gson.Gson;

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

        currentCall = client.newCall(request);

        try {
            final Response response = currentCall.execute();

            if (response.isSuccessful()) {
                showLog("", url);

                String json = response.body().string();
                final Object bm;
                if (returnType != null) {
                    Gson gson = new Gson();
                    bm = gson.fromJson(json, returnType);
                } else {
                    bm = json;
                }

                showLog(StringUtils.getUrlTag(url), json + "");

                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null && !currentCall.isCanceled()) {
                            callBack.onResponse(requestId, bm, response.code());
                        }
                    }
                });
            } else {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFailure(requestId, OnRequestCallBack.FailureReason.OTHER);
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onFailure(requestId, OnRequestCallBack.FailureReason.OTHER);
                    }
                }
            });
        }
    }

    public OkHttpSynchApi addDownloadListener(OnDownloadCallBack downloadCallBack){
        this.downloadCallBack = downloadCallBack;
        return this;
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
