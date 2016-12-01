package com.example.lenovo.myapp.okhttp.call;

import com.cxb.tools.network.okhttp.OkHttpAsynchApi;
import com.cxb.tools.network.okhttp.ServiceResult;
import com.example.lenovo.myapp.model.testbean.UserInfoBean;
import com.example.lenovo.myapp.okhttp.URLSetting;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.example.lenovo.myapp.utils.Constants.ID_BOSS_LOGIN;
import static com.example.lenovo.myapp.utils.Constants.URL_BOSS_LOGIN;

/**
 * 美食易Boss登录接口
 */

public class BossLoginCall extends OkHttpAsynchApi {
    private static final String PATH = URL_BOSS_LOGIN;
    private Map<String, String> params;
    private Type returnType;

    public BossLoginCall() {
        super();
        setCurrentProtocol(Protocol.HTTP);
        setCurrentBaseUrl(URLSetting.getInstance().getBaseUrl());
        setRequestId(ID_BOSS_LOGIN);
    }

    public void setParams(String phone, String password) {
        params = new HashMap<>();
        params.put("phone", phone);
        params.put("pwd", password);

        returnType = new TypeToken<ServiceResult<UserInfoBean>>() {
        }.getType();
    }

    public void requestCall() {
        postParameters(PATH, params, returnType);
    }
}
