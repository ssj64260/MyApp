package com.cxb.tools.network.okhttp;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class OkHttpApi {

    public enum Protocol {
        HTTP, HTTPS
    }

    private static final MediaType OCTET_STREAM = MediaType.parse("\"application/octet-stream\"");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String SESSION_ID = "connectsid";
    private final String Authorization = "Authorization";
    private final String USER_AGENT = "User-Agent";

    protected Protocol currentProtocol;
    protected String currentBaseUrl;
    protected int requestId;

    private Call currentCall;
    private OnRequestCallBack callBack;

    public OkHttpApi() {

    }

    public void getPath(String path, Type returnType) {
        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;

        Request request = new Request.Builder()
                .url(url)
                .build();

        doTheCall(request, url,returnType);
    }

    public void getPath(String path, Map<String, String> params, Type returnType) {
        StringBuilder paramsEndpoint = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (paramsEndpoint.length() == 0) {
                    paramsEndpoint.append('?');
                } else {
                    paramsEndpoint.append('&');
                }
                paramsEndpoint.append(param.getKey());
                paramsEndpoint.append('=');
                try {
                    paramsEndpoint.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            path += paramsEndpoint;
        }

        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;
        Request request = new Request.Builder()
                .url(url)
                .build();

        doTheCall(request, url,returnType);
    }

    public void postPath(String path, Map<String, String> params, Type returnType) {
        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;
        FormBody.Builder body = new FormBody.Builder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            body.add(param.getKey(), param.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
//                .addHeader("content-type", "application/x-www-form-urlencoded")
                .post(body.build())
                .build();

        doTheCall(request, url,returnType);
    }

    protected void doTheCall(Request request, final String url, final Type returnType) {
        OkHttpClient.Builder client = OkhttpClientManager.getInstance().getClient();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(10, TimeUnit.SECONDS);

        if (callBack != null) {
            callBack.onBefore(requestId);
        }

        currentCall = client.build().newCall(request);
        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (callBack != null) {
                    callBack.onFailure(requestId);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                Logger.d(url);
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Object bm = gson.fromJson(json, returnType);

                    Logger.t(url.substring(url.lastIndexOf("/") + 1, url.contains("?") ? url.indexOf("?") : url.length())).json(json + "");

                    if (callBack != null) {
                        callBack.onResponse(requestId, bm, response.code());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailure(requestId);
                    }
                }
            }
        });
    }

    public void cancelRequest() {
        if (currentCall != null) {
            currentCall.cancel();
        }
    }

    public interface OnRequestCallBack {
        void onBefore(int requestId);

        void onFailure(int requestId);

        void onResponse(int requestId, Object dataObject, int networkCode);
    }

    public void addListener(OnRequestCallBack callBack) {
        this.callBack = callBack;
    }

}
