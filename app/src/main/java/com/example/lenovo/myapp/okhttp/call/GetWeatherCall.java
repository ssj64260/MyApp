package com.example.lenovo.myapp.okhttp.call;

import com.cxb.tools.network.okhttp.OkHttpAsynchApi;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.example.lenovo.myapp.utils.Constants.ID_GET_WEATHER;
import static com.example.lenovo.myapp.utils.Constants.WEATHER_URL;

/**
 * 获取天气预报
 */

public class GetWeatherCall extends OkHttpAsynchApi {
    private static final String PATH = "";
    private Map<String, String> params;
    private Type returnType;

    public GetWeatherCall() {
        super();
        setCurrentProtocol(Protocol.HTTP);
        setCurrentBaseUrl(WEATHER_URL);
        setRequestId(ID_GET_WEATHER);
    }

    public void setParams(String weaid) {
        params = new HashMap<>();
        params.put("app", "weather.future");
        params.put("weaid", weaid);
        params.put("appkey", "10003");
        params.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format", "json");

//        returnType = new TypeToken<ServiceResult<UserInfoBean>>() {
//        }.getType();
    }

    public void requestCall() {
        postParameters(PATH, params, null);
    }
}
