package com.example.lenovo.myapp.okhttp.call;

import com.cxb.tools.network.okhttp.OkHttpApi;
import com.example.lenovo.myapp.model.testbean.TableBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.example.lenovo.myapp.utils.Constants;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 美食易广告请求
 */

public class MeishiyiTableCall extends OkHttpApi {
    private static final String PATH = Constants.URL_MSY_TABLE;
    private Map<String, String> params;
    private Type returnType;

    public MeishiyiTableCall() {
        super();
        setCurrentProtocol(Protocol.HTTP);
        setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl());
        setRequestId(Constants.REQUEST_ID_MSY_TABLE);
    }

    public void setParams(String memberId) {
        params = new HashMap<>();
        params.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        params.put("user", "aiweitest");
        params.put("memberId", memberId);

        returnType = new TypeToken<List<TableBean>>() {
        }.getType();
    }

    public void requestCall() {
        postParameters(PATH, params, returnType);
    }
}
