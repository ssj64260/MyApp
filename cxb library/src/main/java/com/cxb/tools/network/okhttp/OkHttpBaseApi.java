package com.cxb.tools.network.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.cxb.tools.utils.StringCheck;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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

/**
 * okhttp 基类
 */

public abstract class OkHttpBaseApi {

    public enum Protocol {
        HTTP, HTTPS
    }

    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType OCTET_STREAM = MediaType.parse("\"application/octet-stream\"");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String SESSION_ID = "connectsid";
    private final String Authorization = "Authorization";
    private final String USER_AGENT = "User-Agent";

    private Protocol currentProtocol = null;
    private String currentBaseUrl;
    int requestId;

    Call currentCall;
    OnRequestCallBack callBack;
    OnDownloadCallBack downloadCallBack;

    OkHttpClient client;

    protected Handler mainThread = new Handler(Looper.getMainLooper());//回调到主线程

    boolean debug = true;//开启debug模式，打印log

    OkHttpBaseApi() {
        client = OkHttpClientManager.getInstance().getClient();
    }

    OkHttpBaseApi(File cacheDir, int cacheSize) {
        Cache cache = new Cache(cacheDir, cacheSize);
        client = OkHttpClientManager.getInstance().getClient().newBuilder()
                .cache(cache)
                .build();
    }

    OkHttpBaseApi(final String username, final String password) {
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

    private String getWholeUrl(String path) {

        if (currentProtocol == null) {
            currentProtocol = Protocol.HTTP;
        }

        String protocol = currentProtocol == Protocol.HTTP ? "http://" : "https://";
        return protocol + currentBaseUrl + "/" + path;
    }

    //GET 参数已拼在url里
    public void getPath(String path, Type returnType) {
        String url = getWholeUrl(path);
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

        String url = getWholeUrl(path);
        Request request = new Request.Builder()
                .url(url)
                .build();

        doTheCall(request, url, returnType);
    }

    //post parameters
    public void postParameters(String path, Map<String, String> params, Type returnType) {
        String url = getWholeUrl(path);
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
        String url = getWholeUrl(path);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        doTheCall(request, url, returnType);
    }

    //post file
    public void postFile(String path, File file, Type returnType) {
        String url = getWholeUrl(path);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        doTheCall(request, url, returnType);
    }

    public void downloadFile(String path, final String fileUri) {
        String url = getWholeUrl(path);
        Request request = new Request.Builder().url(url).build();
        currentCall = client.newCall(request);
        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (downloadCallBack != null) {
                            downloadCallBack.onFailed(fileUri);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    final long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(fileUri);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        final long finalCurrent = current;
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                if (downloadCallBack != null && !call.isCanceled()) {
                                    downloadCallBack.onPregrass(finalCurrent, total);
                                }
                            }
                        });
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (downloadCallBack != null && !call.isCanceled()) {
                                downloadCallBack.onSuccess(fileUri);
                            }
                        }
                    });

                } catch (IOException e) {
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (downloadCallBack != null) {
                                downloadCallBack.onFailed(fileUri);
                            }
                        }
                    });
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected abstract void doTheCall(final Request request, final String url, final Type returnType);

    public void cancelRequest() {
        if (currentCall != null && currentCall.isExecuted()) {
            currentCall.cancel();
        }
    }

    public OkHttpBaseApi addDownloadListener(OnDownloadCallBack downloadCallBack){
        this.downloadCallBack = downloadCallBack;
        return this;
    }

    public OkHttpBaseApi addListener(OnRequestCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public OkHttpBaseApi setCurrentProtocol(OkHttpBaseApi.Protocol currentProtocol) {
        this.currentProtocol = currentProtocol;
        return this;
    }

    public OkHttpBaseApi setCurrentBaseUrl(String currentBaseUrl) {
        this.currentBaseUrl = currentBaseUrl;
        return this;
    }

    public OkHttpBaseApi setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public OkHttpBaseApi isDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    protected void showLog(String tag, String data) {
        if (debug) {
            if (StringCheck.isEmpty(tag)) {
                Logger.d(data);
            } else {
                Logger.t(tag).json(data);
            }
        }
    }
}
