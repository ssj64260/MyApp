package com.cxb.tools.network.okhttp;

import okhttp3.OkHttpClient;

/**
 * 获取okhttpclient对象
 */

public class OkhttpClientManager {

    private static OkhttpClientManager instance;
    private OkHttpClient.Builder client;

    private OkhttpClientManager() {
        client = new OkHttpClient.Builder();
    }

    public static OkhttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkhttpClientManager.class) {
                if (instance == null) {
                    instance = new OkhttpClientManager();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getClient() {
        return client;
    }
}
