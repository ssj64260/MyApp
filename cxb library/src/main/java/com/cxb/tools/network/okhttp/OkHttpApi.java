package com.cxb.tools.network.okhttp;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class OkHttpApi {

    public enum Protocol {
        HTTP, HTTPS
    }

    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType OCTET_STREAM = MediaType.parse("\"application/octet-stream\"");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String SESSION_ID = "connectsid";
    private final String Authorization = "Authorization";
    private final String USER_AGENT = "User-Agent";

    private Protocol currentProtocol;
    private String currentBaseUrl;
    private int requestId;

    private Call currentCall;
    private OnRequestCallBack callBack;

    private OkHttpClient client;

    private boolean debug = false;//开启debug模式，打印log

    public OkHttpApi() {
        client = OkHttpClientManager.getInstance().getClient();
    }

    public OkHttpApi(File cacheDir, int cacheSize) {
        Cache cache = new Cache(cacheDir, cacheSize);
        client = OkHttpClientManager.getInstance().getClient().newBuilder()
                .cache(cache)
                .build();
    }

    public OkHttpApi(final String username, final String password) {
        Authenticator authenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
        };
        client = OkHttpClientManager.getInstance().getClient().newBuilder()
                .authenticator(authenticator)
                .build();
    }

    //GET 参数已拼在url里
    public void getPath(String path, Type returnType) {
        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;

        Request request = new Request.Builder()
                .url(url)
                .build();

        doTheCall(request, url, returnType);
    }

    //GET 参数在params里
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

        doTheCall(request, url, returnType);
    }

    //post parameters
    public void postParameters(String path, Map<String, String> params, Type returnType) {
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

        doTheCall(request, url, returnType);
    }

    //post string
    public void postString(String path, String postBody, Type returnType) {
        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        doTheCall(request, url, returnType);
    }

    //post file
    public void postFile(String path, File file, Type returnType) {
        String protocolString = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        String url = protocolString + currentBaseUrl + "/" + path;
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        doTheCall(request, url, returnType);
    }

    private void doTheCall(final Request request, final String url, final Type returnType) {

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
                    Object bm;
                    if (returnType != null) {
                        Gson gson = new Gson();
                        bm = gson.fromJson(json, returnType);
                    }else {
                        bm = json;
                    }

                    if (debug) {
                        Logger.t(url.substring(url.lastIndexOf("/") + 1, url.contains("?") ? url.indexOf("?") : url.length())).json(json + "");
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

    public void cancelRequest() {
        if (currentCall != null) {
            currentCall.cancel();
        }
    }

    public OkHttpApi addListener(OnRequestCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public OkHttpApi setCurrentProtocol(Protocol currentProtocol) {
        this.currentProtocol = currentProtocol;
        return this;
    }

    public OkHttpApi setCurrentBaseUrl(String currentBaseUrl) {
        this.currentBaseUrl = currentBaseUrl;
        return this;
    }

    public OkHttpApi setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public OkHttpApi isDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

}
