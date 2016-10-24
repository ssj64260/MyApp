package com.example.lenovo.myapp.okhttp.call;

import com.cxb.tools.network.okhttp.OkHttpApi;
import com.example.lenovo.myapp.model.meishiyi.AdBean;
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

public class MeishiyiAdCall extends OkHttpApi {
    private static final String PATH = "ADGetData/getHomeAdvert";
    private Map<String, String> params;
    private Type returnType;

    public MeishiyiAdCall() {
        currentProtocol = Protocol.HTTP;
        currentBaseUrl = URLSetting.getInstance().getBaseUrl();
        requestId = Constants.MSY_AD;
    }

    public void setParams(String token, String user) {

        params = new HashMap<>();
        params.put("access_token", token);
        params.put("user", user);

        returnType = new TypeToken<List<AdBean>>() {
        }.getType();

    }

    public void requestCall() {
        getPath(PATH, params, returnType);
    }
}
