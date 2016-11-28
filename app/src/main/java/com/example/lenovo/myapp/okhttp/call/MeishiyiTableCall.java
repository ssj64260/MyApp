package com.example.lenovo.myapp.okhttp.call;

import com.cxb.tools.network.okhttp.OkHttpAsynchApi;
import com.example.lenovo.myapp.model.testbean.TableBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lenovo.myapp.utils.Constants.REQUEST_ID_MSY_TABLE;
import static com.example.lenovo.myapp.utils.Constants.URL_MSY_TABLE;

/**
 * 美食易广告请求
 */

public class MeishiyiTableCall extends OkHttpAsynchApi {
    private static final String PATH = URL_MSY_TABLE;
    private Map<String, String> params;
    private Type returnType;

    public MeishiyiTableCall() {
        super();
        setCurrentProtocol(Protocol.HTTP);
        setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl());
        setRequestId(REQUEST_ID_MSY_TABLE);
    }

    public void setParams(String memberId) {
        params = new HashMap<>();
        params.put("access_token", "70q2K29N2c8910p827M6Gff1Td1YIo");
        params.put("user", "aiweitest");
        params.put("e_access_token", "sACu425r6r45740A4tjmwSD466108Gy0a3f7");
        params.put("eeId", "97");
        params.put("timesId", "2935");
        params.put("date", "2016-11-23");
        params.put("memberId", memberId);

        returnType = new TypeToken<List<TableBean>>() {
        }.getType();
    }

    public void requestCall() {
        postParameters(PATH, params, returnType);
    }
}
